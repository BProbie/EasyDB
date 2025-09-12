package com.probie.Database.Local.Interface;

import com.probie.Database.Local.LocalRemoteDB;

public interface ILocalRemoteDB {

    /**
     * 下载数据库到本地
     * */
    Boolean downloadDatabase();
    Boolean downloadDatabase(Boolean isReDownload);

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
    Boolean backroll();

    /**
     * 自动提交
     * */
    LocalRemoteDB setIsAutoCommit(Boolean isAutoCommit);
    Boolean getIsAutoCommit();

    /**
     * 目标数据库路径
     * */
    LocalRemoteDB setRemoteFilePath(String remoteFilePath);
    String getRemoteFilePath();

    /**
     * 保存数据库路径
     * */
    LocalRemoteDB setFilePath(String filePath);
    String getFilePath();

    /**
     * 获取路径、文件名
     * */
    String getPath();
    String getFileName();

    /**
     * 设置、获取备注
     * */
    LocalRemoteDB setComment(String comment);
    String getComment();

}