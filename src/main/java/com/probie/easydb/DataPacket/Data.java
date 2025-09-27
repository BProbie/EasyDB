package com.probie.easydb.DataPacket;

import java.util.Objects;
import java.util.ArrayList;
import com.probie.easydb.DataPacket.Interface.IData;
import java.util.concurrent.ConcurrentHashMap;

public class Data extends DataPacket implements IData, Cloneable {

    @Override
    public Data put(Object key, Object value) {
        getWriteLock().lock();
        getMap().put(key, value);
        getWriteLock().unlock();
        return this;
    }

    @Override
    public Data put(Object[] keys, Object value) {
        for (int i = 0; i < keys.length; i++) {
            put(keys[i], value);
        }
        return this;
    }

    @Override
    public Data put(Object[] keys, Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            put(keys[i], values[i]);
        }
        return this;
    }

    @Override
    public Object get(Object key) {
        getReadLock().lock();
        Object value = getMap().get(key);
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
        Object value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
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
    public Data remove(Object... keys) {
        getWriteLock().lock();
        for (Object key : keys) {
            getMap().remove(key);
        }
        getWriteLock().unlock();
        return this;
    }

    @Override
    public Data remove(Object key, Object value) {
        getWriteLock().lock();
        getMap().remove(key, value);
        getWriteLock().unlock();
        return this;
    }

    @Override
    public Data remove(Object[] keys, Object value) {
        for (Object key : keys) {
            remove(key, value);
        }
        return this;
    }

    @Override
    public Data remove(Object[] keys, Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            remove(keys[i], values[i]);
        }
        return this;
    }

    @Override
    public Object enCode() {
        return SerializeBase64.getINSTANCE().enSerializeToBase64(this);
    }

    @Override
    public Data deCode(Object object) {
        setMap(((Data) SerializeBase64.getINSTANCE().deSerializeFromBase64(object)).getMap());
        return this;
    }

    @Override
    public Data setMap(ConcurrentHashMap<Object, Object> concurrentHashMap) {
        this.concurrentHashMap = concurrentHashMap;
        return this;
    }

    @Override
    public ConcurrentHashMap<Object, Object> getMap() {
        return concurrentHashMap;
    }

    @Override
    protected Data clone() {
        try {
            return (Data) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

    /**
     * 重构Hash值判断逻辑
     * */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Data data) {
            return data.getMap().equals(getMap());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMap());
    }

    /**
     * 重构toString
     * */
    @Override
    public String toString() {
        return "Data{" +
                "concurrentHashMap=" + getMap() +
                '}';
    }

}