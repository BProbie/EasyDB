package com.probie.easydb.DataPacket;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.probie.easydb.DataPacket.Interface.IDataPacket;

public class DataPacket implements IDataPacket, Serializable {

    /**
     * @param Object 任意 key
     * @param Object 任意 Value
     * */
    protected volatile ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();

    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = reentrantReadWriteLock.readLock();
    private final Lock writeLock = reentrantReadWriteLock.writeLock();

    public Lock getReadLock() {
        return readLock;
    }

    public Lock getWriteLock() {
        return writeLock;
    }

}