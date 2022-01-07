package edu.iipw.pap.db;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableSet;

/**
 * ObjectPool is an interface describing a single pool
 * that can provide, delete and save Model's instances.
 */
public abstract class ObjectPool {
    protected List<Object> markedToSave = new ArrayList<>();
    protected List<Object> markedToDelete = new ArrayList<>();

    /**
     * Delete removes the provided from the underlying pool.
     */
    public abstract <T> void delete(T obj);

    /**
     * Save ensure the object is persisted in the underlying pool:
     * If obj's primary key is already known - by ensuring the attributes
     * between the persisted entity and the provided obj match exactly;
     * or otherwise by creating an entirely new persisted instance.
     */
    public abstract <T> void save(T obj);

    /**
     * BatchSave should perform the same operation as would save on every provided
     * object, except that probably in a more efficient manner.
     */
    public abstract void batchSave(Object... objs);

    /**
     * BatchDelete should perform the same operation as would delete on every
     * provided object, except that probably in a more efficient manner.
     */
    public abstract void batchDelete(Object... objs);

    /**
     * BatchSaveAndDelete performs both BatchSave and BatchDelete in one go -
     * not only for performance gains - but this is also **required** when
     * removing an object requires updating a separate object.
     */
    public abstract void batchSaveAndDelete(Object[] toSave, Object[] toDelete);

    /**
     * Returns a set of all known entities of the provided type.
     */
    public abstract <T> ObservableSet<T> listAll(Class<T> cls);

    /**
     * CommitMarked performs a batch update of all the objects that
     * were previously marked as to-delete or to-save.
     */
    public void commitMarked() {
        assert this.markedToSave.stream()
            .filter(o -> this.markedToDelete.contains(o))
            .count() == 0;

        this.batchSaveAndDelete(this.markedToSave.toArray(), this.markedToDelete.toArray());
        this.markedToSave.clear();
        this.markedToDelete.clear();
    }

    /**
     * Marks an object for future save (with commitMarked).
     * Provided object cannot have been marked as to-delete.
     */
    public void markToSave(Object obj) {
        assert !this.markedToDelete.contains(obj);
        this.markedToSave.add(obj);
    }

    /**
     * Marks an object for future deletion (with commitMarked).
     * If the provided object has been marked to-save
     * - it won't be saved anymore.
     */
    public void markToDelete(Object obj) {
        this.markedToSave.remove(obj);
        this.markedToDelete.add(obj);
    }
}
