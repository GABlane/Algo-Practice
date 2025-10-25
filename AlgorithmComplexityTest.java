import java.util.*;

/**
 * Algorithm Performance Tester
 * ----------------------------
 * Measures and compares the runtime (in milliseconds),
 * comparisons, and swaps of different searching and sorting algorithms
 * for varying input sizes.
 */
public class AlgorithmComplexityTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Test sizes
        int[] dataSizes = { 1000, 5000, 10000, 50000, 100000 };

        // Main Menu Loop
        while (true) {
            System.out.println("\n========= ALGORITHM COMPLEXITY TEST MENU =========");
            System.out.println(" 1. Linear Search");
            System.out.println(" 2. Binary Search");
            System.out.println(" 3. Quick Sort");
            System.out.println(" 4. Merge Sort");
            System.out.println(" 5. Heap Sort");
            System.out.println(" 6. Bubble Sort");
            System.out.println(" 7. Insertion Sort");
            System.out.println(" 8. Selection Sort");
            System.out.println(" 9. Shell Sort");
            System.out.println("10. Compare All Algorithms");
            System.out.println(" 0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            if (choice == 0) {
                System.out.println("\nProgram finished. Goodbye!");
                break;
            }

            switch (choice) {
                case 1 -> runSelectedAlgorithm(dataSizes, random, "Linear Search");
                case 2 -> runSelectedAlgorithm(dataSizes, random, "Binary Search");
                case 3 -> runSelectedAlgorithm(dataSizes, random, "Quick Sort");
                case 4 -> runSelectedAlgorithm(dataSizes, random, "Merge Sort");
                case 5 -> runSelectedAlgorithm(dataSizes, random, "Heap Sort");
                case 6 -> runSelectedAlgorithm(dataSizes, random, "Bubble Sort");
                case 7 -> runSelectedAlgorithm(dataSizes, random, "Insertion Sort");
                case 8 -> runSelectedAlgorithm(dataSizes, random, "Selection Sort");
                case 9 -> runSelectedAlgorithm(dataSizes, random, "Shell Sort");
                case 10 -> runAllAlgorithms(dataSizes, random);
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }

        scanner.close();
    }

    // =========================================================
    // Core Menu Handlers
    // =========================================================

    public static void runSelectedAlgorithm(int[] dataSizes, Random random, String algorithm) {
        System.out.printf("%-10s %-15s %-15s %-15s%n", "Size(n)", "Time (ms)", "Comparisons", "Swaps");
        System.out.println("--------------------------------------------------------------");

        for (int n : dataSizes) {
            int[] data = generateRandomArray(n, random);
            int key = data[random.nextInt(n)];
            AlgorithmStats stats = new AlgorithmStats(0.0, 0, 0);

            switch (algorithm) {
                case "Linear Search" -> stats = measureSearch(data, key, AlgorithmComplexityTest::linearSearch);
                case "Binary Search" -> {
                    Arrays.sort(data);
                    stats = measureSearch(data, key, AlgorithmComplexityTest::binarySearch);
                }
                case "Quick Sort" -> stats = measureSort(data, AlgorithmComplexityTest::quickSort);
                case "Merge Sort" -> stats = measureSort(data, AlgorithmComplexityTest::mergeSort);
                case "Heap Sort" -> stats = measureSort(data, AlgorithmComplexityTest::heapSort);
                case "Bubble Sort" -> stats = measureSort(data, AlgorithmComplexityTest::bubbleSort);
                case "Insertion Sort" -> stats = measureSort(data, AlgorithmComplexityTest::insertionSort);
                case "Selection Sort" -> stats = measureSort(data, AlgorithmComplexityTest::selectionSort);
                case "Shell Sort" -> stats = measureSort(data, AlgorithmComplexityTest::shellSort);
            }

            System.out.printf("%-10d %-15.2f %-15d %-15d%n", n, stats.time, stats.comparisons, stats.swaps);
        }
    }

    public static void runAllAlgorithms(int[] dataSizes, Random random) {
        System.out.println("\n=== COMPREHENSIVE ALGORITHM COMPARISON ===\n");

        for (int n : dataSizes) {
            int[] baseArray = generateRandomArray(n, random);
            int key = baseArray[random.nextInt(n)];

            System.out.println("Array Size: " + n);
            System.out.println("=".repeat(80));
            System.out.printf("%-15s %-12s %-15s %-15s%n", "Algorithm", "Time (ms)", "Comparisons", "Swaps");
            System.out.println("=".repeat(80));

            // --- Searching ---
            AlgorithmStats linearStats = measureSearch(baseArray, key, AlgorithmComplexityTest::linearSearch);
            System.out.printf("%-15s %-12.2f %-15d %-15s%n", "Linear Search", linearStats.time, linearStats.comparisons,
                    "N/A");

            int[] sortedForSearch = Arrays.copyOf(baseArray, baseArray.length);
            Arrays.sort(sortedForSearch);
            AlgorithmStats binaryStats = measureSearch(sortedForSearch, key, AlgorithmComplexityTest::binarySearch);
            System.out.printf("%-15s %-12.2f %-15d %-15s%n", "Binary Search", binaryStats.time, binaryStats.comparisons,
                    "N/A");

            System.out.println();

            // --- Sorting ---
            AlgorithmStats quickStats = measureSort(baseArray, AlgorithmComplexityTest::quickSort);
            System.out.printf("%-15s %-12.2f %-15d %-15d%n", "Quick Sort", quickStats.time, quickStats.comparisons,
                    quickStats.swaps);

            AlgorithmStats mergeStats = measureSort(baseArray, AlgorithmComplexityTest::mergeSort);
            System.out.printf("%-15s %-12.2f %-15d %-15d%n", "Merge Sort", mergeStats.time, mergeStats.comparisons,
                    mergeStats.swaps);

            AlgorithmStats heapStats = measureSort(baseArray, AlgorithmComplexityTest::heapSort);
            System.out.printf("%-15s %-12.2f %-15d %-15d%n", "Heap Sort", heapStats.time, heapStats.comparisons,
                    heapStats.swaps);

            AlgorithmStats bubbleStats = measureSort(baseArray, AlgorithmComplexityTest::bubbleSort);
            System.out.printf("%-15s %-12.2f %-15d %-15d%n", "Bubble Sort", bubbleStats.time, bubbleStats.comparisons,
                    bubbleStats.swaps);

            AlgorithmStats insertionStats = measureSort(baseArray, AlgorithmComplexityTest::insertionSort);
            System.out.printf("%-15s %-12.2f %-15d %-15d%n", "Insertion Sort", insertionStats.time,
                    insertionStats.comparisons, insertionStats.swaps);

            AlgorithmStats selectionStats = measureSort(baseArray, AlgorithmComplexityTest::selectionSort);
            System.out.printf("%-15s %-12.2f %-15d %-15d%n", "Selection Sort", selectionStats.time,
                    selectionStats.comparisons, selectionStats.swaps);

            AlgorithmStats shellStats = measureSort(baseArray, AlgorithmComplexityTest::shellSort);
            System.out.printf("%-15s %-12.2f %-15d %-15d%n", "Shell Sort", shellStats.time, shellStats.comparisons,
                    shellStats.swaps);

            System.out.println("\n");
        }
    }

    // =========================================================
    // Utility Functions
    // =========================================================

    public static int[] generateRandomArray(int size, Random random) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
            array[i] = random.nextInt(1_000_000);
        return array;
    }

    public static AlgorithmStats measureSearch(int[] data, int key, SearchAlgorithm algorithm) {
        Counter counter = new Counter();
        long start = System.nanoTime();
        algorithm.search(data, key, counter);
        long end = System.nanoTime();
        return new AlgorithmStats((end - start) / 1_000_000.0, counter.comparisons, counter.swaps);
    }

    public static AlgorithmStats measureSort(int[] data, SortAlgorithm algorithm) {
        int[] copy = Arrays.copyOf(data, data.length);
        Counter counter = new Counter();
        long start = System.nanoTime();
        algorithm.sort(copy, counter);
        long end = System.nanoTime();
        return new AlgorithmStats((end - start) / 1_000_000.0, counter.comparisons, counter.swaps);
    }

    // =========================================================
    // Searching Algorithms
    // =========================================================

    public static int linearSearch(int[] array, int key, Counter counter) {
        for (int i = 0; i < array.length; i++) {
            counter.comparisons++;
            if (array[i] == key)
                return i;
        }
        return -1;
    }

    public static int binarySearch(int[] array, int key, Counter counter) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            counter.comparisons++;
            if (array[mid] == key)
                return mid;
            counter.comparisons++;
            if (array[mid] < key)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    // =========================================================
    // Sorting Algorithms
    // =========================================================

    public static void quickSort(int[] arr, Counter counter) {
        quickSortHelper(arr, 0, arr.length - 1, counter);
    }

    private static void quickSortHelper(int[] arr, int low, int high, Counter counter) {
        if (low >= high) {
            return;
        }
        int pivotIndex = partition(arr, low, high, counter);
        quickSortHelper(arr, low, pivotIndex - 1, counter);
        quickSortHelper(arr, pivotIndex + 1, high, counter);
    }

    private static int partition(int[] arr, int low, int high, Counter counter) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            counter.comparisons++;
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j, counter);
            }
        }
        swap(arr, i + 1, high, counter);
        return i + 1;
    }

    public static void mergeSort(int[] arr, Counter counter) {
        mergeSortHelper(arr, 0, arr.length - 1, counter);
    }

    private static void mergeSortHelper(int[] arr, int left, int right, Counter counter) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(arr, left, mid, counter);
            mergeSortHelper(arr, mid + 1, right, counter);
            merge(arr, left, mid, right, counter);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right, Counter counter) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        System.arraycopy(arr, left, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            counter.comparisons++;
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
            counter.swaps++;
        }

        while (i < n1) {
            arr[k++] = leftArr[i++];
            counter.swaps++;
        }

        while (j < n2) {
            arr[k++] = rightArr[j++];
            counter.swaps++;
        }
    }

    public static void heapSort(int[] arr, Counter counter) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i, counter);
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i, counter);
            heapify(arr, i, 0, counter);
        }
    }

    private static void heapify(int[] arr, int n, int i, Counter counter) {
        int largest = i, left = 2 * i + 1, right = 2 * i + 2;

        if (left < n) {
            counter.comparisons++;
            if (arr[left] > arr[largest])
                largest = left;
        }

        if (right < n) {
            counter.comparisons++;
            if (arr[right] > arr[largest])
                largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest, counter);
            heapify(arr, n, largest, counter);
        }
    }

    public static void bubbleSort(int[] arr, Counter counter) {
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - i - 1; j++) {
                counter.comparisons++;
                if (arr[j] > arr[j + 1])
                    swap(arr, j, j + 1, counter);
            }
    }

    public static void insertionSort(int[] arr, Counter counter) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i], j = i - 1;
            while (j >= 0) {
                counter.comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    counter.swaps++;
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
    }

    public static void selectionSort(int[] arr, Counter counter) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                counter.comparisons++;
                if (arr[j] < arr[minIndex])
                    minIndex = j;
            }
            if (minIndex != i)
                swap(arr, minIndex, i, counter);
        }
    }

    public static void shellSort(int[] arr, Counter counter) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap; j -= gap) {
                    counter.comparisons++;
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        counter.swaps++;
                    } else {
                        break;
                    }
                }
                arr[j] = temp;
            }
        }
    }

    private static void swap(int[] arr, int i, int j, Counter counter) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        counter.swaps++;
    }

    // =========================================================
    // Helper Classes and Interfaces
    // =========================================================

    static class Counter {
        long comparisons = 0;
        long swaps = 0;
    }

    static class AlgorithmStats {
        double time;
        long comparisons;
        long swaps;

        AlgorithmStats(double time, long comparisons, long swaps) {
            this.time = time;
            this.comparisons = comparisons;
            this.swaps = swaps;
        }
    }

    @FunctionalInterface
    interface SortAlgorithm {
        void sort(int[] array, Counter counter);
    }

    @FunctionalInterface
    interface SearchAlgorithm {
        int search(int[] array, int key, Counter counter);
    }
}