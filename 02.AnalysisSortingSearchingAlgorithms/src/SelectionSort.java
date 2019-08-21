//Намира най малки елемент и го разменя, по този начин всичко от ляво на текущия индекс е сортирано
//Много бавен О(n2)
//Unstable
//Memory - O(1): Ползва константна памет (Не изполва допълнителни Масиви, Рекурсия)
//Use Selection sort: For small arrays (less than 20-30 elements). 
public class SelectionSort {
	
	private static int[] array = {44, 31, 4, 23, 1, 6666, 12};
	
	public static void main(String[] args) {
		
		for (int i = 0; i < array.length - 1; i++) {
			/* set current element as minimum*/
			int minIndex = i;
			for (int j = i + 1; j < array.length; j++) {
				/* check the element to be minimum */
				if (array[j] < array[minIndex]) {
					minIndex = j;
				}
			}
			/* swap the minimum element with the current element*/
			swapIndexes(minIndex, i);
		}
		
		printArray();
	}

   static void swapIndexes(int minIndex, int i) {
		int swappedValue = array[i];
		array[i] = array[minIndex];
		array[minIndex] = swappedValue;
	}
   
   static void printArray() {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
}
