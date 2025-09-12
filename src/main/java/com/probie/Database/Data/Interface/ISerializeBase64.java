package com.probie.Database.Data.Interface;

import java.io.*;
import java.util.Base64;

public interface ISerializeBase64 {

    /**
     * 加密: Object -> 序列化 -> Base64加密 -> 转String
     * @param object 任意对象
     * */
    default Object enSerializeToBase64(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(object);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    /**
     * 解密: String -> Byte -> Base64解密 -> 反序列化 -> Object
     * @param object 任意数据
     * */
    default Object deSerializeFromBase64(Object object) {
        byte[] bytes = Base64.getDecoder().decode(object.toString());
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

}