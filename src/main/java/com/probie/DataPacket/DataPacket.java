package com.probie.DataPacket;

import java.util.HashMap;
import java.io.Serializable;
import com.probie.DataPacket.Interface.IDataPacket;

public class DataPacket implements IDataPacket, Serializable {

    protected HashMap<Object, Object> hashMap = new HashMap<>();

}