package com.probie.easydb.database.local.api;

public interface ILocalDBComputer {

    /**
     * 获取当前路径
     * */
    default String getCurrentPath() {
        return System.getProperty("user.dir");
    }

}