package p6_allPathsInMazeDFSAlgoritm;

import java.awt.List;
import java.util.ArrayList;

import p6_allPathsInMazeDFSAlgoritm.Tuple;

public class AllPathsInMazeDFS {
	
	private static final char[][] maze = { 
			{' ', ' ', ' ', '*', ' ', ' ', ' '},
			{'*', '*', ' ', '*', ' ', '*', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', '*', '*', '*', '*', '*', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', 'e'},
			};
	
	 public static void main(String[] args) {
	        
	        String path = "";
	        generatePath(0, 0, path);
	    }

	    private static void generatePath(int row, int col, String path) {
	        if (!isValid(row, col)) {
	            return;
	        }
	        if (maze[row][col] == 'e') {
	            System.out.println(path);
	            return;
	        }
	        maze[row][col] = '.';

	        generatePath(row, col + 1, path + "R");
	        generatePath(row + 1, col, path + "D");
	        generatePath(row, col - 1, path + "L");
	        generatePath(row - 1, col, path + "U");

	        maze[row][col] = ' ';
	    }

	    private static boolean isValid(int row, int col) {
	        return row >= 0 && row < maze.length && col >= 0 && col < maze[row].length
	                && maze[row][col] != '*' && maze[row][col] != '.';
	    }
}
