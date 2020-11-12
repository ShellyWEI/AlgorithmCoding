package LinkedIn.PhoneScreen;

public class SearchRotatedSortedArray {
    // 2 binary search: find pivot index and use binary search for each part
    public int searchTargetByPivot(int[] sortedArray, int target) {
        // find pivot, sortedArray can't have duplicate nums
        int left = 0;
        int right = sortedArray.length - 1;
        int pivot = sortedArray[0];
        // find interval that array[left] > array[right];
        while (left < right - 1) {
            int mid = (left + right) / 2;
            if (sortedArray[mid] > pivot) {
                left = mid;
            } else if (sortedArray[mid] < pivot) {
                right = mid;
            }
        }
        int leftResult = binarySearch(0, left, sortedArray, target);
        int rightResult = binarySearch(right, sortedArray.length - 1, sortedArray, target);
        return leftResult > 0 ? leftResult : rightResult;
    }
    private int binarySearch(int left, int right, int[] array, int target) {
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] < target) {
                left = mid + 1;
            } else if (array[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    // 1 binary search
    public int searchTargetByInf(int[] sortedArray, int target) {
        int left = 0;
        int right = sortedArray.length - 1;
        int leftMin = sortedArray[0];
        while (left <= right) {
            int mid = (left + right) / 2;
            // mid and target on the same part of array
            if (sortedArray[mid] == target) {
                return mid;
            }
            int num;
            if ((sortedArray[mid] < leftMin) == (target < leftMin)) {
                num = sortedArray[mid];
            } else {
                // if they are not in the same side, change left
                num = sortedArray[mid] < leftMin ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            if (num < target) {
                left = mid + 1;
            } else if (num > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
