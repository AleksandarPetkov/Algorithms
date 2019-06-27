package p2_RecursiveFactoriel;

import java.util.Scanner;

public class RecursiveFactoriel {
	// Call Stack - Специално парче памет, където пази извиканите методи.
	// Методът който е най-горе е този който в момента се изпълнява. Всички под него
	// са изчакващи
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		int num = in.nextInt();
		
		System.out.println(recursiveFactoriel(num));
	}

	private static int recursiveFactoriel(int num) {
		if (num == 0) {
			return 1;
		}
		int result = num * recursiveFactoriel(num - 1);
		return result;
	}
}
