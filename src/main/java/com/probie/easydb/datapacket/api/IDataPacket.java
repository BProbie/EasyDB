package com.probie.easydb.datapacket.api;

import java.util.concurrent.locks.Lock;

public interface IDataPacket {

    /**
     * 获取读锁和写锁
     * */
    Lock getReadLock();
    Lock getWriteLock();

}