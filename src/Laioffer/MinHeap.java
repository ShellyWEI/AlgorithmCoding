package Laioffer;

import java.util.Arrays;

public class MinHeap {
    int[] array;
    int size;
    public MinHeap(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        this.array = array;
        this.size = array.length;
        heapify();
    }
    public Integer poll() {
        int polled = array[0];
        swap(array, 0, size - 1);
        size--;
        percolateDown(0);
        return polled;
    }
    public void offer(int value) {
        if (size == array.length) {
            //array = new int[(int)(size * 1.5)];
            Arrays.copyOf(array, (int)(size * 1.5));
        }
        array[size] = value;
        size++;
        percolateUp(size - 1);
    }
    private void heapify() {
        int lastNonLeafIndex = (size - 1) / 2;
        while (lastNonLeafIndex >= 0) {
            percolateDown(lastNonLeafIndex);
            lastNonLeafIndex--;
        }
    }
    private void percolateUp(int index) {
        while (index > 0 ) {
            int parentIndex = (index - 1) / 2;
            if (array[parentIndex] > array[index]) {
                swap(array, parentIndex, index);
            } else {
                break;
            }
            index = parentIndex;
        }

    }
    // index < size / 2 - 1, otherwise it will be leaf node
    // note: right child can be null
    private void percolateDown(int index) {
        if (index < array.length - 1) {
            return;
        }
        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;
        if (array[leftChild] < array[rightChild]) {
            if (array[index] > array[leftChild]) {
                swap(array, index, leftChild);
                percolateDown(leftChild);
            }
        } else {
            if (array[index] > array[rightChild]) {
                swap(array, index, rightChild);
                percolateDown(rightChild);
            }
        }

    }
    private void swap(int[] array, int left, int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }
}
