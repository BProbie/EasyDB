package com.probie.easydb.Database.Local.Interface;

import com.probie.easydb.Database.Local.LocalRemoteDB;

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
     * 导入数据文件
     * */
    Boolean load();

    /**
     * 提交数据
     * */
    Boolean commit();

    /**
     * 回滚到上一次提交
     * */
    Boolean rollback();

    /**
     * 目标数据库路径
     * */
    LocalRemoteDB setRemoteFilePath(String remoteFilePath);
    String getRemoteFilePath();

    /**
     * 设置、获取备注
     * */
    LocalRemoteDB setComment(String comment);
    String getComment();

}