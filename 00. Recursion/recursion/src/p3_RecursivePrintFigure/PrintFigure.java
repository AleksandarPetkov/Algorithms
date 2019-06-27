package p3_RecursivePrintFigure;

import java.util.Scanner;

//PRE-ACTION - POST ACTION PROBLEM
public class PrintFigure {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int count = in.nextInt();
		
		printFigure(count);
	}
	
	private static void printFigure(int n) {
		if (n == 0) {
			return;
		}
		
		// PRE-ACTION
		System.out.println(draw("-", n));
		
		//Example: IF n=3; Before method invoke itself Print
		//---
		//--
		//-
		printFigure(n - 1);
		// Starting pop from Stack
		//#
		//##
		//###
		
		//POST ACTION
		System.out.println(draw("#", n));
	}
	
    public static String draw(String symbol, int n) {
		String line = "";
		for (int i = 0; i < n; i++) {
			line += symbol;
		}
		return line;
	}
}
