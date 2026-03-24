package com.probie.easydb.easydb.api;

import com.probie.easydb.database.local.LocalDatabaseFactory;
import com.probie.easydb.database.remote.RemoteDatabaseFactory;

public interface IEasyDB {

    /**
     * 构造一个本地数据库
     * */
    public LocalDatabaseFactory getLocalDatabaseFactory();

    /**
     * 构造一个远程数据库
     * */
    RemoteDatabaseFactory getRemoteDatabaseFactory();

}