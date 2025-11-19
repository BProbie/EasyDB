package com.probie.easydb.Database.Local.Interface;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.nio.file.StandardCopyOption;

public interface ILocalDBRemote {

    /**
     * 设置代理连接
     * */
    void connectProxy();

    /**
     * 下载远程文件
     * @param remoteFile 远程文件路径
     * @param localFile 本地目录或文件路径，可为String绝对路径，也可为File对象
     * */
    default Boolean downloadRemoteFile(Object remoteFile, Object localFile) {
        // 获取远程数据，存入缓存
        try (InputStream inputStream = new URL(remoteFile.toString()).openConnection().getInputStream()) {
            // 获取文件对象
            File file = localFile instanceof File? (File) localFile : new File(localFile.toString());

            // 创建路径
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // 下载文件
            if (file.getParentFile().exists()) {
                try {
                    System.out.println(file.getAbsolutePath());
                    Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ioException) {
                    throw new RuntimeException(ioException);
                }
            }

            return file.exists();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * 读取远程文件内容
     * @param remoteFile 远程文件路径
     * */
    default Object[] readRemoteFile(Object remoteFile) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(remoteFile.toString()).openConnection().getInputStream()))) {
            ArrayList<Object> values = new ArrayList<>();
            Object value;
            while (true) {
                try {
                    if ((value = bufferedReader.readLine()) == null) break;
                } catch (IOException ioException) {
                    throw new RuntimeException(ioException);
                }
                values.add(value);
            }

            return values.toArray();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

}