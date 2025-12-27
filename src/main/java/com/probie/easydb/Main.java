package com.probie.easydb;

/**
 * Information
 * <p>
 * API-Name: EasyDB
 * API-Version: 2.7 (Stable)
 * API-Language: Java (JDK-21.0.8)
 * API-Dependence-Manager: Maven
 * API-Dependence: Null
 * <p>
 * Author: Probie
 * GitHub: <a href="https://github.com/BProbie"> EasyDB-Github </a>
 * */

public class Main {

    public static void main(String[] args) {

    }

//    public static void main(String[] args) {
//
//        EG!!!
//
//        本地数据库
//        LocalDB localDB = EasyDB.getInstance().getLocalDatabaseFactory().buildLocalDB().setComment("localDB");
//        if (localDB.connect()) {
//          localDB.set("date","yes");
//          localDB.set("probie",new Data().put("name","probie").put("age",18));
//          localDB.commit();
//          System.out.println(localDB.get("date"));
//          System.out.println(new Data().deCode(localDB.get("probie")));
//        }
//
//        本地包内数据库
//        LocalDB localDB = EasyDB.getInstance().getLocalDatabaseFactory().buildLocalDB();
//        if (localDB.connect(ClassLoader.getSystemResourceAsStream("EasyDB.properties"))) {
//          System.out.println(localDB.get("date"));
//          System.out.println(new Data().deCode(localDB.get("probie")));
//        }
//
//        本地远程数据库
//        LocalRemoteDB localRemoteDB = EasyDB.getInstance().getLocalDatabaseFactory().buildLocalRemoteDB("https://raw.githubusercontent.com/BProbie/EasyDB/refs/heads/master/EasyDB.db");
//        localRemoteDB.downloadDatabase();
//        if (localRemoteDB.connect()) {
//            localRemoteDB.set("date","yes");
//            localRemoteDB.set("probie",new Data().put("name","probie").put("age",18));
//            localRemoteDB.commit();
//            System.out.println(localRemoteDB.get("date"));
//            System.out.println(new Data().deCode(localRemoteDB.get("probie")));
//        }
//
//        远程数据库
//        RemoteDB remoteDB = EasyDB.getInstance().getRemoteDatabaseFactory().buildRemoteDB();
//        remoteDB.runPreparedStatementExecute("");
//
//        远程 Supabase 数据库
//        Supabase supabase = EasyDB.getInstance().getRemoteDatabaseFactory().buildSupabase();
//        supabase.runPreparedStatementUpdate("");
//
//        TODO
//
//
//    }

}