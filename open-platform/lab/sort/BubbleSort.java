package sort;

public class BubbleSort {

	public static void main(String[] args) {
		int[] nums = { 5, 2, 4, 6, 1, 3 };

		bubbleSort(nums);

		for (int n : nums) {
			System.out.println(n);
		}
	}

	public static void bubbleSort(int[] nums) {
		/* iterate sort array from second element */
		for (int i = 1; i < nums.length; i++) {
			
			/* move lowest number to bottom */
			for (int j = nums.length - 1; j >= i; j--) {
				
				/* swap lower number to previous element */
				if (nums[j] < nums[j - 1]) {
					int temp = nums[j];
					nums[j] = nums[j - 1];
					nums[j - 1] = temp;
				}
			}
		}
	}
}
