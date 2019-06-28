package p5_8QueensPuzzle;

import java.util.HashSet;
import java.util.Set;

public class QueenPuzzle {
	private static final int STARTING_ROW = 0;
	private static final int SIZE = 8;
	private static boolean[][] chessBoard = new boolean[SIZE][SIZE];

	private static Set<Integer> attackedRows = new HashSet<>();
	private static Set<Integer> attackedColumns = new HashSet<>();

	private static Set<Integer> attackedLeftDiagonals = new HashSet<>();
	private static Set<Integer> attackedRightDiagonals = new HashSet<>();

	// 'BACKTRACKING' => class of algorithms for finding all solutions to some
	// combinatorial problem
	// Измъква ни от задънена улица, реже всички пътища които са невъзмoжни
	public static void main(String[] args) {
		putQueen(STARTING_ROW);
	}

	private static void putQueen(int row) {
		if (row == SIZE) {
			printChessBoard();
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
		boolean isPositionOccupied = attackedRows.contains(row) || attackedColumns.contains(col)
				|| attackedLeftDiagonals.contains(col - row) || attackedRightDiagonals.contains(row + col);

		return !isPositionOccupied;
	}

	private static void markAttackedPositions(int row, int col) {
		attackedRows.add(row);
		attackedColumns.add(col);
		attackedLeftDiagonals.add(col - row);
		attackedRightDiagonals.add(col + row);
		
		chessBoard[row][col]=true;
	}

	private static void deleteAttackedPositions(int row, int col) {
		attackedRows.remove(row);
		attackedColumns.remove(col);
		attackedLeftDiagonals.remove(col - row);
		attackedRightDiagonals.remove(col + row);
		
		chessBoard[row][col]=false;
	}
	
	private static void printChessBoard() {
		for (int row = 0; row < SIZE; row++) {
			
			for (int col = 0; col < SIZE; col++) {
				if (chessBoard[row][col]==true) {
					System.out.print("*");
				} else {
					System.out.print("-");
				}
			}
			System.out.println();
		}
		System.out.println();
		
	}
}
