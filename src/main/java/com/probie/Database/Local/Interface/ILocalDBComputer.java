package com.probie.Database.Local.Interface;

public interface ILocalDBComputer {

    /**
     * 获取当前路径
     * */
    default String getCurrentPath() {
        return System.getProperty("user.dir");
    }

}