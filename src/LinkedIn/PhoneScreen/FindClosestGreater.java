package LinkedIn.PhoneScreen;

// find smallest Greater character than target, if not return first
public class FindClosestGreater {
    public char findClosest(String sortedString, char target) {
        char[] array = sortedString.toCharArray();
        int left = 0;
        int right = array.length - 1;
        while (left < right - 1) {
            int mid = (left + right) / 2;
            if (array[mid] <= target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        if (array[left] > target) {
            return array[left];
        }
        if (array[right] > target) {
            return array[right];
        } else {
            int returnedIndex = right + 1 >= array.length ? right - array.length + 1 : right + 1;
            return array[returnedIndex];
        }
    }
}
