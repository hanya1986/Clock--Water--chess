/*
 * Clock.java
 * 
 * Version:
 *   $Id: Clock.java,v 1.1 2014/12/10 00:38:41 yc7816 Exp $
 *   
 * Revision:
 *   $Log: Clock.java,v $
 *   Revision 1.1  2014/12/10 00:38:41  yc7816
 *   Initial version
 *
 *   Revision 1.5  2014/11/25 04:50:36  yc7816
 *   Last correction made.
 *
 *   Revision 1.4  2014/11/25 02:58:07  yc7816
 *   Making corrections
 *
 *   Revision 1.3  2014/11/24 18:55:59  yc7816
 *   Corrected the Solver method, neighbor method in Clock has changed the param, and trying to write the neighbor method in Water.
 *
 *   Revision 1.2  2014/11/24 16:30:00  yc7816
 *   Solver for clock puzzle does work
 *
 *   Revision 1.1  2014/11/24 15:44:46  yc7816
 *   initial version
 *
 *
 */

import java.util.ArrayList;

/**
 * Clock class to provide configurations to the solver, read in the input from
 * argument line.
 * 
 * @Author's Login ID: yc7816
 * 
 * @Name: Yihao Cheng
 * 
 */

public class Clock extends Puzzle<Integer> {
	private static int start;
	private static int goal;
	private static int total;

	/**
	 * Get the starting config for this puzzle.
	 * 
	 * @Override return start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * Get the goal config for this puzzle.
	 * 
	 * @Override
	 */
	public boolean checkForGoal(Integer config) {
		if (Clock.goal == config) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * For an incoming config, generate and return all direct neighbors to this
	 * config.
	 * 
	 * @param config
	 * @return neighbors
	 * @Override
	 */
	public ArrayList<Integer> getNeighbors(Integer config) {
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		int temp1;
		if (config == 1) {
			neighbors.add(total);
		}
		if (config > 1) {
			temp1 = config - 1;
			neighbors.add(temp1);
		}
		if (config < total) {
			temp1 = config + 1;
			neighbors.add(temp1);
		}
		if (config == total) {
			temp1 = 1;
			neighbors.add(temp1);
		}

		return neighbors;
	}

	/**
	 * Main method that reads in the argument line and runs the solverBFS method
	 * in Solver class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			total = Integer.parseInt(args[0]);
			start = Integer.parseInt(args[1]);
			goal = Integer.parseInt(args[2]);
			if (start > total || goal > total) {
				System.out
						.println("The start and goal should smaller than hours");
				System.exit(0);
			}
			if (start <= 0 || goal <= 0 || total <= 0) {
				System.out
						.println("The input argument should not be less than 1");
				System.exit(0);
			}
			Puzzle<Integer> s1 = new Clock();
			ArrayList<Integer> path = s1.solverBFS(s1);
			for (int i = 0; i < path.size(); i++) {
				System.out.println("Step " + i + ": " + path.get(i));
			}

		} catch (NullPointerException ex) {
			System.out.println("Error");
		} catch (NumberFormatException e) {
			System.out
					.println("Wrong input at argument line, it should be \"Clock {hours} {start} {goal}\" like \" Clock 12 2 5\"");
		} catch (Exception ex) {
			System.out
					.println("There should be argument input after the \"Clock\" ");
		}
	}

}
