package edu.iipw.pap.db;

import javafx.collections.ObservableSet;

/**
 * ObjectPool is an interface describing a single pool
 * that can provide, delete and save Model's instances.
 */
public interface ObjectPool {
    /**
     * Delete removes the provided from the underlying pool.
     */
    public <T> void delete(T obj);

    /**
     * Save ensure the object is persisted in the underlying pool:
     * If obj's primary key is already known - by ensuring the attributes
     * between the persisted entity and the provided obj match exactly;
     * or otherwise by creating an entirely new persisted instance.
     */
    public <T> void save(T obj);

    /**
     * Returns a set of all known entities of the provided type.
     */
    public <T> ObservableSet<T> listAll(Class<T> cls);
}
