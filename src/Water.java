/*
 * Water.java
 * 
 * Version:
 *   $Id: Water.java,v 1.2 2014/12/10 15:00:23 yc7816 Exp $
 *   
 * Revision:
 *   $Log: Water.java,v $
 *   Revision 1.2  2014/12/10 15:00:23  yc7816
 *   simplifying the code as possible
 *
 *   Revision 1.1  2014/12/10 00:38:40  yc7816
 *   Initial version
 *
 *   Revision 1.8  2014/11/25 04:50:36  yc7816
 *   Last correction made.
 *
 *   Revision 1.7  2014/11/25 04:39:20  yc7816
 *   final version for submit(correcting)
 *
 *   Revision 1.6  2014/11/25 03:55:15  yc7816
 *   final version
 *
 *   Revision 1.5  2014/11/25 03:40:14  yc7816
 *   version for submit
 *
 *   Revision 1.4  2014/11/25 02:58:07  yc7816
 *   Making corrections
 *
 *   Revision 1.3  2014/11/24 18:55:59  yc7816
 *   Corrected the Solver method, neighbor method in Clock has changed the param, and trying to write the neighbor method in Water.
 *
 *   Revision 1.2  2014/11/24 16:29:59  yc7816
 *   Solver for clock puzzle does work
 *
 *   Revision 1.1  2014/11/24 15:44:45  yc7816
 *   initial version
 *
 *
 */
import java.util.ArrayList;

/**
 * Class that plays the water puzzle game, it extends method from Puzzle class
 * and using the solver algorithm to solve the puzzle.
 * 
 * @Author's Login ID: yc7816
 * 
 * @Name: Yihao Cheng
 * 
 */
public class Water extends Puzzle<ArrayList<Integer>> {
	ArrayList<Integer> jugs;
	private static int goal;

	/**
	 * Constructor that reads in argument and add it to the ArrayList.
	 * 
	 * @param args
	 */
	public Water(String[] args) {
		jugs = new ArrayList<Integer>();
		for (int i = 1; i < args.length; i++) {
			jugs.add(Integer.parseInt(args[i]));
			if (Integer.parseInt(args[i]) < 0) {
				System.out
						.println("The input should be all positive bigger or equal to 0");
				System.exit(0);
			}
		}
		goal = Integer.parseInt(args[0]);
	}

	/**
	 * method that set the starting jug into 0;
	 * 
	 * @return start
	 */
	@Override
	public ArrayList<Integer> getStart() {
		ArrayList<Integer> start = new ArrayList<Integer>();
		for (int i = 0; i < jugs.size(); i++) {
			start.add(0);
		}
		return start;
	}

	/**
	 * method that check if it is the goal.
	 * 
	 * @param config
	 * @return true or false
	 */
	@Override
	public boolean checkForGoal(ArrayList<Integer> config) {
		if (config.contains(Water.goal)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method that empty the jug or fulfill the jug or pour one jug into
	 * another.
	 * 
	 * @param config
	 * @return neighbors
	 */
	@Override
	public ArrayList<ArrayList<Integer>> getNeighbors(ArrayList<Integer> config) {
		ArrayList<ArrayList<Integer>> neighbors = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> currentHolds = new ArrayList<Integer>(config);
		int currentJugHold;
		for (int i = 0; i < config.size(); i++) {
			if (currentHolds.size() != config.size()) {
				currentHolds = restore(config);
			}
			currentJugHold = currentHolds.remove(i);
			if (currentJugHold != jugs.get(i)) {
				currentHolds.add(i, jugs.get(i));
				neighbors.add(currentHolds);
				currentHolds = restore(config);
				currentJugHold = currentHolds.remove(i);
			}
			if (currentJugHold != 0) {
				currentHolds.add(i, 0);
				neighbors.add(currentHolds);
				currentHolds = restore(config);
				currentJugHold = currentHolds.remove(i);
			}
		}
		for (int i = 0; i < jugs.size(); i++) {
			for (int j = 0; j < jugs.size(); j++) {
				ArrayList<Integer> current = new ArrayList<Integer>();
				current.addAll(config);
				if (i != j) {
					if (current.get(i) <= jugs.get(j) - current.get(j)) {
						current.set(j, current.get(j) + current.get(i));
						current.set(i, 0);
					} else {
						current.set(j, jugs.get(j));
						current.set(i,
								current.get(i) - jugs.get(j) - current.get(j));
					}
					neighbors.add(current);

				}
			}
		}

		return neighbors;
	}

	/**
	 * method that reset the ArrayList
	 * 
	 * @param config
	 * @return newConfig
	 */
	private ArrayList<Integer> restore(ArrayList<Integer> config) {
		ArrayList<Integer> newConfig = new ArrayList<Integer>();
		for (int i = 0; i < config.size(); i++) {
			newConfig.add(config.get(i));
		}
		return newConfig;
	}

	/**
	 * Main method that for testing the program and run it.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Puzzle<ArrayList<Integer>> s1 = new Water(args);
			ArrayList<ArrayList<Integer>> path = s1.solverBFS(s1);
			if (args.length >= 2) {
				if (path != null) {
					for (int i = 0; i < path.size(); i++) {
						System.out.print("Step " + i + ": ");
						for (int j = 0; j < path.get(i).size(); j++) {
							System.out.print(path.get(i).get(j));
							if (j + 1 != path.get(i).size()) {
								System.out.print(" ");
							}
						}
						System.out.print("\n");
					}
				} else {
					System.out.println("No solution.");
				}
			} else {
				System.out.println("Usage: java Water amount jug1 jug2 ...");
			}
		} catch (NullPointerException ex) {
			System.out.println("Error");
		} catch (NumberFormatException e) {
			System.out.println(" Usage: java Water amount jug1 jug2 ...");
		} catch (Exception ex) {
			System.out
					.println("There should be argument input after the \"Water\" ");
		}
	}
}
