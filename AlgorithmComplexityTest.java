import java.util.*;

/**
 * Algorithm Performance Tester
 * ----------------------------
 * Measures and compares the runtime (in milliseconds)
 * of different searching and sorting algorithms
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
        System.out.printf("%-10s %-15s%n", "Size(n)", algorithm + " (ms)");

        for (int n : dataSizes) {
            int[] data = generateRandomArray(n, random);
            int key = data[random.nextInt(n)]; // Pick a random key to search for
            double duration = 0;

            switch (algorithm) {
                case "Linear Search" -> duration = measure(() -> linearSearch(data, key));
                case "Binary Search" -> {
                    Arrays.sort(data); // Binary search requires a sorted array
                    duration = measure(() -> binarySearch(data, key));
                }
                case "Quick Sort" -> duration = measureSort(data, AlgorithmComplexityTest::quickSort);
                case "Merge Sort" -> duration = measureSort(data, AlgorithmComplexityTest::mergeSort);
                case "Heap Sort" -> duration = measureSort(data, AlgorithmComplexityTest::heapSort);
                case "Bubble Sort" -> duration = measureSort(data, AlgorithmComplexityTest::bubbleSort);
                case "Insertion Sort" -> duration = measureSort(data, AlgorithmComplexityTest::insertionSort);
                case "Selection Sort" -> duration = measureSort(data, AlgorithmComplexityTest::selectionSort);
                case "Shell Sort" -> duration = measureSort(data, AlgorithmComplexityTest::shellSort);
            }

            System.out.printf("%-10d %-15.2f%n", n, duration);
        }
    }

    public static void runAllAlgorithms(int[] dataSizes, Random random) {
        System.out.printf(
                "%-10s %-13s %-13s %-13s %-13s %-13s %-13s %-13s %-13s %-13s%n",
                "Size(n)", "Linear", "Binary", "Quick", "Merge", "Heap",
                "Bubble", "Insertion", "Selection", "Shell");

        for (int n : dataSizes) {
            int[] baseArray = generateRandomArray(n, random);
            int key = baseArray[random.nextInt(n)];

            // --- Searching ---
            double linear = measure(() -> linearSearch(baseArray, key));

            // For a fair comparison, binary search is timed on a pre-sorted array
            int[] sortedForSearch = Arrays.copyOf(baseArray, baseArray.length);
            Arrays.sort(sortedForSearch);
            double binary = measure(() -> binarySearch(sortedForSearch, key));

            // --- Sorting ---
            // Each sorting algorithm gets an identical copy of the unsorted array
            double quick = measureSort(baseArray, AlgorithmComplexityTest::quickSort);
            double merge = measureSort(baseArray, AlgorithmComplexityTest::mergeSort);
            double heap = measureSort(baseArray, AlgorithmComplexityTest::heapSort);
            double bubble = measureSort(baseArray, AlgorithmComplexityTest::bubbleSort);
            double insertion = measureSort(baseArray, AlgorithmComplexityTest::insertionSort);
            double selection = measureSort(baseArray, AlgorithmComplexityTest::selectionSort);
            double shell = measureSort(baseArray, AlgorithmComplexityTest::shellSort);

            System.out.printf(
                    "%-10d %-13.2f %-13.2f %-13.2f %-13.2f %-13.2f %-13.2f %-13.2f %-13.2f %-13.2f%n",
                    n, linear, binary, quick, merge, heap, bubble, insertion, selection, shell);
        }
    }

    // =========================================================
    // Utility Functions
    // =========================================================

    public static int[] generateRandomArray(int size, Random random) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
            array[i] = random.nextInt(1_000_000); // Numbers between 0 and 999,999
        return array;
    }

    public static double measure(Runnable task) {
        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();
        return (end - start) / 1_000_000.0; // Convert nanoseconds to milliseconds
    }

    public static double measureSort(int[] data, SortAlgorithm algorithm) {
        // Create a copy so the original array is not modified
        int[] copy = Arrays.copyOf(data, data.length);
        return measure(() -> algorithm.sort(copy));
    }

    // =========================================================
    // Searching Algorithms
    // =========================================================

    /**
     * Linear Search: O(n)
     */
    public static int linearSearch(int[] array, int key) {
        for (int i = 0; i < array.length; i++)
            if (array[i] == key)
                return i;
        return -1;
    }

    /**
     * Binary Search: O(log n) - Requires a sorted array
     */
    public static int binarySearch(int[] array, int key) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == key)
                return mid;
            else if (array[mid] < key)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    // =========================================================
    // Sorting Algorithms
    // =========================================================

    /**
     * Quicksort: Average O(n log n), Worst O(n^2)
     */
    public static void quickSort(int[] arr) {
        quickSortHelper(arr, 0, arr.length - 1);
    }

    private static void quickSortHelper(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivotIndex = partition(arr, low, high);
        quickSortHelper(arr, low, pivotIndex - 1);
        quickSortHelper(arr, pivotIndex + 1, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    /**
     * Merge Sort: O(n log n)
     */
    public static void mergeSort(int[] arr) {
        if (arr.length < 2)
            return;
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        mergeSort(left);
        mergeSort(right);

        merge(arr, left, right);
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length)
            arr[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        while (i < left.length)
            arr[k++] = left[i++];
        while (j < right.length)
            arr[k++] = right[j++];
    }

    /**
     * Heap Sort: O(n log n)
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i, left = 2 * i + 1, right = 2 * i + 2;
        if (left < n && arr[left] > arr[largest])
            largest = left;
        if (right < n && arr[right] > arr[largest])
            largest = right;
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    /**
     * Bubble Sort: O(n^2)
     */
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - i - 1; j++)
                if (arr[j] > arr[j + 1])
                    swap(arr, j, j + 1);
    }

    /**
     * Insertion Sort: O(n^2)
     */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i], j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * Selection Sort: O(n^2)
     */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[minIndex])
                    minIndex = j;
            swap(arr, minIndex, i);
        }
    }

    /**
     * Shell Sort: O(n (log n)^2) or O(n^1.5) depending on gap sequence
     */
    public static void shellSort(int[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
                    arr[j] = arr[j - gap];
                arr[j] = temp;
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // =========================================================
    // Functional Interface for Sorting
    // =========================================================
    @FunctionalInterface
    interface SortAlgorithm {
        void sort(int[] array);
    }
}
