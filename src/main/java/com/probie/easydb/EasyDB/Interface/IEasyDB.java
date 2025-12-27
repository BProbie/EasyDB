package com.probie.easydb.EasyDB.Interface;

import com.probie.easydb.Database.Local.LocalDatabaseFactory;
import com.probie.easydb.Database.Remote.RemoteDatabaseFactory;

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