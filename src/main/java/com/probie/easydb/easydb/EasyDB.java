package com.probie.easydb.easydb;

import com.probie.easydb.easydb.api.IEasyDB;
import com.probie.easydb.database.local.LocalDatabaseFactory;
import com.probie.easydb.database.remote.RemoteDatabaseFactory;

public class EasyDB implements IEasyDB {

    private final String NAME = "EasyDB";
    private final String VERSION = "3.0.0";

    private volatile static EasyDB INSTANCE;

    /**
     * 获取一个懒加载的 EasyDB 的实例化单例对象
     * */
    public synchronized static EasyDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EasyDB();
        }
        return INSTANCE;
    }

    @Override
    public LocalDatabaseFactory getLocalDatabaseFactory() {
        return LocalDatabaseFactory.getInstance();
    }

    @Override
    public RemoteDatabaseFactory getRemoteDatabaseFactory() {
        return RemoteDatabaseFactory.getInstance();
    }

}