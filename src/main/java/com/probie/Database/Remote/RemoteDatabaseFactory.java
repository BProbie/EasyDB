package com.probie.Database.Remote;

import com.probie.Database.Remote.Interface.IRemoteDatabaseFactory;

public class RemoteDatabaseFactory implements IRemoteDatabaseFactory {

    private static RemoteDatabaseFactory INSTANCE = new RemoteDatabaseFactory();

    /**
     * 获取一个RemoteDatabaseFactory单例对象
     * */
    public static RemoteDatabaseFactory getInstance() {
        return INSTANCE;
    }

}