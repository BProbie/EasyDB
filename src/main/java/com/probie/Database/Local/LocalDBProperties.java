package com.probie.Database.Local;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import com.probie.Database.Local.Interface.ILocalDBProperties;

public class LocalDBProperties extends LocalDBFile implements ILocalDBProperties {

    /**
     * @param String 路径
     * @param Properties 配置文件实例
     * */
    private static ConcurrentHashMap<String, Properties> propertiesMap = new ConcurrentHashMap<>();

    /**
     * @param String 路径
     * @param Properties 缓存配置文件实例
     * */
    private static ConcurrentHashMap<String, Properties> tempPropertiesMap = new ConcurrentHashMap<>();

    private Properties properties = new Properties();
    private Properties tempProperties = new Properties();

    @Override
    public void setPropertiesMap(ConcurrentHashMap<String, Properties> propertiesMap) {
        LocalDBProperties.propertiesMap = propertiesMap;
    }

    @Override
    public ConcurrentHashMap<String, Properties> getPropertiesMap() {
        return propertiesMap;
    }

    @Override
    public void setTempPropertiesMap(ConcurrentHashMap<String, Properties> tempPropertiesMap) {
        LocalDBProperties.tempPropertiesMap = tempPropertiesMap;
    }

    @Override
    public ConcurrentHashMap<String, Properties> getTempPropertiesMap() {
        return tempPropertiesMap;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = (Properties) properties.clone();
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public void setTempProperties(Properties tempProperties) {
        this.tempProperties = (Properties) tempProperties.clone();
    }

    @Override
    public Properties getTempProperties() {
        return tempProperties;
    }

}