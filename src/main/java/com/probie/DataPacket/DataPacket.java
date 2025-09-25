package com.probie.DataPacket;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.ConcurrentHashMap;
import com.probie.DataPacket.Interface.IDataPacket;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataPacket implements IDataPacket, Serializable {

    /**
     * @param Object 任意key
     * @param Object 任意Value
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