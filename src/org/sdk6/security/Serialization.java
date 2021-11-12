package org.sdk6.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Serialization<T> {
    private String path;
    T object;

    /**
     * The Serialization constructor.
     * @param path File path from hard disk.
     * @param object The object type class.
     */
    public Serialization(String path, T object) {
        this.path = path;
        this.object = object;
    }

    /**
     * Check if the current object is serializable or not.
     * @return True if it is serializable and false if not.
     */
    public boolean isSerialized() {
        return object instanceof Serializable;
    }

    /**
     * Serialize this object class in a file.
     * @return True if it is operation was successful and false if not.
     */
    public boolean serialize() {
        if (Objects.isNull(object)) {
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deserialize a class from file in computer.
     * @return The object class in the file.
     */
    @SuppressWarnings("unchecked")
    public T deSerialize() {
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the  path of file from hard disk.
     * @return The path of file.
     */
    public String getPath() {
        return path;
    }

    /**
     * Set new file path.
     * @param path The file path.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Get the object property in this class.
     * @return Object property in this class.
     */
    public T getObject() {
        return object;
    }

    /**
     * Set object property.
     * @param object The object property.
     */
    public void setObject(T object) {
        this.object = object;
    }

}
