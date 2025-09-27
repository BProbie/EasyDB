package com.probie.easydb.Database.Remote.Interface;

import com.probie.easydb.Database.Remote.RemoteDB;
import com.probie.easydb.Database.Remote.Supabase;

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