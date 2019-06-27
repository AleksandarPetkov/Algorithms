package p5_8QueensPuzzle;

import java.util.HashSet;
import java.util.Set;

public class QueenPuzzle {
	private static final int STARTING_ROW = 0;
	private static final int SIZE = 8;
	private static boolean[][] chessBoard = new boolean[SIZE][SIZE];
	
	private static Set<Integer> attackedRowSet = new HashSet<>();
	private static Set<Integer> attackedColumns = new HashSet<>();

	private static Set<Integer> attackedLeftDiagonals = new HashSet<>();
	private static Set<Integer> attackedRightDiagonals= new HashSet<>();

	
	// 'BACKTRACKING' => class of algorithms for finding all solutions to some combinatorial problem
	// Измъква ни от задънена улица, реже всички пътища които са невъзмжни
	public static void main(String[] args) {
		putQueen(STARTING_ROW);
	}
	
	private static void putQueen(int row) {
		if (row == SIZE) {
			//TODO Print
			return;
		}
		
		for (int col = 0; col < SIZE; col++) {
			if (canPlaceQueen(row, col)) {
				markAttackedPositions(row, col);
				putQueen(row + 1);
				deleteAttackedPositions(row, col);
			}
		}
	}
	

	private static boolean canPlaceQueen(int row, int col) {
		return true;
	}
	
	private static void deleteAttackedPositions(int row, int col) {
		
	}

	private static void markAttackedPositions(int row, int col) {
		
	}

}
