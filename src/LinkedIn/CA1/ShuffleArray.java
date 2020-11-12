package LinkedIn.CA1;

public class ShuffleArray {
    int[] shuffled;
    int[] original;

    public ShuffleArray(int[] nums) {
        this.shuffled = new int[nums.length];
        this.original = nums;
    }

    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return original;
    }

    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        for (int i = 0; i < original.length; i++) {
            int index = (int)(Math.random() * i);
            shuffled[index] = original[i];
            shuffled[i] = original[index];
        }
        return shuffled;
    }

}
