package com.probie.Database.Local.Interface;

import java.io.*;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;

public interface ILocalDBFile {

    /**
     * 查看路径是否存在
     * @param targetPath 目标路径，可为String绝对路径，也可为File对象
     * */
    default Boolean isPathExists(Object targetPath) {
        if (targetPath instanceof File file) {
            return file.exists();
        } else {
            return new File(targetPath.toString()).exists();
        }
    }

    /**
     * 查看文件是否存在
     * @param targetFile 目标文件，可为String绝对路径，也可为File对象
     * */
    default Boolean isFileExists(Object targetFile) {
        if (targetFile instanceof File file) {
            return file.exists();
        } else {
            return new File(targetFile.toString()).exists();
        }
    }

    /**
     * 创建文件路径
     * @param targetPath 目标路径，可为String绝对路径，也可为File对象
     * */
    default Boolean createPath(Object targetPath) {
        if (targetPath instanceof File file) {
            if (!file.exists()) {
                return file.mkdirs() && file.exists();
            } else {
                return true;
            }
        } else {
            File file = new File(targetPath.toString());
            if (!file.exists()) {
                return file.mkdirs() && file.exists();
            } else {
                return true;
            }
        }
    }

    /**
     * 创建文件
     * @param targetFile 目标文件，可为String绝对路径，也可为File对象
     * */
    default Boolean createFile(Object targetFile) {
        if (targetFile instanceof File file) {
            if (!file.exists()) {
                if (createPath(file.getParentFile())) {
                    try {
                        return file.createNewFile() && file.exists();
                    } catch (IOException ioException) {
                        throw new RuntimeException(ioException);
                    }
                }
            } else {
                return true;
            }
        } else {
            File file = new File(targetFile.toString());
            if (!file.exists()) {
                if (createPath(file.getParentFile())) {
                    try {
                        return file.createNewFile() && file.exists();
                    } catch (IOException ioException) {
                        throw new RuntimeException(ioException);
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除文件
     * @param targetFile 目标文件，可为String绝对路径，也可为File对象
     * */
    default Boolean deleteFile(Object targetFile) {
        if (targetFile instanceof File file) {
            if (file.exists()) {
                return file.delete() && !file.exists();
            } else {
                return true;
            }
        } else {
            File file = new File(targetFile.toString());
            if (file.exists()) {
                return file.delete() && !file.exists();
            } else {
                return true;
            }
        }
    }

    /**
     * 清空文件
     * @param targetFile 目标文件，可为String绝对路径，也可为File对象
     * */
    default Boolean clearFile(Object targetFile) {
        return deleteFile(targetFile) && createFile(targetFile);
    }

    /**
     * 读取文件内容
     * @param targetFile 目标文件，可为String绝对路径，也可为File对象
     * */
    default Object[] readFile(Object targetFile) {
        String path;
        if (targetFile instanceof File file) {
            path = file.getAbsolutePath();
        } else {
            path = targetFile.toString();
        }

       try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
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

    /**
     * 写入文件内容
     * @param targetFile 目标文件，可为String绝对路径，也可为File对象
     * */
    default Boolean writeFile(Object targetFile, Object value) {
        File file;
        if (targetFile instanceof File temp) {
            file = temp;
        } else {
            file = new File(targetFile.toString());
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }

        if (file.exists()) {
            try (OutputStream outputStream = new FileOutputStream(targetFile.toString())) {
                byte[] bytes = value.toString().getBytes(StandardCharsets.UTF_8);
                try {
                    outputStream.write(bytes);
                    outputStream.close();
                } catch (IOException ioException) {
                    throw new RuntimeException(ioException);
                }
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }

        return file.exists();
    }

}