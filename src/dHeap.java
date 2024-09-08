/*
* Name: Felix Najera
* PID:  A17618969
*/

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Title: dHeap Description: This program creates a Heap with d branching factor
 *
 * @author Felix Najera
 * @since 9/2/24
 *
 * @param <T> the type of elements held in this collection
 */

public class dHeap<T extends Comparable<? super T>> implements HeapInterface<T> {

    private T[] heap;   // backing array
    private int d;      // branching factor
    private int nelems; // number of elements
    private boolean isMaxHeap; // indicates whether heap is max or min


    /**
     * Initializes a binary max heap with capacity = 10
     */
    @SuppressWarnings("unchecked")
    public dHeap() {
        this(2, 10, false);
    }

    /**
     * Initializes a binary max heap with a given initial capacity.
     *
     * @param heapSize The initial capacity of the heap.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int heapSize) {
        this(2, heapSize, true);
    }

    /**
     * Initializes a d-ary heap (with a given value for d), with a given initial
     * capacity.
     *
     * @param d         The number of child nodes each node in the heap should have.
     * @param heapSize  The initial capacity of the heap.
     * @param isMaxHeap indicates whether the heap should be max or min
     * @throws IllegalArgumentException if d is less than one.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int d, int heapSize, boolean isMaxHeap) throws IllegalArgumentException {
        if (d < 1) {
            throw new IllegalArgumentException("Branching factor must be at least 1");
        }
        this.d = d;
        this.isMaxHeap = isMaxHeap;
        this.heap = (T[]) new Comparable[heapSize];
        this.nelems = 0;
    }


    /**
    * Getter for the dHeap Object size
    * @return attribute of number of elements
    *
    * */
    public int size() {
        return nelems;
    }


    /**
    *
    * Rearranges heap around removed leaf
    * @return the element of the heap to be deleted
    * @throws NoSuchElementException ensures no errors occur from remove from an empty
    * */
    @Override
    public T remove() throws NoSuchElementException {
        if (nelems == 0) {
            throw new NoSuchElementException("Heap is empty");
        }

        T removed = heap[0];
        heap[0] = heap[nelems - 1];
        nelems--;

        percolateDown(0);

        return removed;
    }



    /**
    *
    *
    *
    * Resize the heap with the additional node
    * @param item object for the heap
    * @throws NullPointerException prevents additional null nodes being placed internally
    * */
    @Override
    public void add(T item) throws NullPointerException {
        /*
        *          if (item == null) {
             throw new NullPointerException("Item cannot be null");
         }

         nelems++;
         resize(); // Ensure we have enough space

         int index = nelems - 1;
         heap[index] = item;

         percolateUp(index);
        *
        *
        *
        *
        *
        *
        *
        *
        *
        *
        * */

        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        if (nelems == heap.length) {
            resize();
        }
        heap[nelems] = item;
        percolateUp(nelems);
        nelems++;
    }



    /**
    *
    * uses java builtin array for clearing
    *
    *
    * */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        nelems = 0;
        Arrays.fill(heap, 0, nelems, null);

    }


    /**
    *
    * gives the data of the top of the heap
    * @return the top of the heap
    * @throws NoSuchElementException check for empty heap
    * */
    @Override
    public T element() throws NoSuchElementException {
        if (nelems == 0) {
            throw new NoSuchElementException();
        }
        return heap[0];
    }



    /**
    *
    * parent position
    * @param index the known index
    * @return the index of parent
    * */
    private int parent(int index) {
        return (index - 1) / d;
    }





    /**
    *
    *
    * percolates whether its less or greater
    * @param index the origin of index of percolating
    *
    * */
    private void percolateUp(int index) {
        int parentIndex = parent(index);
        if (isMaxHeap) {
            while (parentIndex >= 0 && compare(heap[index], heap[parentIndex]) > 0) {
                swap(parentIndex, index);
                index = parentIndex;
                parentIndex = parent(index);
            }
        } else {
            while (parentIndex >= 0 && compare(heap[index], heap[parentIndex]) < 0) {
                swap(parentIndex, index);
                index = parentIndex;
                parentIndex = parent(index);
            }
        }
    }


    /**
    *
    *
    * ensures the transfer of nodes in the Collection doesn't
    * @param index node position that moves down the Collection
    * */

    private void percolateDown(int index) {


        int childIndex = index * d + 1;
        while (childIndex < nelems) {
            int maxChild = childIndex;

            for (int i = 1; i < d; i++) {
                int testIndex = childIndex + i;
                if (testIndex < nelems && (!isMaxHeap || compare(heap[testIndex], heap[maxChild]) > 0)) {
                    maxChild = testIndex;
                }
            }

            if (isMaxHeap) {
                if (compare(heap[maxChild], heap[index]) > 0) {
                    swap(maxChild, index);
                    index = maxChild;
                    childIndex = d * index + 1;
                } else {
                    break;
                }
            } else {
                if (compare(heap[maxChild], heap[index]) < 0) {
                    swap(maxChild, index);
                    index = maxChild;
                    childIndex = d * index + 1;
                } else {
                    break;
                }
            }
        }
    }



    /**
    *
    *
    * Creates new instantiation of builtin Array with newCapacity
    *
    * */
    @SuppressWarnings("unchecked")
    private void resize() {
        @SuppressWarnings("unchecked")

        /*
        *
        T[] temp = (T[]) new Comparable[heap.length * 2];
        System.arraycopy(heap, 0, temp, 0, heap.length);
        heap = temp;
        // Double or increase by a larger factor (e.g., 1.5x) to reduce frequent resizing
        T[] temp = (T[]) new Comparable[heap.length * 2]; // Consider a larger factor
        System.arraycopy(heap, 0, temp, 0, heap.length);
        heap = temp;
        *
        *
        *
        *
        * */

        int newCapacity = heap.length * 2;
        heap = Arrays.copyOf(heap, newCapacity);

    }

    /*
    additional helper functions
     */



    /**
    *
    * traverses heap for largest value found
    * @param end max bound for the search/traversal
    * @param start bound to search in
    * @return the value in its primitive form
    *
    * */
    private int findLargestChild(int start, int end) {
        int largest = start;
        for (int i = start + 1; i <= end; i++) {
            if (isMaxHeap ? compare(heap[i], heap[largest]) > 0 : compare(heap[i], heap[largest]) < 0) {
                largest = i;
            }
        }
        return largest;
    }



    /**
    *
    * makes comparison of node data
    * @param a the first node
    * @param b the second node in mind
    * @return gives a value of the binary return
    * */
    private int compare(T a, T b) {
        return a.compareTo(b);
    }



    /**
    *
    * has the nodes in the heap flip positions
    *
    * @param j arbitrary label of one node
    * @param i different arbitrary label of node to swap with
    *
    *
    *
    * */
    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }


    /**
    * checks whether the heap is empty
    * @return t/f if the elements/"nodes" aren't there
    *
    * */
    public boolean isEmpty() {
        return nelems == 0;
    }
}
