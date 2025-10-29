package com.probie.easydb.Database.Local;

import java.io.*;
import java.util.Properties;
import java.nio.charset.StandardCharsets;
import com.probie.easydb.Database.Local.Interface.ILocalRemoteDB;

public class LocalRemoteDB extends LocalDatabase implements ILocalRemoteDB, Serializable, Closeable, Cloneable {

    private String remoteFilePath;
    private String comment = "A Local Database Of LocalRemoteDB Basic On Properties";

    public LocalRemoteDB(String remoteFilePath) {

        setSynFullFilePath(getFullFilePath());
        this.remoteFilePath = remoteFilePath;
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public Boolean downloadDatabase() {
        return downloadDatabase(false);
    }

    @Override
    public Boolean downloadDatabase(Boolean isReDownload) {
        if (new File(getFullFilePath()).exists()) {
            if (!isReDownload) {
                return true;
            }
        }
        getWriteLock().lock();
        Boolean isDownload = downloadRemoteFile(getRemoteFilePath(), getFullFilePath());
        getWriteLock().unlock();
        return isDownload;
    }

    @Override
    public Boolean connect() {
        try {
            return connect(new InputStreamReader(new FileInputStream(getFullFilePath()), StandardCharsets.UTF_8));
        } catch (FileNotFoundException fileNotFoundException) {
            throw new RuntimeException(fileNotFoundException);
        }
    }

    @Override
    public Boolean connect(InputStream inputStream) {
        if (!getIsConnection()) {
            if (getPropertiesMap().get(getSynFullFilePath()) == null) {
                return load(inputStream);
            }
        }
        return true;
    }

    @Override
    public Boolean connect(InputStreamReader inputStreamReader) {
        if (!getIsConnection()) {
            if (getPropertiesMap().get(getSynFullFilePath()) == null) {
                return load(inputStreamReader);
            }
        }
        return true;
    }

    @Override
    public Boolean load() {
        try {
            return load(new InputStreamReader(new FileInputStream(getFullFilePath()), StandardCharsets.UTF_8));
        } catch (FileNotFoundException fileNotFoundException) {
            throw new RuntimeException(fileNotFoundException);
        }
    }

    @Override
    public Boolean load(InputStream inputStream) {
        try {
            getReadLock().lock();
            getProperties().load(new InputStreamReader(new FileInputStream(getFullFilePath()), StandardCharsets.UTF_8));
            setIsConnection(true);
        } catch (IOException ignored) {

        } finally {
            getReadLock().unlock();
        }
        return getIsConnection();
    }

    @Override
    public Boolean load(InputStreamReader inputStreamReader) {
        try {
            getReadLock().lock();
            getProperties().load(new InputStreamReader(new FileInputStream(getFullFilePath()), StandardCharsets.UTF_8));
            setIsConnection(true);
        } catch (IOException ignored) {

        } finally {
            getReadLock().unlock();
        }
        return new File(getFullFilePath()).exists();
    }

    @Override
    public Boolean commit() {
        setTempProperties(getProperties());
        try {
            getWriteLock().lock();
            getProperties().store(new FileWriter(getFullFilePath()), getComment());
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        } finally {
            getWriteLock().unlock();
        }
        return new File(getFullFilePath()).exists();
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
        return new File(getFullFilePath()).getName();
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