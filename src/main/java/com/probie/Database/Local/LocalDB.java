package com.probie.Database.Local;

import java.io.*;
import java.nio.charset.StandardCharsets;
import com.probie.Database.Local.Interface.ILocalDB;

public class LocalDB extends LocalDatabase implements ILocalDB, Serializable, Closeable, Cloneable {

    private Boolean isAutoCommit = true;
    private Boolean isConnection = false;

    private String filePath = getCurrentPath()+"\\"+"LocalDB.properties";
    private String comment = "A Local Database Of LocalDB Basic On Properties";

    public LocalDB() {
        setSynFilePath(getFilePath());
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public Boolean connect() {
        if (!getIsConnection()) return load();
        return false;
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
        setProperties(getTempProperties());
        getWriteLock().unlock();
        return true;
    }

    @Override
    public LocalDB setIsAutoCommit(Boolean isAutoCommit) {
        this.isAutoCommit = isAutoCommit;
        return this;
    }

    @Override
    public Boolean getIsAutoCommit() {
        return isAutoCommit;
    }

    @Override
    public LocalDB setIsConnection(Boolean isConnection) {
        this.isConnection = isConnection;
        return this;
    }

    @Override
    public Boolean getIsConnection() {
        return isConnection;
    }

    @Override
    public LocalDB setFilePath(String filePath) {
        if (!getFileName().equals(filePath)) {
            setIsConnection(false);
            this.filePath = filePath;
            setSynFilePath(getFilePath());
        }
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
    public LocalDB setComment(String comment) {
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
    protected LocalDB clone() {
        try {
            return (LocalDB) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

}