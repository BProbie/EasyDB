package com.probie.Database.Local.Interface;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public interface ILocalDBProperties {

    /**
     * 维护一个全局PropertiesMap
     * */
    void setPropertiesMap(ConcurrentHashMap<String, Properties> propertiesMap);
    ConcurrentHashMap<String, Properties> getPropertiesMap();

    /**
     * 维护一个全局TempPropertiesMap
     * */
    void setTempPropertiesMap(ConcurrentHashMap<String, Properties> tempPropertiesMap);
    ConcurrentHashMap<String, Properties> getTempPropertiesMap();

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