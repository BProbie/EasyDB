package com.probie.Database.Local.Interface;

import com.probie.Database.Local.LocalDB;

public interface ILocalDB {

    /**
     * 连接到数据库
     * */
    Boolean connect();

    /**
     * 提交数据
     * */
    Boolean commit();

    /**
     * 回滚到上一次提交
     * */
    Boolean rollback();

    /**
     * 自动提交
     * */
    LocalDB setIsAutoCommit(Boolean isAutoCommit);
    Boolean getIsAutoCommit();

    /**
     * 目标数据库路径
     * */
    LocalDB setFilePath(String filePath);
    String getFilePath();

    /**
     * 获取路径、文件名
     * */
    String getPath();
    String getFileName();

    /**
     * 设置、获取备注
     * */
    LocalDB setComment(String comment);
    String getComment();

}