package com.probie.easydb.Database.Local;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import com.probie.easydb.DataPacket.Data;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.probie.easydb.Database.Local.Interface.ILocalDatabase;

public class LocalDatabase extends LocalDBProperties implements ILocalDatabase {

    private final static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final static Lock readLock = reentrantReadWriteLock.readLock();
    private final static Lock writeLock = reentrantReadWriteLock.writeLock();

    private Boolean isConnection = false;
    private Boolean isAutoCommit = true;

    private String fullFilePath = getCurrentPath()+File.separator+"LocalDB.properties";
    private String synFullFilePath;
    private String split = ", ";

    @Override
    public void set(Object key, Object value) {
        if (value instanceof Data) {
            value = ((Data) value).enCode();
        }
        getWriteLock().lock();
        getProperties().setProperty(key.toString(), value.toString());
        getWriteLock().unlock();
    }

    @Override
    public void set(Object[] keys, Object value) {
        for (Object key : keys) {
            set(key, value);
        }
    }

    @Override
    public void set(Object key, Object[] values) {
        if (values.length == 1) {
            set(key, values[0]);
        } else {
            Object value = "";
            for (Object object : values) {
                value += object + split;
            }
            set(key, value);
        }
    }

    @Override
    public void set(Object[] keys, Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            set(keys[i], values[i]);
        }
    }

    @Override
    public Object get(Object key) {
        getReadLock().lock();
        Object value = getProperties().get(key);
        getReadLock().unlock();
        return value;
    }

    @Override
    public Object[] get(Object... keys) {
        ArrayList<Object> arrayList = new ArrayList<>();
        for (Object key : keys) {
            arrayList.add(get(key));
        }
        return arrayList.toArray();
    }

    @Override
    public Object get(Object key, Object defaultValue) {
        return getProperties().getOrDefault(key, defaultValue);
    }

    @Override
    public Object[] get(Object[] keys, Object defaultValue) {
        ArrayList<Object> arrayList = new ArrayList<>();
        for (Object key : keys) {
            arrayList.add(get(key, defaultValue));
        }
        return arrayList.toArray();
    }

    @Override
    public Object[] get(Object[] keys, Object[] defaultValues) {
        ArrayList<Object> arrayList = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            arrayList.add(get(keys[i], defaultValues[i]));
        }
        return arrayList.toArray();
    }

    @Override
    public void add(Object key, Object value) {
        if (value instanceof Data) {
            value = ((Data) value).enCode();
        }
        Object oldValue = get(key, "");
        if (oldValue.equals("")) {
            set(key, value);
        } else {
            set(key, oldValue + split + value);
        }
    }

    @Override
    public void add(Object[] keys, Object value) {
        for (Object key : keys) {
            add(key, value);
        }
    }

    @Override
    public void add(Object key, Object[] values) {
        for (Object value : values) {
            add(key, value);
        }
    }

    @Override
    public void add(Object[] keys, Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            add(keys[i], values[i]);
        }
    }

    @Override
    public void remove(Object... keys) {
        for (Object key : keys) {
            getProperties().remove(key);
        }
    }

    @Override
    public Boolean remove(Object key, Object value) {
        getWriteLock().lock();
        Boolean isRemove = getProperties().remove(key, value);
        getWriteLock().unlock();
        return isRemove;
    }

    @Override
    public Boolean[] remove(Object[] keys, Object value) {
        ArrayList<Boolean> arrayList = new ArrayList<>();
        for (Object key : keys) {
            arrayList.add(remove(key, value));
        }
        return arrayList.toArray(new Boolean[0]);
    }

    @Override
    public Boolean[] remove(Object key, Object[] values) {
        ArrayList<Boolean> arrayList = new ArrayList<>();
        for (Object value : values) {
            arrayList.add(remove(key, value));
        }
        return arrayList.toArray(new Boolean[0]);
    }

    @Override
    public Boolean[] remove(Object[] keys, Object[] values) {
        ArrayList<Boolean> arrayList = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            arrayList.add(remove(keys[i], values[i]));
        }
        return arrayList.toArray(new Boolean[0]);
    }

    @Override
    public void clear() {
        getWriteLock().lock();
        getProperties().clear();
        getWriteLock().unlock();
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        getPropertiesMap().put(getSynFullFilePath(), properties);
    }

    @Override
    public Properties getProperties() {
        Properties properties = getPropertiesMap().get(getSynFullFilePath());
        if (properties == null) {
            properties = new Properties();
            getPropertiesMap().put(getSynFullFilePath(), properties);
        }
        return properties;
    }

    @Override
    public void setTempProperties(Properties tempProperties) {
        super.setProperties(tempProperties);
        getPropertiesMap().put(getSynFullFilePath(), tempProperties);
    }

    @Override
    public Properties getTempProperties() {
        Properties tempProperties = getTempPropertiesMap().get(getSynFullFilePath());
        if (tempProperties == null) {
            tempProperties = new Properties();
            getTempPropertiesMap().put(getSynFullFilePath(), tempProperties);
        }
        return tempProperties;
    }

    @Override
    public Lock getReadLock() {
        return readLock;
    }

    @Override
    public Lock getWriteLock() {
        return writeLock;
    }

    @Override
    public void setIsConnection(Boolean isConnection) {
        this.isConnection = isConnection;
    }

    @Override
    public Boolean getIsConnection() {
        return isConnection;
    }

    @Override
    public void setIsAutoCommit(Boolean isAutoCommit) {
        this.isAutoCommit = isAutoCommit;
    }

    @Override
    public Boolean getIsAutoCommit() {
        return isAutoCommit;
    }

    @Override
    public void setFullFilePath(String filePath) {
        if (!getFileName().equals(filePath)) {
            setIsConnection(false);
            this.fullFilePath = filePath;
            setSynFullFilePath(getFullFilePath());
        }
    }

    @Override
    public String getFullFilePath() {
        return fullFilePath;
    }

    @Override
    public String getPath() {
        return new File(getFullFilePath()).getParentFile().getAbsolutePath();
    }

    @Override
    public String getFileName() {
        return new File(getFullFilePath()).getName();
    }

    @Override
    public void setSynFullFilePath(String synFilePath) {
        this.synFullFilePath = synFilePath;
    }

    @Override
    public String getSynFullFilePath() {
        return synFullFilePath;
    }

    @Override
    public void setSplit(String split) {
        this.split = split;
    }

    @Override
    public String getSplit() {
        return split;
    }

}