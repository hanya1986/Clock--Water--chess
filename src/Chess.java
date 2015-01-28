/*
 * GUI_part.java
 * 
 * Version:
 *   $Id: Chess.java,v 1.5 2014/12/11 02:15:15 yc7816 Exp $
 *   
 * Revision:
 *   $Log: Chess.java,v $
 *   Revision 1.5  2014/12/11 02:15:15  yc7816
 *   final version for submit
 *
 *   Revision 1.4  2014/12/11 00:40:24  yc7816
 *   final check and test
 *
 *   Revision 1.3  2014/12/11 00:26:58  yc7816
 *   Correcting the errors and adding the comments
 *
 *   Revision 1.2  2014/12/10 15:00:23  yc7816
 *   simplifying the code as possible
 *
 *   Revision 1.1  2014/12/10 00:38:40  yc7816
 *   Initial version
 *
 *  
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Class to run the Chess.
 * 
 * @Author's Login ID: yc7816
 * 
 * @Name: Yihao Cheng
 * 
 */

public class Chess extends Puzzle<Panel> {

	protected static int ROWS;
	protected static int COLUMNS;
	private Panel start;

	/**
	 * Constructor which takes in the size of the string and initial board
	 * 
	 * @param start
	 * @param ROWS
	 * @param COLS
	 */

	public Chess(ArrayList<ArrayList<String>> start, int ROWS, int COLS) {
		Chess.ROWS = ROWS;
		Chess.COLUMNS = COLS;
		this.start = new Panel(start);
	}

	/**
	 * Getting the initial starting board;
	 * 
	 * @return start
	 */
	@Override
	public Panel getStart() {
		return start;
	}

	/**
	 * check to see if the current board is the goal.
	 * 
	 * @param config
	 * @return x;
	 */
	@Override
	public boolean checkForGoal(Panel config) {
		boolean x = false;
		int count = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (!(config.board.get(i).get(j).equals("."))) {
					count++;
				}
			}
		}
		if (count == 1) {
			x = true;
		}
		return x;
	}

	/**
	 * Method to call for pieces that move straight directions.
	 * 
	 * @param r
	 * @param c
	 * @param piece
	 * @param config
	 * @param moveSteps
	 * @param column
	 */
	private void setMoveStraight(int r, int c, String piece, Panel config,
			ArrayList<Panel> moveSteps, boolean column) {
		if (column) {
			for (int i = c; i < COLUMNS; i++) {
				Panel current = config.getClone();
				if ((!(current.board.get(r).get(i).equals("."))) && (!(i == c))) {
					current.setBoard(r, i, piece);
					current.setBoard(r, c, ".");
					moveSteps.add(current);
					break;
				}
			}
			for (int i = c; i >= 0; i--) {
				Panel current = config.getClone();
				if ((!(current.board.get(r).get(i).equals("."))) && (!(i == c))) {
					current.setBoard(r, i, piece);
					current.setBoard(r, c, ".");
					moveSteps.add(current);
					break;

				}
			}
		} else {
			for (int i = r; i < ROWS; i++) {
				Panel current = config.getClone();
				if ((!(current.board.get(i).get(c).equals("."))) && (!(i == r))) {
					current.setBoard(i, c, piece);
					current.setBoard(r, c, ".");
					moveSteps.add(current);
					break;
				}
			}
			for (int i = r; i >= 0; i--) {
				Panel current = config.getClone();
				if ((!(current.board.get(i).get(c).equals("."))) && (!(i == r))) {
					current.setBoard(i, c, piece);
					current.setBoard(r, c, ".");
					moveSteps.add(current);
					break;

				}
			}
		}
	}

	/**
	 * Method to call for pieces that move oblique directions.
	 * 
	 * @param r
	 * @param c
	 * @param loc1
	 * @param loc2
	 * @param piece
	 * @param config
	 * @param moveSteps
	 * @param x
	 * @param y
	 */
	private void setMoveoOblique(int r, int c, int loc1, int loc2,
			String piece, Panel config, ArrayList<Panel> moveSteps, boolean x,
			boolean y) {
		int R = r;
		int C = c;
		while ((R != loc1) && (C != loc2)) {
			Panel current = config.getClone();
			if ((!(current.board.get(R).get(C).equals("."))) && (R != r)
					&& (C != c)) {
				current.setBoard(R, C, piece);
				current.setBoard(r, c, ".");
				moveSteps.add(current);
				break;

			}
			if (x) {
				R--;
				C--;
			}
			if (y) {
				R++;
				C++;
			}
			if (!x && !y) {
				R--;
				C++;
			}
			if (x && y) {
				R++;
				C--;
			}
		}
	}

	/**
	 * Method to call for Knight, King to move.
	 * 
	 * @param r
	 * @param c
	 * @param loc1
	 * @param loc2
	 * @param piece
	 * @param config
	 * @param moveSteps
	 */
	private void setMoveK(int r, int c, int loc1, int loc2, String piece,
			Panel config, ArrayList<Panel> moveSteps) {
		if (!(config.board.get(r - loc1).get(c - loc2).equals("."))) {
			Panel current = config.getClone();
			current.setBoard(r - loc1, c - loc2, piece);
			current.setBoard(r, c, ".");
			moveSteps.add(current);
		}
	}

	/**
	 * Method that getting the neighbors of the each possible movement of pieces
	 * and called by the solver.
	 * 
	 * @param config
	 * @return neighbors
	 * @override
	 */
	public ArrayList<Panel> getNeighbors(Panel config) {
		ArrayList<Panel> neighbors = new ArrayList<Panel>();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				ArrayList<Panel> moveSteps = new ArrayList<Panel>();
				switch (config.board.get(i).get(j)) {
				case "Q":
					setMoveStraight(i, j, "Q", config, moveSteps, true);
					setMoveStraight(i, j, "Q", config, moveSteps, false);
					setMoveoOblique(i, j, -1, -1, "Q", config, moveSteps, true,
							false);
					setMoveoOblique(i, j, ROWS, COLUMNS, "Q", config,
							moveSteps, false, true);
					setMoveoOblique(i, j, -1, COLUMNS, "Q", config, moveSteps,
							false, false);
					setMoveoOblique(i, j, ROWS, -1, "Q", config, moveSteps,
							true, true);
					neighbors.addAll(moveSteps);
					break;
				case "K":
					if (!(i - 1 <= -1)) {
						setMoveK(i, j, 1, 0, "K", config, moveSteps);

						if (!(j - 1 <= -1)) {
							setMoveK(i, j, 1, 1, "K", config, moveSteps);
						}

						if (!(j + 1 >= COLUMNS)) {
							setMoveK(i, j, 1, -1, "K", config, moveSteps);
						}
					}

					if (!(i + 1 >= ROWS)) {
						setMoveK(i, j, -1, 0, "K", config, moveSteps);
						if (!(j - 1 <= -1)) {
							setMoveK(i, j, -1, 1, "K", config, moveSteps);
						}
						if (!(j + 1 >= COLUMNS)) {
							setMoveK(i, j, -1, -1, "K", config, moveSteps);
						}
					}
					if (!(j - 1 <= -1)) {
						setMoveK(i, j, 0, 1, "K", config, moveSteps);
					}
					if (!(j + 1 >= COLUMNS)) {
						setMoveK(i, j, 0, -1, "K", config, moveSteps);
					}
					neighbors.addAll(moveSteps);
					break;
				case "N":
					if (!(i - 2 <= -1)) {
						if (!(j - 1 <= -1)) {
							setMoveK(i, j, 2, 1, "N", config, moveSteps);
						}
						if (!(j + 1 >= COLUMNS)) {
							setMoveK(i, j, 2, -1, "N", config, moveSteps);
						}
					}
					if (!(i + 2 >= ROWS)) {
						if (!(j - 1 <= -1)) {
							setMoveK(i, j, -2, 1, "N", config, moveSteps);
						}
						if (!(j + 1 >= COLUMNS)) {
							setMoveK(i, j, -2, -1, "N", config, moveSteps);
						}
					}

					if (!(j + 2 >= COLUMNS)) {
						if (!(i - 1 <= -1)) {
							setMoveK(i, j, 1, -2, "N", config, moveSteps);
						}
						if (!(i + 1 >= ROWS)) {
							setMoveK(i, j, -1, -2, "N", config, moveSteps);
						}
					}
					if (!(j - 2 <= -1)) {
						if (!(i - 1 <= -1)) {
							setMoveK(i, j, 1, 2, "N", config, moveSteps);
						}
						if (!(i + 1 >= ROWS)) {
							setMoveK(i, j, -1, 2, "N", config, moveSteps);
						}
					}
					neighbors.addAll(moveSteps);
					break;
				case "B":
					setMoveoOblique(i, j, -1, -1, "B", config, moveSteps, true,
							false);
					setMoveoOblique(i, j, ROWS, COLUMNS, "B", config,
							moveSteps, false, true);
					setMoveoOblique(i, j, -1, COLUMNS, "B", config, moveSteps,
							false, false);
					setMoveoOblique(i, j, ROWS, -1, "B", config, moveSteps,
							true, true);
					neighbors.addAll(moveSteps);
					break;
				case "R":
					setMoveStraight(i, j, "R", config, moveSteps, true);
					setMoveStraight(i, j, "R", config, moveSteps, false);
					neighbors.addAll(moveSteps);
					break;
				case "P":
					if (!(i - 1 <= -1)) {
						if (!(j - 1 <= -1)) {
							setMoveK(i, j, 1, 1, "P", config, moveSteps);
						}
						if (!(j >= COLUMNS - 1)) {
							setMoveK(i, j, 1, -1, "P", config, moveSteps);
						}
					}
					neighbors.addAll(moveSteps);
					break;
				default:
					break;
				}
			}
		}
		return neighbors;
	}

	/**
	 * Printing the solution board.
	 * 
	 * @param board
	 * 
	 */
	public static void printBoard(Panel solution) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				System.out.print(solution.board.get(i).get(j));
			}
			System.out.println("");
		}
		System.out.println();
	}

	/**
	 * main method that runs the program.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		ArrayList<ArrayList<String>> startGame = new ArrayList<ArrayList<String>>();
		BufferedReader reader = null;
		int row = 0;
		int col = 0;
		int h = 0;
		if (args.length != 1) {
			System.out.println("% usage: java Chess input-file");
		}
		try {
			FileReader file = new FileReader(args[0]);
			reader = new BufferedReader(file);
			String line;
			while ((line = reader.readLine()) != null) {
				String[] pieces = line.split(" ");
				if (h != 0) {
					ArrayList<String> current = new ArrayList<String>();
					for (String str : pieces) {
						current.add(str);
					}
					startGame.add(current);
				} else {
					row = Integer.parseInt(pieces[0]);
					col = Integer.parseInt(pieces[1]);
					h++;
				}
			}
			Chess chess = new Chess(startGame, row, col);
			ArrayList<Panel> solution = chess.solverBFS(chess);
			if (solution != null) {
				for (int i = 0; i < solution.size(); i++) {
					Chess.printBoard(solution.get(i));
				}
			} else {
				System.out.println("No solution");
			}
		} catch (NullPointerException e) {
			System.out.println("Error");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("{input-file} not found.");
		} catch (IOException e) {
			System.out.println("reading error");
		}
	}

}

/**
 * Class of Board object of the Chess.
 *
 */
class Panel {

	ArrayList<ArrayList<String>> board;

	/**
	 * Constructor that being created when needed board.
	 * 
	 * @param board
	 */
	public Panel(ArrayList<ArrayList<String>> board) {
		this.board = board;
	}

	/**
	 * Method that being called to cloning the board.
	 * 
	 * @return board
	 */
	public Panel getClone() {
		ArrayList<ArrayList<String>> current = new ArrayList<ArrayList<String>>();
		Panel copy;
		for (int i = 0; i < Chess.ROWS; i++) {
			ArrayList<String> row = new ArrayList<String>();
			for (int j = 0; j < Chess.COLUMNS; j++) {
				String piece = new String(board.get(i).get(j));
				row.add(piece);
			}
			current.add(row);
		}
		copy = new Panel(current);
		return copy;
	}

	/**
	 * Method that reset the board by new location of pieces.
	 * 
	 * @param row
	 * @param col
	 * @param piece
	 */
	public void setBoard(int row, int col, String piece) {
		board.get(row).set(col, piece);
	}
}
