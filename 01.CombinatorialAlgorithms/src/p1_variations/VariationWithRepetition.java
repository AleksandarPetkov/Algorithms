package p1_variations;

import java.util.Iterator;

public class VariationWithRepetition {

	// При вариацийте имам повторение:
	// Пример: Имам, apple banana orange
	// Мога да взема apple apple apple (имам повторения)
	// apple apple banana (имам повторения)
	//Ex 10 X 10 X 10
	private static final int k = 3;
	private static final int n = 10; // n: is object.lenght
	static String[] objects = { "banana", "apple", "orange", "strawberry", "raspberry", "apricot", "cherry", "lemon",
			"grapes", "melon" };

	static String[] arr = new String[k];

	public static void main(String[] args) {
		generateVariationsWithRepetitions(0);
	}

	private static void generateVariationsWithRepetitions(int index) {
		if (index == k) {
		printArray(); 
			return;
		}
		
		for (int i = 0; i < objects.length; i++) {
			arr[index] = objects[i];
			generateVariationsWithRepetitions(index + 1);
		}
	}
	
	private static void printArray() {
		for (String obj : arr) {
			System.out.println(obj + " ");
		}
		System.out.println();
	}

}
