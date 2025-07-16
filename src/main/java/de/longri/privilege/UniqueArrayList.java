package de.longri.privilege;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * A {@code ListenableArrayList} is an extension of {@link ArrayList} that allows for the registration
 * of listeners to be notified whenever the list is modified. It enables the observer pattern where
 * listeners can react to changes in the list.
 *
 * @param <E> the type of elements in this list
 */
public class UniqueArrayList<E> extends ArrayList<E> {

    public interface ChangeListener {
        void listChanged();
    }

    /**
     * A list of listeners that are notified of changes to the list.
     * <p>
     * The `listeners` variable holds a collection of `ChangeListener` objects registered to listen
     * for changes in the `ListenableArrayList`. Whenever a modification occurs in the list, such as
     * adding, removing, or updating elements, each listener in this list is notified through the
     * `listChanged` method of the `ChangeListener` interface.
     * <p>
     * This enables external components to react to changes in the list state in a decoupled and
     * event-driven manner.
     *
     * @see ChangeListener
     */
    private final ArrayList<ChangeListener> listeners = new ArrayList<>();

    /**
     * Registers a {@link ChangeListener} to be notified of changes in the list.
     *
     * @param listener the ChangeListener to add; cannot be null
     */
    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes the specified ChangeListener from the list of listeners.
     * If the listener is not present in the list, no action is taken.
     *
     * @param listener the ChangeListener to be removed
     */
    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all registered {@link ChangeListener} instances that the list has changed.
     * <p>
     * This method iterates through the collection of listeners and invokes the
     * {@code listChanged()} method on each listener. It is typically called whenever
     * a change occurs in the list that needs to be communicated to listeners,
     * such as additions, modifications, or removals of elements.
     * <p>
     * This method is private and is used internally by the {@code ListenableArrayList}
     * class to handle notification logic for list changes.
     */
    private void fireListChanged() {
        for (ChangeListener listener : listeners) {
            listener.listChanged();
        }
    }

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {
        if (this.contains(element)) return null;
        E ret = super.set(index, element);
        fireListChanged();
        return ret;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    @Override
    public boolean add(E e) {
        if (this.contains(e)) return false;
        boolean ret = super.add(e);
        fireListChanged();
        return ret;
    }

    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {
        if (this.contains(element)) return;
        super.add(index, element);
        fireListChanged();
    }

    /**
     * {@inheritDoc}
     *
     * @since 21
     */
    @Override
    public void addFirst(E element) {
        if (this.contains(element)) return;
        super.add(0, element);
        fireListChanged();
    }

    /**
     * {@inheritDoc}
     *
     * @since 21
     */
    @Override
    public void addLast(E element) {
        if (this.contains(element)) return;
        super.add(element);
        fireListChanged();
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E remove(int index) {
        E ret = super.remove(index);
        fireListChanged();
        return ret;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NoSuchElementException {@inheritDoc}
     * @since 21
     */
    @Override
    public E removeFirst() {
        E ret = super.removeFirst();
        fireListChanged();
        return ret;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NoSuchElementException {@inheritDoc}
     * @since 21
     */
    @Override
    public E removeLast() {
        E ret = super.removeLast();
        fireListChanged();
        return ret;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size(), c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        int idx = index;
        for (E e : c) {
            if (!this.contains(e)) {
                super.add(idx++, e);
            }

        }
        boolean ret= idx - index == c.size();
        if (ret) fireListChanged();
        return ret;
    }

    @Override
    public boolean remove(Object o) {
        // remove over index
        // indexOf works with object.equals not with ==
        int idx = indexOf(o);
        boolean ret = super.remove(idx) != null;
        if (ret) fireListChanged();
        return ret;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // remove over index
        // indexOf works with object.equals not with ==
        boolean ret = true;
        for (Object o : c) {
            int idx = indexOf(o);
            ret = ret && super.remove(idx) != null;
        }
        if (ret) fireListChanged();
        return ret;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        boolean ret = true;
        for (int i = 0; i < this.size(); i++) {
            Object o = this.get(i);
            if (filter.test((E) o)) {
                ret = ret && super.remove(i) != null;
            }
        }
        if (ret) fireListChanged();
        return ret;
    }

    @Override
    public void clear() {
        super.clear();
        fireListChanged();
    }

}
