/**
 * PA5 Part 1 worksheet
 */
public class Worksheet {

    /**
     * method that stores the result of Q1 in PA5 worksheet
     * @return heap's array representation after insertions
     */
    public static int[] insertionResult(){
        /*
        TODO: replace the values with the heap's array representations
         */
        int[] output =  new int[]{45, 22, 36, 50, 87, 25, 79, 51, 95, 90, 104, 5};
        return output;
    }

    /**
     * method that stores the result of Q2 in PA5 worksheet
     * @return heap's array representations for each iteration of element removal, 5 iterations in total
     */
    public static int[][] removalResult(){
        /*
        TODO: store the values with the heap's array representations for each removal iteration
         */
        int[][] output = new int[][] {
                {37, 56, 42, 59, 62, 90, 50, 67, 70, 75, 64, 0},
                {42, 56, 50, 59, 62, 90, 64, 67, 70, 75, 0, 0},
                {50, 56, 64, 59, 62, 90, 75, 67, 70, 0, 0, 0},
                {56, 59, 64, 67, 62, 90, 75, 70, 0, 0, 0, 0},
                {59, 62, 64, 67, 70, 90, 75, 0, 0, 0, 0, 0}
        };;  // idk if [][] on the left side after the instantiation
                //NO the [][] is a "type"
        return output;
    }

    /**
     * method that stores the result of Q3 in PA5 worksheet
     * @return heap's array representations for d-ary heaps, where d = 3,4
     */
    public static int[][] dResult(){
        /*
        TODO: store 3-ary heap's array representation output[0] and store 4-ary representation in output[1]
         */
        int[][] output = new int[2][11];

        // 3-ary max-heap
        output[0] = new int[]{92, 85, 56, 79, 47, 55, 61, 43, 53, 31, 20};
        
        // 4-ary max-heap
        output[1] = new int[]{92, 85, 61, 56, 47, 55, 20, 43, 53, 31, 79};
        return output;
    }

    /**
     * method that stores the result of Q4 in PA5 worksheet
     * @return array representations
     */
    public static int[][] heapOperations(){
        /*
        TODO: store heap's array status after multiples operations - operation i will be stored at index i-1
         */
        int[][] output = new int[5][12];


        // Operation 1: After removing the top twice
        output[0] = new int[]{71, 65, 70, 59, 55, 36, 37, 26, 35, 0, 0, 0};
        
        // Operation 2: After inserting 15 and 100
        output[1] = new int[]{100, 71, 70, 59, 65, 36, 37, 26, 35, 55, 15, 0};
        
        // Operation 3: After removing the top once
        output[2] = new int[]{71, 65, 70, 59, 55, 36, 37, 26, 35, 15, 0, 0};
        
        // Operation 4: After inserting 72 and 88
        output[3] = new int[]{88, 72, 71, 59, 65, 70, 37, 26, 35, 15, 55, 36};
        
        // Operation 5: After removing the top 5 times
        output[4] = new int[]{59, 55, 36, 26, 35, 15, 37, 0, 0, 0, 0, 0};
        
        return output;
    }
}