package com.probie.Database.Local;

import com.probie.Database.Local.Interface.ILocalDatabaseFactory;

public class LocalDatabaseFactory implements ILocalDatabaseFactory {

    private static LocalDatabaseFactory INSTANCE = new LocalDatabaseFactory();

    /**
     * 获取一个LocalDatabase单例对象
     * */
    public static LocalDatabaseFactory getInstance() {
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