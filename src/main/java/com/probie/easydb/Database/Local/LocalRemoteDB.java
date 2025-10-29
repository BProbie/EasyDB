package com.probie.easydb.Database.Local;

import java.io.*;
import java.util.Properties;
import java.nio.charset.StandardCharsets;
import com.probie.easydb.Database.Local.Interface.ILocalRemoteDB;

public class LocalRemoteDB extends LocalDatabase implements ILocalRemoteDB, Serializable, Closeable, Cloneable {

    private String remoteFilePath;
    private String comment = "A Local Database Of LocalRemoteDB Basic On Properties";

    public LocalRemoteDB(String remoteFilePath) {
        setSynFilePath(getFilePath());
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
        if (!getIsConnection()) {
            if (getPropertiesMap().get(getSynFilePath()) == null) {
                return load();
            }
        }
        return true;
    }

    @Override
    public Boolean load() {
        try {
            getReadLock().lock();
            getProperties().load(new InputStreamReader(new FileInputStream(getFilePath()), StandardCharsets.UTF_8));
            setIsConnection(true);
        } catch (IOException ignored) {

        } finally {
            getReadLock().unlock();
        }
        return new File(getFilePath()).exists();
    }

    @Override
    public Boolean commit() {
        setTempProperties(getProperties());
        try {
            getWriteLock().lock();
            getProperties().store(new FileWriter(getFilePath()), getComment());
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        } finally {
            getWriteLock().unlock();
        }
        return new File(getFilePath()).exists();
    }

    @Override
    public Boolean rollback() {
        getWriteLock().lock();
        Properties temp = getTempProperties();
        setTempProperties(getProperties());
        setProperties(temp);
        commit();
        getWriteLock().unlock();
        return true;
    }

    @Override
    public LocalRemoteDB setRemoteFilePath(String remoteFilePath) {
        if (!getRemoteFilePath().equals(remoteFilePath)) {
            this.remoteFilePath = remoteFilePath;
            setIsConnection(false);
        }
        return this;
    }

    @Override
    public String getRemoteFilePath() {
        return remoteFilePath;
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
            setIsConnection(false);
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