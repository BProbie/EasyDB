package com.probie.easydb.Database.Local;

import java.io.*;
import java.util.Properties;
import java.nio.charset.StandardCharsets;
import com.probie.easydb.Database.Local.Interface.ILocalDB;

public class LocalDB extends LocalDatabase implements ILocalDB, Serializable, Closeable, Cloneable {

    private String comment = "A Local Database Of LocalDB From EasyDB Basic On Properties";

    public LocalDB() {
        setSynFullFilePath(getFullFilePath());
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public Boolean connect() {
        try {
            return connect(new InputStreamReader(new FileInputStream(getFullFilePath()), StandardCharsets.UTF_8));
        } catch (FileNotFoundException ignored) {}
        return false;
    }

    @Override
    public Boolean connect(InputStream inputStream) {
        if (!getIsConnection()) {
            if (getPropertiesMap().get(getSynFullFilePath()) == null) {
                return load(inputStream);
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ignored) {}
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
        if (inputStreamReader != null) {
            try {
                inputStreamReader.close();
            } catch (IOException ignored) {}
        }
        return true;
    }

    @Override
    public Boolean load() {
        try {
            return load(new InputStreamReader(new FileInputStream(getFullFilePath()), StandardCharsets.UTF_8));
        } catch (FileNotFoundException ignored) {}
        return false;
    }

    @Override
    public Boolean load(InputStream inputStream) {
        try {
            getReadLock().lock();
            getProperties().load(inputStream);
            setIsConnection(true);
        }
        catch (IOException ignored) {}
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {}
            }
            getReadLock().unlock();
        }
        return getIsConnection();
    }

    @Override
    public Boolean load(InputStreamReader inputStreamReader) {
        try {
            getReadLock().lock();
            getProperties().load(inputStreamReader);
            setIsConnection(true);
        }
        catch (IOException ignored) {}
        finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException ignored) {}
            }
            getReadLock().unlock();
        }
        return new File(getFullFilePath()).exists();
    }

    @Override
    public Boolean commit() {
        setTempProperties(getProperties());
        try (FileWriter fileWriter = new FileWriter(getFullFilePath())) {
            getWriteLock().lock();
            getProperties().store(fileWriter, getComment());
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        } finally {
            getWriteLock().unlock();
        }
        return new File(getFullFilePath()).exists();
    }

    @Override
    public Boolean rollback() {
        Properties temp = getTempProperties();
        setTempProperties(getProperties());
        setProperties(temp);
        commit();
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