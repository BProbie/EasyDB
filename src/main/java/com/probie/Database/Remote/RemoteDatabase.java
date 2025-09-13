package com.probie.Database.Remote;

import java.io.Closeable;
import java.sql.Connection;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.probie.Database.Remote.Interface.IRemoteDatabase;

public class RemoteDatabase implements IRemoteDatabase, Serializable, Closeable {

    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = reentrantReadWriteLock.readLock();
    private Lock writeLock = reentrantReadWriteLock.writeLock();

    private Connection connection;

    private Boolean isAutoCommit = true;

    private String userName = "postgres";
    private String password = "123456";

    private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres?user="+getUserName()+".hgxarfmxttbwcfgcmxps&password="+getPassword();

    public RemoteDatabase() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public Boolean connect() {
        try {
            Class.forName(getDriver());
        } catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException(classNotFoundException);
        }
        try {
            setConnection(DriverManager.getConnection(getUrl()));
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
        return true;
    }

    @Override
    public Boolean commit() {
        try {
            getConnection().commit();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
        return true;
    }

    @Override
    public Boolean rollback() {
        try {
            getConnection().rollback();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
        return true;
    }

    @Override
    public PreparedStatement getPreparedStatement(String preparedStatementCommand) {
        try {
            return getConnection().prepareStatement(preparedStatementCommand);
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    @Override
    public PreparedStatement getPreparedStatement(String preparedStatementCommand, Object... values) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(preparedStatementCommand);
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i, values[i].toString());
            }
            return preparedStatement;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    @Override
    public Boolean runPreparedStatementCommand(PreparedStatement preparedStatementCommand) {
        try {
            if (!preparedStatementCommand.isClosed()) {
                getWriteLock().lock();
                Boolean isRun = preparedStatementCommand.execute();
                getWriteLock().unlock();
                preparedStatementCommand.close();
                return isRun;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
        return false;
    }

    @Override
    public Boolean runPreparedStatementCommand(String preparedStatementCommand) {
        return runPreparedStatementCommand(getPreparedStatement(preparedStatementCommand));
    }

    @Override
    public Boolean runPreparedStatementCommand(String preparedStatementCommand, Object... values) {
        return runPreparedStatementCommand(getPreparedStatement(preparedStatementCommand, values));
    }

    @Override
    public Lock getReadLock() {
        return readLock;
    }

    @Override
    public Lock getWriteLock() {
        return writeLock;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setIsAutoCommit(Boolean isAutoCommit) {
        this.isAutoCommit = isAutoCommit;
    }

    @Override
    public Boolean getIsAutoCommit() {
        return isAutoCommit;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void close() {
        try {
            if (!getConnection().isClosed()) {
                if (getIsAutoCommit()) {
                    commit();
                }
                getConnection().close();
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

}