package com.probie.Database.Remote.Interface;

import com.probie.Database.Remote.RemoteDB;

public interface IRemoteDatabaseFactory {

    /**
     * 构造一远程数据库
     * */
    RemoteDB buildRemoteDB();

}