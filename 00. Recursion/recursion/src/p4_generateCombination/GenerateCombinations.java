package p4_generateCombination;

import p2_RecursiveFactoriel.RecursiveFactoriel;

public class GenerateCombinations {

	private static int ARR_INDEX = 0;
	
	private static final int COMBINATIONS = 3;

	public static void main(String[] args) {
		int startNum = 4;
		int endNum = 8;

		int[] arr = new int[COMBINATIONS];
		genCombinationsRecursive(arr, ARR_INDEX, startNum, endNum);
		
		//Този вариант с Loops, ще работи по бързо! Но не е гъвкав! Как ще сме сигурни, че всеки път COMBINATIONS ще са 3? Aко са повече, или по малко трябва да добавяме или шремахваме Loop
//		genCombinationsByLoops(startNum, endNum);
	}

	//RECURSIVE WAI
	private static void genCombinationsRecursive(int[] arr, int index, int startNum, int endNum) {
		if (index == arr.length) {
			print(arr);
			return;
		}

		for (int i = startNum; i <= endNum; i++) {
			arr[index] = i;
			genCombinationsRecursive(arr, index + 1, i + 1, endNum);
		}
	}
	
	//BY LOOPS
	private static void genCombinationsByLoops(int startNum, int endNum) {
		for (int i = startNum; i <= endNum; i++) {
			
			for (int j= i+1; j <= endNum; j++) {
				
				for (int k = j+1; k <= endNum; k++) {
					System.out.println(String.format("%d %d %d", i,j,k));
				}
			}
		}
		
	}
	
	private static void print(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}
