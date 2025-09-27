package com.probie.easydb.Database.Remote;

import com.probie.easydb.Database.Remote.Interface.IRemoteDatabaseFactory;

public class RemoteDatabaseFactory implements IRemoteDatabaseFactory {

    private volatile static RemoteDatabaseFactory INSTANCE;

    /**
     * 获取一个RemoteDatabaseFactory单例对象
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