package com.probie.Database.Local;

import java.util.Properties;
import com.probie.Database.Local.Interface.ILocalDBProperties;

public class LocalDBProperties extends LocalDBFile implements ILocalDBProperties {

    private Properties properties = new Properties();
    private Properties tempProperties = new Properties();

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