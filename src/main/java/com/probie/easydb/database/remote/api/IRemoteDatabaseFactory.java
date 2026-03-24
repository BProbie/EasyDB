package com.probie.easydb.database.remote.api;

import com.probie.easydb.database.remote.RemoteDB;
import com.probie.easydb.database.remote.Supabase;

public interface IRemoteDatabaseFactory {

    /**
     * 构造一远程数据库
     * */
    RemoteDB buildRemoteDB();

    /**
     * 构造一Supabase数据库
     * */
    Supabase buildSupabase();

}