package com.probie.easydb.database.local.api;

import com.probie.easydb.database.local.LocalDB;
import com.probie.easydb.database.local.LocalRemoteDB;

public interface ILocalDatabaseFactory {

    /**
     * 构造一个本地文件数据库
     * */
    LocalDB buildLocalDB();

    /**
     * 构造一个远程文件数据库
     * */
    LocalRemoteDB buildLocalRemoteDB(String remoteFilePath);

}