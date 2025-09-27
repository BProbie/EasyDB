package com.probie.easydb.Database.Local.Interface;

public interface ILocalDBComputer {

    /**
     * 获取当前路径
     * */
    default String getCurrentPath() {
        return System.getProperty("user.dir");
    }

}