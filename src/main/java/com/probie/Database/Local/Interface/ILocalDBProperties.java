package com.probie.Database.Local.Interface;

import java.util.Properties;

public interface ILocalDBProperties {

    /**
     * 设置/获取 维护的Properties对象
     * */
    void setProperties(Properties properties);
    Properties getProperties();

    /**
     * 设置/获取 维护的TempProperties对象
     * */
    void setTempProperties(Properties tempProperties);
    Properties getTempProperties();

}