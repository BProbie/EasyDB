package com.probie.easydb.Database.Local.Interface;

import com.probie.easydb.Database.Local.LocalDB;
import com.probie.easydb.Database.Local.LocalRemoteDB;

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