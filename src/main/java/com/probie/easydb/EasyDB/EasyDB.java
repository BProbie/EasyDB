package com.probie.easydb.EasyDB;

import com.probie.easydb.EasyDB.Interface.IEasyDB;
import com.probie.easydb.Database.Local.LocalDatabaseFactory;
import com.probie.easydb.Database.Remote.RemoteDatabaseFactory;

public class EasyDB implements IEasyDB {

    private final String NAME = "EasyDB";
    private final String VERSION = "2.7";

    private volatile static EasyDB INSTANCE;

    /**
     * 获取 EasyDB 的单例对象
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