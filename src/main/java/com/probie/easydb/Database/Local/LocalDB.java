package com.probie.easydb.Database.Local;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import com.probie.easydb.Database.Local.Interface.ILocalDB;

public class LocalDB extends LocalDatabase implements ILocalDB, Serializable, Closeable, Cloneable {

    private String comment = "A Local Database Of LocalDB Basic On Properties";

    public LocalDB() {
        setSynFilePath(getFilePath());
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
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