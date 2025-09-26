package com.probie.Database.Local.Interface;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public interface ILocalDBProperties {

    /**
     * 维护一个全局 PropertiesMap
     * */
    ConcurrentHashMap<String, Properties> getPropertiesMap();

    /**
     * 维护一个全局 TempPropertiesMap
     * */
    ConcurrentHashMap<String, Properties> getTempPropertiesMap();

    /**
     * 设置/获取 维护的 Properties 对象
     * */
    void setProperties(Properties properties);
    Properties getProperties();

    /**
     * 设置/获取 维护的 TempProperties 对象
     * */
    void setTempProperties(Properties tempProperties);
    Properties getTempProperties();

}