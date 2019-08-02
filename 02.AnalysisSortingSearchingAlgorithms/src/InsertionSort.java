// Бавех О(n2)
// Stable
//Най добрия алгоритъм за сортиране на малък брой елементи от 1 до 20 ел.
//Подобен на Bubble sort (разменя индекс), той разчита на това че веднъж щом хвана елемент всички преди него са сортирани
public class InsertionSort {
	static int[] array = { 44, 31, 4, 1, 535353 };

	public static void main(String[] args) {

		for (int i = 1; i < array.length; ++i) {
			int key = array[i];
			int j = i - 1;

			/*
			 * Move elements of arr[0..i-1], that are greater than key, to one position
			 * ahead of their current position
			 */
			while (j >= 0 && array[j] > key) {
				array[j + 1] = array[j];
				j = j - 1;
			}
			array[j + 1] = key;
		}
		printArray();
	}

	static void printArray() {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
}
