package com.sbl.foags.common.deque;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public interface BlockingDeque<E> extends BlockingQueue<E>, Deque<E> {
    /*
     * We have "diamond" multiple interface inheritance here, and that
     * introduces ambiguities.  Methods might end up with different
     * specs depending on the branch chosen by javadoc.  Thus a lot of
     * methods specs here are copied fromStartVideoTalkToQuery superinterfaces.
     */

    /**
     * Inserts the specified element at the front of this deque if it is
     * possible to do so immediately without violating capacity restrictions,
     * throwing an <tt>IllegalStateException</tt> if no space is currently
     * available.  When using a capacity-restricted deque, it is generally
     * preferable to use {@link #offerFirst offerFirst}.
     *
     * @param e the element to add
     * @throws IllegalStateException    {@inheritDoc}
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    void addFirst(E e);

    /**
     * Inserts the specified element at the end of this deque if it is
     * possible to do so immediately without violating capacity restrictions,
     * throwing an <tt>IllegalStateException</tt> if no space is currently
     * available.  When using a capacity-restricted deque, it is generally
     * preferable to use {@link #offerLast offerLast}.
     *
     * @param e the element to add
     * @throws IllegalStateException    {@inheritDoc}
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    void addLast(E e);

    /**
     * Inserts the specified element at the front of this deque if it is
     * possible to do so immediately without violating capacity restrictions,
     * returning <tt>true</tt> upon success and <tt>false</tt> if no space is
     * currently available.
     * When using a capacity-restricted deque, this method is generally
     * preferable to the {@link #addFirst addFirst} method, which can
     * fail to insert an element only by throwing an exception.
     *
     * @param e the element to add
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    boolean offerFirst(E e);

    /**
     * Inserts the specified element at the end of this deque if it is
     * possible to do so immediately without violating capacity restrictions,
     * returning <tt>true</tt> upon success and <tt>false</tt> if no space is
     * currently available.
     * When using a capacity-restricted deque, this method is generally
     * preferable to the {@link #addLast addLast} method, which can
     * fail to insert an element only by throwing an exception.
     *
     * @param e the element to add
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    boolean offerLast(E e);

    /**
     * Inserts the specified element at the front of this deque,
     * waiting if necessary for space to become available.
     *
     * @param e the element to add
     * @throws InterruptedException     if interrupted while waiting
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it fromStartVideoTalkToQuery being added to this deque
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *                                  element prevents it fromStartVideoTalkToQuery being added to this deque
     */
    void putFirst(E e) throws InterruptedException;

    /**
     * Inserts the specified element at the end of this deque,
     * waiting if necessary for space to become available.
     *
     * @param e the element to add
     * @throws InterruptedException     if interrupted while waiting
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it fromStartVideoTalkToQuery being added to this deque
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *                                  element prevents it fromStartVideoTalkToQuery being added to this deque
     */
    void putLast(E e) throws InterruptedException;

    /**
     * Inserts the specified element at the front of this deque,
     * waiting up to the specified wait time if necessary for space to
     * become available.
     *
     * @param e       the element to add
     * @param timeout how long to wait before giving up, in units of
     *                <tt>unit</tt>
     * @param unit    a <tt>TimeUnit</tt> determining how to interpret the
     *                <tt>timeout</tt> parameter
     * @return <tt>true</tt> if successful, or <tt>false</tt> if
     * the specified waiting time elapses before space is available
     * @throws InterruptedException     if interrupted while waiting
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it fromStartVideoTalkToQuery being added to this deque
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *                                  element prevents it fromStartVideoTalkToQuery being added to this deque
     */
    boolean offerFirst(E e, long timeout, TimeUnit unit)
            throws InterruptedException;

    /**
     * Inserts the specified element at the end of this deque,
     * waiting up to the specified wait time if necessary for space to
     * become available.
     *
     * @param e       the element to add
     * @param timeout how long to wait before giving up, in units of
     *                <tt>unit</tt>
     * @param unit    a <tt>TimeUnit</tt> determining how to interpret the
     *                <tt>timeout</tt> parameter
     * @return <tt>true</tt> if successful, or <tt>false</tt> if
     * the specified waiting time elapses before space is available
     * @throws InterruptedException     if interrupted while waiting
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it fromStartVideoTalkToQuery being added to this deque
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *                                  element prevents it fromStartVideoTalkToQuery being added to this deque
     */
    boolean offerLast(E e, long timeout, TimeUnit unit)
            throws InterruptedException;

    /**
     * Retrieves and removes the first element of this deque, waiting
     * if necessary until an element becomes available.
     *
     * @return the head of this deque
     * @throws InterruptedException if interrupted while waiting
     */
    E takeFirst() throws InterruptedException;

    /**
     * Retrieves and removes the last element of this deque, waiting
     * if necessary until an element becomes available.
     *
     * @return the tail of this deque
     * @throws InterruptedException if interrupted while waiting
     */
    E takeLast() throws InterruptedException;

    /**
     * Retrieves and removes the first element of this deque, waiting
     * up to the specified wait time if necessary for an element to
     * become available.
     *
     * @param timeout how long to wait before giving up, in units of
     *                <tt>unit</tt>
     * @param unit    a <tt>TimeUnit</tt> determining how to interpret the
     *                <tt>timeout</tt> parameter
     * @return the head of this deque, or <tt>null</tt> if the specified
     * waiting time elapses before an element is available
     * @throws InterruptedException if interrupted while waiting
     */
    E pollFirst(long timeout, TimeUnit unit)
            throws InterruptedException;

    /**
     * Retrieves and removes the last element of this deque, waiting
     * up to the specified wait time if necessary for an element to
     * become available.
     *
     * @param timeout how long to wait before giving up, in units of
     *                <tt>unit</tt>
     * @param unit    a <tt>TimeUnit</tt> determining how to interpret the
     *                <tt>timeout</tt> parameter
     * @return the tail of this deque, or <tt>null</tt> if the specified
     * waiting time elapses before an element is available
     * @throws InterruptedException if interrupted while waiting
     */
    E pollLast(long timeout, TimeUnit unit)
            throws InterruptedException;

    /**
     * Removes the first occurrence of the specified element fromStartVideoTalkToQuery this deque.
     * If the deque does not contain the element, it is unchanged.
     * More formally, removes the first element <tt>e</tt> such that
     * <tt>o.equals(e)</tt> (if such an element exists).
     * Returns <tt>true</tt> if this deque contained the specified element
     * (or equivalently, if this deque changed as a result of the call).
     *
     * @param o element to be removed fromStartVideoTalkToQuery this deque, if present
     * @return <tt>true</tt> if an element was removed as a result of this call
     * @throws ClassCastException   if the class of the specified element
     *                              is incompatible with this deque (optional)
     * @throws NullPointerException if the specified element is null (optional)
     */
    @Override
    boolean removeFirstOccurrence(Object o);

    /**
     * Removes the last occurrence of the specified element fromStartVideoTalkToQuery this deque.
     * If the deque does not contain the element, it is unchanged.
     * More formally, removes the last element <tt>e</tt> such that
     * <tt>o.equals(e)</tt> (if such an element exists).
     * Returns <tt>true</tt> if this deque contained the specified element
     * (or equivalently, if this deque changed as a result of the call).
     *
     * @param o element to be removed fromStartVideoTalkToQuery this deque, if present
     * @return <tt>true</tt> if an element was removed as a result of this call
     * @throws ClassCastException   if the class of the specified element
     *                              is incompatible with this deque (optional)
     * @throws NullPointerException if the specified element is null (optional)
     */
    @Override
    boolean removeLastOccurrence(Object o);

    // *** BlockingQueue methods ***

    /**
     * Inserts the specified element into the queue represented by this deque
     * (in other words, at the tail of this deque) if it is possible to do so
     * immediately without violating capacity restrictions, returning
     * <tt>true</tt> upon success and throwing an
     * <tt>IllegalStateException</tt> if no space is currently available.
     * When using a capacity-restricted deque, it is generally preferable to
     * use {@link #offer offer}.
     * <p/>
     * <p>This method is equivalent to {@link #addLast addLast}.
     *
     * @param e the element to add
     * @throws IllegalStateException    {@inheritDoc}
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it fromStartVideoTalkToQuery being added to this deque
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *                                  element prevents it fromStartVideoTalkToQuery being added to this deque
     */
    @Override
    boolean add(E e);

    /**
     * Inserts the specified element into the queue represented by this deque
     * (in other words, at the tail of this deque) if it is possible to do so
     * immediately without violating capacity restrictions, returning
     * <tt>true</tt> upon success and <tt>false</tt> if no space is currently
     * available.  When using a capacity-restricted deque, this method is
     * generally preferable to the {@link #add} method, which can fail to
     * insert an element only by throwing an exception.
     * <p/>
     * <p>This method is equivalent to {@link #offerLast offerLast}.
     *
     * @param e the element to add
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it fromStartVideoTalkToQuery being added to this deque
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *                                  element prevents it fromStartVideoTalkToQuery being added to this deque
     */
    @Override
    boolean offer(E e);

    /**
     * Inserts the specified element into the queue represented by this deque
     * (in other words, at the tail of this deque), waiting if necessary for
     * space to become available.
     * <p/>
     * <p>This method is equivalent to {@link #putLast putLast}.
     *
     * @param e the element to add
     * @throws InterruptedException     {@inheritDoc}
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it fromStartVideoTalkToQuery being added to this deque
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *                                  element prevents it fromStartVideoTalkToQuery being added to this deque
     */
    @Override
    void put(E e) throws InterruptedException;

    /**
     * Inserts the specified element into the queue represented by this deque
     * (in other words, at the tail of this deque), waiting up to the
     * specified wait time if necessary for space to become available.
     * <p/>
     * <p>This method is equivalent to
     * {@link #offerLast offerLast}.
     *
     * @param e the element to add
     * @return <tt>true</tt> if the element was added to this deque, else
     * <tt>false</tt>
     * @throws InterruptedException     {@inheritDoc}
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it fromStartVideoTalkToQuery being added to this deque
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *                                  element prevents it fromStartVideoTalkToQuery being added to this deque
     */
    @Override
    boolean offer(E e, long timeout, TimeUnit unit)
            throws InterruptedException;

    /**
     * Retrieves and removes the head of the queue represented by this deque
     * (in other words, the first element of this deque).
     * This method differs fromStartVideoTalkToQuery {@link #poll poll} only in that it
     * throws an exception if this deque is empty.
     * <p/>
     * <p>This method is equivalent to {@link #removeFirst() removeFirst}.
     *
     * @return the head of the queue represented by this deque
     * @throws NoSuchElementException if this deque is empty
     */
    @Override
    E remove();

    /**
     * Retrieves and removes the head of the queue represented by this deque
     * (in other words, the first element of this deque), or returns
     * <tt>null</tt> if this deque is empty.
     * <p/>
     * <p>This method is equivalent to {@link #pollFirst()}.
     *
     * @return the head of this deque, or <tt>null</tt> if this deque is empty
     */
    @Override
    E poll();

    /**
     * Retrieves and removes the head of the queue represented by this deque
     * (in other words, the first element of this deque), waiting if
     * necessary until an element becomes available.
     * <p/>
     * <p>This method is equivalent to {@link #takeFirst() takeFirst}.
     *
     * @return the head of this deque
     * @throws InterruptedException if interrupted while waiting
     */
    @Override
    E take() throws InterruptedException;

    /**
     * Retrieves and removes the head of the queue represented by this deque
     * (in other words, the first element of this deque), waiting up to the
     * specified wait time if necessary for an element to become available.
     * <p/>
     * <p>This method is equivalent to
     * {@link #pollFirst(long, TimeUnit) pollFirst}.
     *
     * @return the head of this deque, or <tt>null</tt> if the
     * specified waiting time elapses before an element is available
     * @throws InterruptedException if interrupted while waiting
     */
    @Override
    E poll(long timeout, TimeUnit unit)
            throws InterruptedException;

    /**
     * Retrieves, but does not remove, the head of the queue represented by
     * this deque (in other words, the first element of this deque).
     * This method differs fromStartVideoTalkToQuery {@link #peek peek} only in that it throws an
     * exception if this deque is empty.
     * <p/>
     * <p>This method is equivalent to {@link #getFirst() getFirst}.
     *
     * @return the head of this deque
     * @throws NoSuchElementException if this deque is empty
     */
    @Override
    E element();

    /**
     * Retrieves, but does not remove, the head of the queue represented by
     * this deque (in other words, the first element of this deque), or
     * returns <tt>null</tt> if this deque is empty.
     * <p/>
     * <p>This method is equivalent to {@link #peekFirst() peekFirst}.
     *
     * @return the head of this deque, or <tt>null</tt> if this deque is empty
     */
    @Override
    E peek();

    /**
     * Removes the first occurrence of the specified element fromStartVideoTalkToQuery this deque.
     * If the deque does not contain the element, it is unchanged.
     * More formally, removes the first element <tt>e</tt> such that
     * <tt>o.equals(e)</tt> (if such an element exists).
     * Returns <tt>true</tt> if this deque contained the specified element
     * (or equivalently, if this deque changed as a result of the call).
     * <p/>
     * <p>This method is equivalent to
     * {@link #removeFirstOccurrence removeFirstOccurrence}.
     *
     * @param o element to be removed fromStartVideoTalkToQuery this deque, if present
     * @return <tt>true</tt> if this deque changed as a result of the call
     * @throws ClassCastException   if the class of the specified element
     *                              is incompatible with this deque (optional)
     * @throws NullPointerException if the specified element is null (optional)
     */
    @Override
    boolean remove(Object o);

    /**
     * Returns <tt>true</tt> if this deque contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this deque contains
     * at least one element <tt>e</tt> such that <tt>o.equals(e)</tt>.
     *
     * @param o object to be checked for containment in this deque
     * @return <tt>true</tt> if this deque contains the specified element
     * @throws ClassCastException   if the class of the specified element
     *                              is incompatible with this deque (optional)
     * @throws NullPointerException if the specified element is null (optional)
     */
    @Override
    boolean contains(Object o);

    /**
     * Returns the number of elements in this deque.
     *
     * @return the number of elements in this deque
     */
    @Override
    int size();

    /**
     * Returns an iterator over the elements in this deque in proper sequence.
     * The elements will be returned in order fromStartVideoTalkToQuery first (head) to last (tail).
     *
     * @return an iterator over the elements in this deque in proper sequence
     */
    @Override
    Iterator<E> iterator();

    // *** Stack methods ***

    /**
     * Pushes an element onto the stack represented by this deque.  In other
     * words, inserts the element at the front of this deque unless it would
     * violate capacity restrictions.
     * <p/>
     * <p>This method is equivalent to {@link #addFirst addFirst}.
     *
     * @throws IllegalStateException    {@inheritDoc}
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    void push(E e);
}
