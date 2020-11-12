package LinkedIn.CA2;

import java.util.Arrays;

public class KLargest {
    // TODO: Quick Select
    public int kthLargest (int[] nums, int k) {
        quickSelect(nums, 0, nums.length - 1, k - 1);
        Arrays.copyOf(nums, k);
        Arrays.sort(nums);
        return nums[k - 1];
    }
    //
    private void quickSelect(int[] nums, int left, int right, int k) {
        int pivotIndex = partition(nums, left, right);
        // if pivot's left side is all bigger than target and size is k - 1, we find kth one
        if (pivotIndex == k) {
            return;
        } else if (pivotIndex < k) {
            // left sides is not enough, find more k - pivotIndex on right side
            quickSelect(nums, pivotIndex + 1, right,  k);
        } else {
            quickSelect(nums, left, pivotIndex - 1, k);
        }
    }
    private int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int end = right - 1;
        while (left <= end) {
            if (nums[left] > pivot) {
                left++;
            } else if (nums[end] < pivot) {
                end--;
            } else {
                swap(nums, left++, end--);
            }
        }
        swap(nums, right, left);
        return left;
    }
    private void swap(int[] nums, int left, int right) {
        if (left == right) {
            return;
        }
        int num = nums[left];
        nums[left] = nums[right];
        nums[right] = num;
    }

    public static void main(String[] args) {
        KLargest test = new KLargest();
        int[] nums = new int[]{1,4,5,3,6,8,9,2,7};
        System.out.println(test.kthLargest(nums, 6));
    }
}
