package p1_RecursiveSumArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class RecursiveSumArray {
	static int index = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int[] array = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

		System.out.println(sumArray(array, index));
	}

	private static int sumArray(int[] arr, int index) {
		if (arr.length == index) {
			return 0;
		}
		// Взима arr[index] и сумата всичко от дясно на arr[index]
		//Пример: arr = { 1 2 3 } 
		//arr[index] = 3 + 0 - Last call in stack
		//arr[index] = 2 + 3(sum)
		//arr[index] = 1 + 5(sum)
		//return result
		return arr[index] += sumArray(arr, index + 1);
		
	}
}
