package com.probie.Database.Data;

import com.probie.Database.Data.Interface.ISerializeBase64;

public class SerializeBase64 implements ISerializeBase64 {

    private static SerializeBase64 INSTANCE = new SerializeBase64();

    public static SerializeBase64 getINSTANCE() {
        return INSTANCE;
    }

}