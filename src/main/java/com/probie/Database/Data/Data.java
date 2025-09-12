package com.probie.Database.Data;

import java.util.Objects;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.Serializable;
import com.probie.Database.Data.Interface.IData;

public class Data implements IData, Serializable, Cloneable {

    private HashMap<Object, Object> hashMap = new HashMap<>();

    @Override
    public Data put(Object key, Object value) {
        getHashMap().put(key, value);
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
        return getHashMap().get(key);
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
        if (get(key) == null) {
            return defaultValue;
        }
        return get(key);
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
        for (Object key : keys) {
            getHashMap().remove(key);
        }
        return this;
    }

    @Override
    public Data remove(Object key, Object value) {
        getHashMap().remove(key, value);
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
        setHashMap(((Data) SerializeBase64.getINSTANCE().deSerializeFromBase64(object)).getHashMap());
        return this;
    }

    @Override
    public Data setHashMap(HashMap<Object, Object> hashMap) {
        this.hashMap = hashMap;
        return this;
    }

    @Override
    public HashMap<Object, Object> getHashMap() {
        return hashMap;
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
            return data.getHashMap().equals(getHashMap());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHashMap());
    }

    /**
     * 重构toString
     * */
    @Override
    public String toString() {
        return "Data{" +
                "hashMap=" + hashMap +
                '}';
    }

}