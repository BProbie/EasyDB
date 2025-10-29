package com.probie.easydb.Database.Local.Interface;

import java.io.InputStream;
import java.io.InputStreamReader;
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
    Boolean connect(InputStream inputStream);
    Boolean connect(InputStreamReader inputStreamReader);

    /**
     * 导入数据文件
     * */
    Boolean load();
    Boolean load(InputStream inputStream);
    Boolean load(InputStreamReader inputStreamReader);

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