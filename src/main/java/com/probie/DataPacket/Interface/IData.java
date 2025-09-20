package com.probie.DataPacket.Interface;

import com.probie.DataPacket.Data;
import java.util.concurrent.ConcurrentHashMap;

public interface IData {

    /**
     * 添加数据
     * */
    Data put(Object key, Object value);
    Data put(Object[] keys, Object value);
    Data put(Object[] keys, Object[] values);

    /**
     * 获取数据
     * */
    Object get(Object key);
    Object[] get(Object... keys);

    /**
     * 获取数据，若不存在则返回默认数据
     * */
    Object get(Object key, Object defaultValue);
    Object[] get(Object[] keys, Object defaultValue);
    Object[] get(Object[] keys, Object[] defaultValues);

    /**
     * 删除数据
     * */
    Data remove(Object... keys);
    Data remove(Object key, Object value);
    Data remove(Object[] keys, Object value);
    Data remove(Object[] keys, Object[] values);

    /**
     * 对象加编码解码
     * */
    Object enCode();
    Data deCode(Object object);

    /**
     * 设置/获取 维护的 ConcurrentHashMap
     * */
    Data setMap(ConcurrentHashMap<Object, Object> concurrentHashMap);
    ConcurrentHashMap<Object, Object> getMap();

}