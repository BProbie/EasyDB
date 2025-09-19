package com.probie.Database.Remote;

import java.sql.*;
import java.io.Closeable;
import java.io.Serializable;
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
            getReadLock().lock();
            Class.forName(getDriver());
            setConnection(DriverManager.getConnection(getUrl()));
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException(exception);
        } finally {
            getReadLock().unlock();
        }
        return true;
    }

    @Override
    public Boolean commit() {
        try {
            getWriteLock().lock();
            getConnection().commit();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        } finally {
            getWriteLock().unlock();
        }
        return true;
    }

    @Override
    public Boolean rollback() {
        try {
            getWriteLock().lock();
            getConnection().rollback();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        } finally {
            getWriteLock().unlock();
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
    public PreparedStatement getPreparedStatement(PreparedStatement preparedStatement, Object... values) {
        for (int i = 0; i < values.length; i++) {
            try {
                preparedStatement.setString(i, values[i].toString());
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return preparedStatement;
    }

    @Override
    public Boolean runPreparedStatementExecute(PreparedStatement preparedStatement) {
        try {
            getWriteLock().lock();
            if (!preparedStatement.isClosed()) {
                Boolean isExecute = preparedStatement.execute();
                preparedStatement.close();
                return isExecute;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        } finally {
            getWriteLock().unlock();
        }
        return false;
    }

    @Override
    public Boolean runPreparedStatementExecute(PreparedStatement preparedStatement, Object... values) {
        return runPreparedStatementExecute(getPreparedStatement(preparedStatement, values));
    }

    @Override
    public Boolean runPreparedStatementExecute(String preparedStatementCommand) {
        return runPreparedStatementExecute(getPreparedStatement(preparedStatementCommand));
    }

    @Override
    public Boolean runPreparedStatementExecute(String preparedStatementCommand, Object... values) {
        return runPreparedStatementExecute(getPreparedStatement(preparedStatementCommand, values));
    }

    @Override
    public int runPreparedStatementUpdate(PreparedStatement preparedStatement) {
        try {
            getWriteLock().lock();
            if (!preparedStatement.isClosed()) {
                int updateCount = preparedStatement.executeUpdate();
                return updateCount;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        } finally {
            getWriteLock().unlock();
        }
        return -1;
    }

    @Override
    public int runPreparedStatementUpdate(PreparedStatement preparedStatement, Object... values) {
        return runPreparedStatementUpdate(getPreparedStatement(preparedStatement, values));
    }

    @Override
    public int runPreparedStatementUpdate(String preparedStatementCommand) {
        return runPreparedStatementUpdate(getPreparedStatement(preparedStatementCommand));
    }

    @Override
    public int runPreparedStatementUpdate(String preparedStatementCommand, Object... values) {
        return runPreparedStatementUpdate(getPreparedStatement(preparedStatementCommand, values));
    }

    @Override
    public ResultSet runPreparedStatementQuery(PreparedStatement preparedStatement) {
        try {
            getReadLock().lock();
            if (!preparedStatement.isClosed()) {
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        } finally {
            getReadLock().unlock();
        }
        return null;
    }

    @Override
    public ResultSet runPreparedStatementQuery(PreparedStatement preparedStatement, Object... values) {
        return runPreparedStatementQuery(getPreparedStatement(preparedStatement, values));
    }

    @Override
    public ResultSet runPreparedStatementQuery(String preparedStatementCommand) {
        return runPreparedStatementQuery(getPreparedStatement(preparedStatementCommand));
    }

    @Override
    public ResultSet runPreparedStatementQuery(String preparedStatementCommand, Object... values) {
        return runPreparedStatementQuery(getPreparedStatement(preparedStatementCommand, values));
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