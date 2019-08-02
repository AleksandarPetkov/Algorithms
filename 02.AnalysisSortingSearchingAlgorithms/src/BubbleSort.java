//Тръгва от начало, гледа съседния елемент. Ако е по малък ги разменя. Това нещо се прави до края, и започва на ново докато всички не са сортирани! (do-while)
//Много бавен О(n2)
//Stable
//Memory - O(1): Ползва константна памет (Не изполва допълнителни Масиви, Рекурсия)
//Use Bubble sort: For small arrays (less than 20-30 elements). 

public class BubbleSort {

	//fix
	private static int[] array = { 44, 31, 4, 1, 535353};

	public static void main(String[] args) {

		boolean swapped = false;
		do {
			swapped = false;
			for (int i = 0; i < array.length - 1; i++) {
				
				if (array[i] > array[i + 1]) {
					swapIndexes(i, i+1);
					swapped = true;
				}
			}

		} while (swapped);

		printArray();
	}

	static void swapIndexes(int j, int i) {
		int swappedValue = array[i];
		array[i] = array[j];
		array[j] = swappedValue;
	}

	static void printArray() {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
}
