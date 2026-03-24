package com.probie.easydb.database.local;

import com.probie.easydb.database.local.api.ILocalDatabaseFactory;

public class LocalDatabaseFactory implements ILocalDatabaseFactory {

    private volatile static LocalDatabaseFactory INSTANCE;

    /**
     * 获取一个懒加载的 LocalDatabaseFactory 的实例化单例对象
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