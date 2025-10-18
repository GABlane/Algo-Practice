import java.util.*;

public class Algo {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int array[] = { 1000, 5000, 10000, 50000, 100000 };
        Random random = new Random();
        System.out.println(random);
        System.out.println("===========================");
        System.out.println("What would you like to do?");
        System.out.println("1 - Liniear Search");
        System.out.println("2 - Binary Search");
        System.out.println("3 - Quick Sort");
        System.out.println("4 - Merge Sort");
        System.out.println("5 - Heap Sort");
        System.out.println("6 - Bubble Sort");
        System.out.println("7 - Insertion Sort");
        System.out.println("8 - Selection Sort");
        System.out.println("9 - Shell Sort");
        System.out.println("10 - Compare All Algorithms");
        System.out.println("===========================");

        int choice = in.nextInt();
        switch (choice) {
            case 1:

                System.out.println("Linear Search");

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;

            case 6:

                break;

            case 7:

                break;

            case 8:

                break;

            case 9:

                break;

            case 10:

                break;

            default:
                break;
        }

    }

    public static int linearSearch(int[] array, int key) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == key) {
                return i;
            }
        }
        return -1;
    }
}
