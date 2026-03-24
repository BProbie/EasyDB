package com.probie.easydb.database.remote;

import com.probie.easydb.database.remote.api.IRemoteDatabaseFactory;

public class RemoteDatabaseFactory implements IRemoteDatabaseFactory {

    private volatile static RemoteDatabaseFactory INSTANCE;

    /**
     * 获取一个懒加载的 RemoteDatabaseFactory 的实例化单例对象
     * */
    public synchronized static RemoteDatabaseFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDatabaseFactory();
        }
        return INSTANCE;
    }

    @Override
    public RemoteDB buildRemoteDB() {
        return new RemoteDB();
    }

    @Override
    public Supabase buildSupabase() {
        return new Supabase();
    }

}