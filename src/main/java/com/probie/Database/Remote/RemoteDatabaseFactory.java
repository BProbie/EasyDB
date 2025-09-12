package com.probie.Database.Remote;

import com.probie.Database.Remote.Interface.IRemoteDatabaseFactory;

public class RemoteDatabaseFactory implements IRemoteDatabaseFactory {

    private static RemoteDatabaseFactory INSTANCE = new RemoteDatabaseFactory();

    public static RemoteDatabaseFactory getInstance() {
        return INSTANCE;
    }

}