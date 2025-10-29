package com.probie.easydb.Database.Local.Interface;

import java.util.concurrent.locks.Lock;

public interface ILocalDatabase {

    /**
     * 设置数据到缓存
     * */
    void set(Object key, Object value);
    void set(Object[] keys, Object value);
    void set(Object key, Object[] values);
    void set(Object[] keys, Object[] values);

    /**
     * 从缓存获取数据
     * */
    Object get(Object key);
    Object[] get(Object... keys);

    /**
     * 从缓存获取默认数据
     * */
    Object get(Object key, Object defaultValue);
    Object[] get(Object[] keys, Object defaultValue);
    Object[] get(Object[] keys, Object[] defaultValues);

    /**
     * 添加数据到缓存
     * */
    void add(Object key, Object value);
    void add(Object[] keys, Object value);
    void add(Object key, Object[] values);
    void add(Object[] keys, Object[] values);

    /**
     * 从缓存删除数据
     * */
    void remove(Object... keys);
    Boolean remove(Object key, Object value);
    Boolean[] remove(Object[] keys, Object value);
    Boolean[] remove(Object key, Object[] values);
    Boolean[] remove(Object keys[], Object[] values);

    /**
     * 清除缓存数据
     * */
    void clear();

    /**
     * 获取读锁和写锁
     * */
    Lock getReadLock();
    Lock getWriteLock();

    /**
     * 是否连接
     * */
    void setIsConnection(Boolean isConnection);
    Boolean getIsConnection();

    /**
     * 自动提交
     * */
    void setIsAutoCommit(Boolean isAutoCommit);
    Boolean getIsAutoCommit();

    /**
     * 目标数据库路径
     * */
    void setFullFilePath(String filePath);
    String getFullFilePath();

    /**
     * 获取路径、文件名
     * */
    String getPath();
    String getFileName();

    /**
     * 同步路径
     * */
    void setSynFullFilePath(String synFilePath);
    String getSynFullFilePath();

    /**
     * 切割符号
     * */
    void setSplit(String split);
    String getSplit();

}