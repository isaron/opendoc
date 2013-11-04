package sort;

public class InsertionSort {

	public static void main(String[] args) {
		int[] nums = { 5, 2, 4, 6, 1, 3 };

		insertionSort(nums);

		for (int n : nums) {
			System.out.println(n);
		}
	}

	public static void insertionSort(int[] nums) {
		int key;

		for (int i = 1; i < nums.length; i++) {
			key = nums[i];

			// iterate previous elements to insert element
			for (int j = i - 1; j >= 0; j--) {
				
				if (nums[j] > key) {
					nums[j + 1] = nums[j];  // move element
					nums[j] = key;
				}
			}
		}
	}
}
