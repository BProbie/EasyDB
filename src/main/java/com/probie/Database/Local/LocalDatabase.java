package com.probie.Database.Local;

import java.util.ArrayList;
import com.probie.Database.Data.Data;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.probie.Database.Local.Interface.ILocalDatabase;

public class LocalDatabase extends LocalDBProperties implements ILocalDatabase {

    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = reentrantReadWriteLock.readLock();
    private final Lock writeLock = reentrantReadWriteLock.writeLock();

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
    public Lock getReadLock() {
        return readLock;
    }

    @Override
    public Lock getWriteLock() {
        return writeLock;
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