import java.util.*;

public class SortAlgorithms {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for array size
        System.out.println("Enter the number of elements in the array:");
        int n = scanner.nextInt();

        // Declare and input the array elements
        int[] arr = new int[n];
        System.out.println("Enter the elements of the array:");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        // Sorting menu
        System.out.println("Original array: " + Arrays.toString(arr));
        System.out.println("Which sorting algorithm would you like to use?");
        System.out.println("1. Merge Sort");
        System.out.println("2. Quick Sort");
        System.out.println("3. Insertion Sort");
        System.out.println("4. Heap Sort");
        System.out.println("5. Radix Sort");
        System.out.println("6. Bucket Sort");

        int choice = scanner.nextInt();

        // Create a copy 
        int[] arrCopy = Arrays.copyOf(arr, arr.length);

        switch (choice) {
            case 1:
                System.out.println("\nMerge Sort:");
                mergeSort(arrCopy, 0, arrCopy.length - 1);
                break;
            case 2:
                System.out.println("\nQuick Sort:");
                quickSort(arrCopy, 0, arrCopy.length - 1);
                break;
            case 3:
                System.out.println("\nInsertion Sort:");
                insertionSort(arrCopy);
                break;
            case 4:
                System.out.println("\nHeap Sort:");
                heapSort(arrCopy);
                break;
            case 5:
                System.out.println("\nRadix Sort:");
                radixSort(arrCopy);
                break;
            case 6:
                System.out.println("\nBucket Sort:");
                bucketSort(arrCopy);
                break;
            default:
                System.out.println("Invalid choice. Please select a number between 1 and 6.");
                scanner.close();
                return;
        }

        // Print the sorted array
        System.out.println("Sorted array: " + Arrays.toString(arrCopy));
        scanner.close();
    }

    // Merge Sort
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] leftArr = Arrays.copyOfRange(arr, left, mid + 1);
        int[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);
        int i = 0, j = 0, k = left;

        while (i < leftArr.length && j < rightArr.length) {
            arr[k++] = (leftArr[i] <= rightArr[j]) ? leftArr[i++] : rightArr[j++];
        }
        while (i < leftArr.length) arr[k++] = leftArr[i++];
        while (j < rightArr.length) arr[k++] = rightArr[j++];
    }

    // Quick Sort
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp=arr[i];
                arr[i]=arr[j];
                arr[j]=temp;
            }
        }
        i++;
        int temp=arr[i];
        arr[i]=pivot;
        arr[high]=temp;
        return i ;
    }

    // Insertion Sort
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i], j = i - 1;
            while (j >= 0 && arr[j] > key) arr[j + 1] = arr[j--];
            arr[j + 1] = key;
        }
    }

    // Heap Sort
    public static void heapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i, left = 2 * i + 1, right = 2 * i + 2;
        if (left < n && arr[left] > arr[largest]) largest = left;
        if (right < n && arr[right] > arr[largest]) largest = right;
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    // Radix Sort
    public static void radixSort(int[] arr) {
        int max = Arrays.stream(arr).max().orElse(0);
        for (int exp = 1; max / exp > 0; exp *= 10) countingSort(arr, exp);
    }

    private static void countingSort(int[] arr, int exp) {
        int[] output = new int[arr.length];
        int[] count = new int[10];

        for (int num : arr) count[(num / exp) % 10]++;
        for (int i = 1; i < 10; i++) count[i] += count[i - 1];
        for (int i = arr.length - 1; i >= 0; i--) output[--count[(arr[i] / exp) % 10]] = arr[i];
        System.arraycopy(output, 0, arr, 0, arr.length);
    }

    // Bucket Sort
    public static void bucketSort(int[] arr) {
        int max = Arrays.stream(arr).max().orElse(0);
        int min = Arrays.stream(arr).min().orElse(0);
        int bucketCount = Math.max(1, (max - min) / arr.length + 1);

        List<List<Integer>> buckets = new ArrayList<>(Collections.nCopies(bucketCount, null));
        for (int i = 0; i < bucketCount; i++) buckets.set(i, new ArrayList<>());

        for (int num : arr) buckets.get((num - min) / arr.length).add(num);
        int index = 0;
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for (int num : bucket) arr[index++] = num;
        }
    }

    // Swap helper function
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

