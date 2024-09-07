/*
 * Name: Felix Najera
 * PID:  A17618969
 */

import java.io.*;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

/**
 * Implementation of KNN using MyPriorityQueue on the MNIST dataset
 *
 * @author Felix Najera
 * @since A17618969
 */

public class MNIST {

    public static final int NUM_TEST = 10000;   // can be up to 10k
    // ^ recommended to change to 100 temporarily
    public static final int NUM_TRAIN = 20000; // can be up to 60k

    public static final int K = 3;
    public static final int IMG_DIM = 28;

    private static final float[][] TRAIN_IMAGES = new float[NUM_TRAIN][IMG_DIM * IMG_DIM];
    private static final short[] TRAIN_LABELS = new short[NUM_TRAIN];
    public static final float[][] TEST_IMAGES = new float[NUM_TEST][IMG_DIM * IMG_DIM];
    private static final short[] TEST_LABELS = new short[NUM_TEST];

    /**
     * Custom class for storing images, labels, and priority values together
     */
    public static class DataHolder implements Comparable<DataHolder> {
        int label;
        float priority;
        float[] image;

        /**
         * Constructor that creates a new DataHolder
         *
         * @param label A number from 0 to 9 that describes the image.
         * @param priority A value that determines how close this image is
         *                 to the current image being tested
         *                 lower priority = better fit
         * @param image A flattened array representing the image
         */
        public DataHolder(int label, float priority, float[] image) {
            this.label = label;
            this.priority = priority;
            this.image = image;
        }

        /**
         * @param d the other DataHolder that is being compared
         * @return an integer that determines whether priority is lower,
         *         greater, or equal to that of d
         */@Override
        public int compareTo(DataHolder d) {
            return Float.compare(this.priority, d.priority);
        }
    }

    /**
     * Calculate Euclidean distance between two vectors (1-D arrays)
     * @param img1 the first array
     * @param img2 the second array
     * @return the Euclidean distance between img1 and img2
     * @throws IllegalArgumentException ensures a distance is actually present
     */
    public static float totalDist(float[] img1, float[] img2) throws IllegalArgumentException {
        if (img1.length != img2.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }

        float sum = 0;
        for (int i = 0; i < img1.length; i++) {
            float diff = img1[i] - img2[i];
            sum += diff * diff;
        }
        return (float) Math.sqrt(sum);
    }

    /**
     * Given an image
     * @param image the array that contains image data
     * @param k the number of nearest neighbors to find
     * @return an array of DataHolders containing the k closest neighbors to image
     */
    public static DataHolder[] getClosestMatches(float[] image, int k) {
        /*
        *
        *
        * MyPriorityQueue<DataHolder> queue = new MyPriorityQueue<>(NUM_TRAIN);
        * for (int i = 0; i < NUM_TRAIN; i++) {
           float distance = totalDist(image, TRAIN_IMAGES[i]);
           DataHolder holder = new DataHolder(TRAIN_LABELS[i], distance, TRAIN_IMAGES[i]);

           // Add to queue
           try {
               queue.offer(holder);
           } catch (NullPointerException e) {
               System.out.println("Error adding to queue: " + e.getMessage());
           }

           // If queue size exceeds k, remove largest element
           if (queue.size() >= k) {
               try {
                   queue.poll();
               } catch (Exception e) {
                   System.out.println("Error removing from queue: " + e.getMessage());
               }
           }
       }

       DataHolder[] result = new DataHolder[k];
       int index = k - 1;
       while (!queue.isEmpty()) {
           try {
               result[index--] = queue.poll();
           } catch (Exception e) {
               System.out.println("Error retrieving from queue: " + e.getMessage());
           }
       }

       return result;



        *
        * */

        MyPriorityQueue<DataHolder> queue = new MyPriorityQueue<>(k);

        for (int i = 0; i < NUM_TRAIN; i++) {
            float distance = totalDist(image, TRAIN_IMAGES[i]);
            DataHolder holder = new DataHolder(TRAIN_LABELS[i], distance, TRAIN_IMAGES[i]);

            if (queue.size() < k) {
                queue.offer(holder);
            } else if (distance < queue.peek().priority) {
                queue.poll();
                queue.offer(holder);
            }
        }

        DataHolder[] result = new DataHolder[k];
        for (int i = k - 1; i >= 0 && !queue.isEmpty(); i--) {
            result[i] = queue.poll();
        }

        return result;
    }

    /**
     * Makes a prediction using the K-NN Classifier
     * You may assume k < n (amount of training data)
     *
     * @param closestMatches the array of DataHolders containing the k closest matches
     * @return loops through and finds higher than maxCount in individual matches
     */
    public static int predict(DataHolder[] closestMatches) {
        int[] counts = new int[10];

        for (DataHolder holder : closestMatches) {
            counts[holder.label]++;
        }

        int maxCount = 0;
        int predictedLabel = 0;

        for (int i = 0; i < 10; i++) {
            if (counts[i] > maxCount) {
                maxCount = counts[i];
                predictedLabel = i;
            }
        }

        return predictedLabel;
    }

    // you can ignore the rest of this file :)

    /**
     * Loads MNIST data into the specified arrays
     *
     * @param imgFile the link to MNIST images
     * @param lblFile the link to MNIST labels
     * @param imgArr the array to store images (dimension nx784)
     * @param lblArr the array to store labels (dimension n)
     */
    public static void loadData(String imgFile, String lblFile, float[][] imgArr, short[] lblArr) throws IOException {

        InputStream stream;
        File data = Paths.get("src", "data", imgFile).toFile();
        try {
            stream = new FileInputStream(data);
        } catch (FileNotFoundException f) {
            data = Paths.get("data", imgFile).toFile();
            stream = new FileInputStream(data);
        }
        InputStream imgIn = new GZIPInputStream(stream);

        data = Paths.get("src", "data", lblFile).toFile();
        try {
            stream = new FileInputStream(data);
        } catch (FileNotFoundException f) {
            data = Paths.get("data", lblFile).toFile();
            stream = new FileInputStream(data);
        }
        InputStream lblIn = new GZIPInputStream(stream);

        // first 16 bytes of image data are metadata
        // first 8 bytes of label data are metadata
        byte[] tempBuffer = new byte[16];
        imgIn.read(tempBuffer, 0, 16);
        lblIn.read(tempBuffer, 0, 8);

        byte[] dataBuffer = new byte[1];

        for (int i = 0; i < lblArr.length; i++){
            lblIn.read(dataBuffer, 0, 1);
            lblArr[i] = (short) (dataBuffer[0] & 0xFF);

            for (int j = 0; j < IMG_DIM * IMG_DIM; j++){
                imgIn.read(dataBuffer, 0, 1);
                float pixelVal = (dataBuffer[0] & 0xFF) / 255.f;
                imgArr[i][j] = pixelVal;
            }
        }
    }

    /**
     * Calls loadData on each MNIST dataset
     */
    public static void downloadMNIST() {
        long startTime = System.currentTimeMillis();

        String trainImagesFile = "train-images-idx3-ubyte.gz";
        String trainLabelsFile = "train-labels-idx1-ubyte.gz";
        String testImagesFile = "t10k-images-idx3-ubyte.gz";
        String testLabelsFile = "t10k-labels-idx1-ubyte.gz";

        // load all training and test data
        try {
            loadData(trainImagesFile, trainLabelsFile, TRAIN_IMAGES, TRAIN_LABELS);
            loadData(testImagesFile, testLabelsFile, TEST_IMAGES, TEST_LABELS);
            System.out.println("MNIST download completed!");
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("MNIST download failed!");
            System.exit(1);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime)/1000. + " seconds");
    }

    public static void main(String[] args) {
        downloadMNIST();

        long startTime = System.currentTimeMillis();
        int numWrong = 0;
        for (int i = 0; i < NUM_TEST; i++) {
            DataHolder[] closestNeighbors = getClosestMatches(TEST_IMAGES[i], K);
            int guess = predict(closestNeighbors);
            if (guess != TEST_LABELS[i]) {
                numWrong++;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Accuracy: " + (1 - numWrong / (float) NUM_TEST));
        System.out.println("Spent " + (endTime - startTime)/1000. + " seconds testing "
            + NUM_TEST + " images with k = " + K);
    }
}
