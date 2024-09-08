/*
 * Name: Felix Najera
 * PID:  A17618969
 */

/**
 * Priority Queue Implementation using dHeap.
 *
 * @author Felix Najera
 * @since 9/1/2024
 *
 * @param <T> the type of elements held in this collection
 */

public class MyPriorityQueue<T extends Comparable<? super T>> {
    private dHeap<T> pQueue;

    /**
    * Constructor that creates a new priority queue
    *
    * @param initialSize the given size
    */
    public MyPriorityQueue(int initialSize) {
        int defaultInitial = 4;
        pQueue = new dHeap<>(defaultInitial, initialSize, true);
    }

    /**
    * Inserts an element into the Priority Queue. The element received cannot be
    * null.
    *
    * @param element Element to be inserted.
    * @throws NullPointerException if the element received is null.
    * @return returns true
    */
    public boolean offer(T element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }
        pQueue.add(element);
        return true;
    }

    /**
    * Retrieve and remove the head of this Priority Queue (smallest element), or null if the
    * queue is empty.
    *
    * @return The head of the queue (smallest element), or null if queue is empty.
    */
    public T poll() {
        if (pQueue.isEmpty()) {
            return null;
        }
        return pQueue.remove();
    }

    /**
    * Clears the contents of the queue
    */
    public void clear() {
        pQueue.clear();
    }

    /**
    * Retrieves, but does not remove, the head of this queue, or returns null if
    * this queue is empty.
    *
    * @return the head of the queue, null if the queue is empty
    */
    public T peek() {
        if (pQueue.isEmpty()) {
            return null;
        }
        return pQueue.element();
    }

    /**
    * Return true is the queue is empty, false otherwise
    * @return yes/no of an empty queue
    */
    public boolean isEmpty() {
        return pQueue.isEmpty();
    }

    /**
     *
     *
     *
     * getter for the size parameter
     * @return size attribute
     */
    public int size() {
        return pQueue.size();
    }

}
