package com.probie;

import com.probie.Database.Local.LocalDatabaseFactory;
import com.probie.Database.Remote.RemoteDatabaseFactory;

public class EasyDB {

    private static EasyDB INSTANCE = new EasyDB();

    /**
     * 获取EasyDB的单例对象
     * */
    public static EasyDB getInstance() {
        return INSTANCE;
    }

    /**
     * 构造一个本地数据库
     * */
    public LocalDatabaseFactory getLocalDatabaseFactory() {
        return LocalDatabaseFactory.getInstance();
    }

    /**
     * 构造一个远程数据库
     * */
    public RemoteDatabaseFactory getRemoteDatabaseFactory() {
        return RemoteDatabaseFactory.getInstance();
    }

}