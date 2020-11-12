package LinkedIn.PhoneScreen;

public class ArraysSort {
    public int[] mergeSort(int[] array) {
        if (array == null) {
            return array;
        }
        int[] helper = new int[array.length]; // used for store temp state array
        mergeSort(array, helper, 0, array.length - 1);
        return array;
    }
    private void mergeSort(int[] array, int[] helper, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(array, helper, left, mid);
        mergeSort(array, helper, mid + 1, right);
        merge(array, helper, left, mid, right);
    }
    private void merge(int[] array, int[] helper, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            helper[i] = array[i];
        }
        int leftIndex = left;
        int rightIndex = mid + 1;
        int index = left;
        while (leftIndex <= mid && rightIndex <= right) {
            if (array[leftIndex] <= array[rightIndex]) {
                helper[index++] = array[leftIndex++];
            } else {
                helper[index++] = array[rightIndex++];
            }
        }
        while (leftIndex <= mid) {
            helper[index++] = array[leftIndex++];
        }
        for (int i = 0; i <= right; i++) {
            array[i] = helper[i];
        }
    }

    public int[] quickSort(int[] array) {
        quickSortHelper(array, 0, array.length - 1);
        return array;
    }
    // set up [left, right] range to do quickSort
    private void quickSortHelper(int[] array, int left, int right) {
        if (left > right) {
            return;
        }
        int pivotIndex = left + (int)(Math.random() * (right - left + 1));
        int pivot = array[pivotIndex];
        swap(array, pivot, right);
        int leftIndex = left;
        int rightIndex = right - 1;
        while (leftIndex <= rightIndex) {
            if (array[leftIndex] > pivot) {
                swap(array, leftIndex, rightIndex--);
            } else {
                leftIndex++;
            }
        }
        swap(array, leftIndex, right);
        // leftIndex would be pivot's position, recursion shouldn't include pivot
        quickSortHelper(array, left, rightIndex);
        quickSortHelper(array, leftIndex + 1, right);
    }
    private void swap(int[] array, int left, int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }
}
