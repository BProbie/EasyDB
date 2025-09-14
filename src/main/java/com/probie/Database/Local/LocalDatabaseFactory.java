package com.probie.Database.Local;

import com.probie.Database.Local.Interface.ILocalDatabaseFactory;

public class LocalDatabaseFactory implements ILocalDatabaseFactory {

    private volatile static LocalDatabaseFactory INSTANCE;

    /**
     * 获取一个LocalDatabaseFactory单例对象
     * */
    public synchronized static LocalDatabaseFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalDatabaseFactory();
        }
        return INSTANCE;
    }

    @Override
    public LocalDB buildLocalDB() {
        return new LocalDB();
    }

    @Override
    public LocalRemoteDB buildLocalRemoteDB(String remoteFilePath) {
        return new LocalRemoteDB(remoteFilePath);
    }

}