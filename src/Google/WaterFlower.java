package Google;

public class WaterFlower {
    public int water(int[] plant, int capacityA, int capacityB) {
        int remainCapacityA = capacityA;
        int remainCapacityB = capacityB;
        int left = 0; // A starting position
        int right = plant.length - 1; // B starting position
        int refillCount = 0;
        while (left < right) {
            if (remainCapacityA >= plant[left] && remainCapacityB >= plant[right]) {
                // both can move forward to next flower
                remainCapacityA -= plant[left];
                remainCapacityB -= plant[right];
                left++;
                right--;
                System.out.println("both move forward to " + left + " and " + right);
            } else if (remainCapacityA < plant[left] && remainCapacityB < plant[right]) {
                // both not move forward to next
                plant[left] -= remainCapacityA;
                plant[right] -= remainCapacityB;
                refillCount += 2;
                remainCapacityA = capacityA;
                remainCapacityB = capacityB;
                System.out.println("both refill at " + left + " and " + right);
            } else {
                // either one would go back to refill
                // because exhausting current load would minimize times to refill
                refillCount++;
                if (remainCapacityA < plant[left]) {
                    plant[left] -= remainCapacityA;
                    remainCapacityA = capacityA;
                    System.out.println("A refill plant at "+ left);
                } else {
                    plant[right] -= remainCapacityB;
                    remainCapacityB = capacityB;
                    System.out.println("B refill plant at "+ right);
                }
            }
        }
        // in odd plants, both water the same flower and only one has
        int biggerCapacity = Math.max(capacityA, capacityB);
        if (left == right) {
            while (remainCapacityA + remainCapacityB < plant[left]) {
                // only one refill can water the flower
                if (Math.max(capacityA + remainCapacityB, capacityB + remainCapacityA) > plant[left]) {
                    refillCount++;
                    break;
                }
                // otherwise both need to refill
                plant[left] -= (remainCapacityA + remainCapacityB);
                remainCapacityA = capacityA;
                remainCapacityB = capacityB;
                refillCount += 2;
            }
        }
        return refillCount;
    }
    public static void main(String[] args) {
        WaterFlower test = new WaterFlower();
        int[] plant = new int[]{2,4,5,1,9};
        System.out.println(test.water(plant, 2, 3));
    }
}
