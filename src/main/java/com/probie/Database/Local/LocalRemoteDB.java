package com.probie.Database.Local;

import java.io.*;
import java.nio.charset.StandardCharsets;
import com.probie.Database.Local.Interface.ILocalRemoteDB;

public class LocalRemoteDB extends LocalDatabase implements ILocalRemoteDB, Serializable, Closeable, Cloneable {

    private Boolean isAutoCommit = true;

    private String remoteFilePath;
    private String filePath = getCurrentPath()+"\\"+"LocalRemoteDB.properties";
    private String comment = "A Local Database Of LocalRemoteDB Basic On Properties";

    public LocalRemoteDB(String remoteFilePath) {
        this.remoteFilePath = remoteFilePath;
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public Boolean downloadDatabase() {
        return downloadDatabase(false);
    }

    @Override
    public Boolean downloadDatabase(Boolean isReDownload) {
        if (new File(getFilePath()).exists()) {
            if (!isReDownload) {
                return true;
            }
        }
        getWriteLock().lock();
        Boolean isDownload = downloadRemoteFile(getRemoteFilePath(), getFilePath());
        getWriteLock().unlock();
        return isDownload;
    }

    @Override
    public Boolean connect() {
        try {
            getReadLock().lock();
            setSynFilePath(getRemoteFilePath());
            getProperties().load(new InputStreamReader(new FileInputStream(getFilePath()), StandardCharsets.UTF_8));
            getReadLock().unlock();
        } catch (IOException ignored) {}
        return new File(getFilePath()).exists();
    }

    @Override
    public Boolean commit() {
        setTempProperties(getProperties());
        try {
            getWriteLock().lock();
            getProperties().store(new FileWriter(getFilePath()), getComment());
            getWriteLock().unlock();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        return new File(getFilePath()).exists();
    }

    @Override
    public Boolean backroll() {
        getWriteLock().lock();
        setProperties(getTempProperties());
        getWriteLock().unlock();
        return true;
    }

    @Override
    public LocalRemoteDB setIsAutoCommit(Boolean isAutoCommit) {
        this.isAutoCommit = isAutoCommit;
        return this;
    }

    @Override
    public Boolean getIsAutoCommit() {
        return isAutoCommit;
    }

    @Override
    public LocalRemoteDB setRemoteFilePath(String remoteFilePath) {
        this.remoteFilePath = remoteFilePath;
        return this;
    }

    @Override
    public String getRemoteFilePath() {
        return remoteFilePath;
    }

    @Override
    public LocalRemoteDB setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public String getPath() {
        return new File(getFilePath()).getParentFile().getAbsolutePath();
    }

    @Override
    public String getFileName() {
        return new File(getFilePath()).getName();
    }

    @Override
    public LocalRemoteDB setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public String getComment() {
        return comment;
    }


    @Override
    public void close() {
        if (getIsAutoCommit()) {
            commit();
        }
    }

    @Override
    protected LocalRemoteDB clone() {
        try {
            return (LocalRemoteDB) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

}