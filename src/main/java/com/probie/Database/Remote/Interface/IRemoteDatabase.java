package com.probie.Database.Remote.Interface;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.locks.Lock;

public interface IRemoteDatabase {

    /**
     * 连接远程数据库
     * */
    Boolean connect();

    /**
     * 提交、回滚操作
     * */
    Boolean commit();
    Boolean rollback();

    /**
     * 获取PreparedStatement
     * */
    PreparedStatement getPreparedStatement(String preparedStatementCommand);
    PreparedStatement getPreparedStatement(String preparedStatementCommand, Object... values);
    PreparedStatement getPreparedStatement(PreparedStatement preparedStatement, Object... values);

    /**
     * execute执行PreparedStatement
     * */
    Boolean runPreparedStatementExecute(PreparedStatement preparedStatement);
    Boolean runPreparedStatementExecute(PreparedStatement preparedStatement, Object... values);
    Boolean runPreparedStatementExecute(String preparedStatementCommand);
    Boolean runPreparedStatementExecute(String preparedStatementCommand, Object... values);

    /**
     * update执行PreparedStatement
     * */
    int runPreparedStatementUpdate(PreparedStatement preparedStatement);
    int runPreparedStatementUpdate(PreparedStatement preparedStatement, Object... values);
    int runPreparedStatementUpdate(String preparedStatementCommand);
    int runPreparedStatementUpdate(String preparedStatementCommand, Object... values);

    /**
     * query执行PreparedStatement
     * */
    ResultSet runPreparedStatementQuery(PreparedStatement preparedStatement);
    ResultSet runPreparedStatementQuery(PreparedStatement preparedStatement, Object... values);
    ResultSet runPreparedStatementQuery(String preparedStatementCommand);
    ResultSet runPreparedStatementQuery(String preparedStatementCommand, Object... values);

    /**
     * 获取读锁和写锁
     * */
    Lock getReadLock();
    Lock getWriteLock();

    /**
     * 设置、获取连接
     * */
    void setConnection(Connection connection);
    Connection getConnection();

    /**
     * 自动提交
     * */
    void setIsAutoCommit(Boolean isAutoCommit);
    Boolean getIsAutoCommit();

    /**
     * 用户名
     * */
    void setUserName(String userName);
    String getUserName();

    /**
     * 密码
     * */
    void setPassword(String password);
    String getPassword();

    /**
     * jdbc driver
     * */
    void setDriver(String driver);
    String getDriver();

    /**
     * 远程连接url
     * */
    void setUrl(String url);
    String getUrl();


}