import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

class dHeapTester {
    public static void main(String[] args) {
        testMaxHeap();
        testMinHeap();
        testDifferentBranchingFactors();
        testEdgeCases();
        testLargeNumberOfElements();
        testAlternatingAddRemove();
        System.out.println("All tests passed successfully!");
    }

    private static void testMaxHeap() {
        System.out.println("Testing Max Heap...");
        dHeap<Integer> maxHeap = new dHeap<>(2, 10, true);

        // Test add and remove
        maxHeap.add(5);
        maxHeap.add(3);
        maxHeap.add(7);
        maxHeap.add(1);

        assert maxHeap.size() == 4 : "Size should be 4";
        assert maxHeap.element() == 7 : "Max element should be 7";

        assert maxHeap.remove() == 7 : "Removed element should be 7";
        assert maxHeap.remove() == 5 : "Removed element should be 5";
        assert maxHeap.remove() == 3 : "Removed element should be 3";
        assert maxHeap.remove() == 1 : "Removed element should be 1";

        assert maxHeap.size() == 0 : "Heap should be empty";

        System.out.println("Max Heap tests passed.");
    }

    private static void testMinHeap() {
        System.out.println("Testing Min Heap...");
        dHeap<Integer> minHeap = new dHeap<>(2, 10, false);

        // Test add and remove
        minHeap.add(5);
        minHeap.add(3);
        minHeap.add(7);
        minHeap.add(1);

        assert minHeap.size() == 4 : "Size should be 4";
        assert minHeap.element() == 1 : "Min element should be 1";

        assert minHeap.remove() == 1 : "Removed element should be 1";
        assert minHeap.remove() == 3 : "Removed element should be 3";
        assert minHeap.remove() == 5 : "Removed element should be 5";
        assert minHeap.remove() == 7 : "Removed element should be 7";

        assert minHeap.size() == 0 : "Heap should be empty";

        System.out.println("Min Heap tests passed.");
    }

    private static void testDifferentBranchingFactors() {
        System.out.println("Testing different branching factors...");
        for (int d = 2; d <= 5; d++) {
            dHeap<Integer> heap = new dHeap<>(d, 20, true);
            List<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                numbers.add(i);
            }
            Collections.shuffle(numbers);

            for (int num : numbers) {
                heap.add(num);
            }

            int prev = Integer.MAX_VALUE;
            while (heap.size() > 0) {
                int current = heap.remove();
                assert current <= prev : "Heap property violated for d = " + d;
                prev = current;
            }
        }
        System.out.println("Different branching factors tests passed.");
    }

    private static void testEdgeCases() {
        System.out.println("Testing edge cases...");
        dHeap<Integer> heap = new dHeap<>(3, 5, true);

        // Test removing from empty heap
        try {
            heap.remove();
            assert false : "Should throw NoSuchElementException";
        } catch (NoSuchElementException e) {
            // Expected
        }

        // Test adding null
        try {
            heap.add(null);
            assert false : "Should throw NullPointerException";
        } catch (NullPointerException e) {
            // Expected
        }

        // Test clear
        heap.add(1);
        heap.add(2);
        heap.clear();
        assert heap.size() == 0 : "Heap should be empty after clear";

        System.out.println("Edge cases tests passed.");
    }

    private static void testLargeNumberOfElements() {
        System.out.println("Testing large number of elements...");
        dHeap<Integer> heap = new dHeap<>(4, 10, true);
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            heap.add(random.nextInt(1000000));
        }

        assert heap.size() == 10000 : "Heap size should be 10000";

        int prev = Integer.MAX_VALUE;
        while (heap.size() > 0) {
            int current = heap.remove();
            assert current <= prev : "Heap property violated";
            prev = current;
        }

        System.out.println("Large number of elements test passed.");
    }

    private static void testAlternatingAddRemove() {
        System.out.println("Testing alternating add and remove...");
        dHeap<Integer> heap = new dHeap<>(3, 5, false);
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0 || heap.size() == 0) {
                heap.add(random.nextInt(1000));
            } else {
                heap.remove();
            }
        }

        int prev = Integer.MIN_VALUE;
        while (heap.size() > 0) {
            int current = heap.remove();
            assert current >= prev : "Heap property violated";
            prev = current;
        }

        System.out.println("Alternating add and remove test passed.");
    }
}