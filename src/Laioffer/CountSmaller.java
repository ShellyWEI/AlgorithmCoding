package Laioffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountSmaller {
    public int[] countArray(int[] array) {
        // Write your solution here
        int length = array.length;
        int[] indexArray = new int[length]; // help using index to find
        for (int i = 0; i < array.length; i++) {
            indexArray[i] = i;
        }
        int[] countArray = new int[length]; // final result
        int[] helper = new int[length]; // used to record each merge's index, for helping find matching original value
        mergeSort(array, helper, countArray, indexArray, 0, array.length - 1);
        return countArray;
    }
    private void mergeSort(int[] array, int[] helper, int[] count, int[] index, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(array, helper, count, index, left, mid);
        mergeSort(array, helper, count, index, mid + 1, right);
        merge(array, helper, count, index, left, mid + 1, right);
    }
    private void merge(int[] array, int[] helper, int[] count, int[] index, int leftIndex, int rightIndex, int rightEnd) {
        // copy current index position
        copyArray(index, helper, leftIndex, rightEnd);
        int leftEnd = rightIndex - 1;
        int curIndex = leftIndex;
        while (leftIndex <= leftEnd) {
            if (rightIndex > rightEnd || array[helper[leftIndex]] <= array[helper[rightIndex]]) {
                count[helper[leftIndex]] += (rightIndex - leftEnd - 1);
                index[curIndex++] = helper[leftIndex++];
            } else {
                index[curIndex++] = helper[rightIndex++];
            }
        }
    }
    private void copyArray(int[] from, int[] to, int left, int right) {
        while (left <= right) {
            from[left] = to[left];
            left++;
        }
    }
    public static void main (String[] args) {
        CountSmaller ts = new CountSmaller();
        int[] a = new int[]{4,1,6,2,5,3};
        ts.countArray(a);
        List<List<Integer>> res = new ArrayList<>();
        res.add(Arrays.asList(1,2,3));
    }
}
