package com.probie.easydb.database.local.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import com.probie.easydb.database.local.LocalDB;

public interface ILocalDB {

    /**
     * 连接到数据库
     * */
    Boolean connect();
    Boolean connect(InputStream inputStream);
    Boolean connect(InputStreamReader inputStreamReader);

    /**
     * 导入配置文件
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
     * 设置、获取备注
     * */
    LocalDB setComment(String comment);
    String getComment();

}