/*
 * Puzzle.java
 * 
 * Version:
 *   $Id$
 *   
 * Revision:
 *   $Log$
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 ** Class that has the solver in it
 * 
 * @Author's Login ID: yc7816
 * 
 * @Name: Yihao Cheng
 */
public abstract class Puzzle<T> {

	/**
	 * method to get the start
	 * 
	 * @return T
	 */
	public T getStart() {
		return null;
	}

	/**
	 * method to check for the goal
	 * 
	 * @param config
	 * @return boolean
	 */

	public boolean checkForGoal(T config) {
		return false;
	}

	/**
	 * method to find the neighbors(the way each puzzle works)
	 * 
	 * @param config
	 * @return ArrayList<T>
	 */
	java.util.ArrayList<T> getNeighbors(T config) {
		return null;
	}

	/**
	 * The naive BFS solving method
	 * 
	 * @return current
	 */
	public ArrayList<T> solverBFS(Puzzle<T> s) {
		T current = null;
		boolean found = false;
		ArrayList<T> queue = new ArrayList<T>();
		Map<T, T> predecessors = new HashMap<T, T>();
		queue.add(s.getStart());
		predecessors.put(s.getStart(), s.getStart());
		while (!queue.isEmpty() && !found) {
			current = queue.remove(0);
			for (T y : s.getNeighbors(current)) {
				if (!predecessors.containsKey(y)) {
					predecessors.put(y, current);
				}
				if (s.checkForGoal(y)) {
					current = y;
					found = true;
					break;
				} else {
					queue.add(y);
				}
			}
		}

		if (found) {
			ArrayList<T> result = new ArrayList<T>();
			T solution = current;
			result.add(solution);
			while (!(solution.equals(s.getStart()))) {
				result.add(0, predecessors.get(solution));
				solution = predecessors.get(solution);
			}
			return result;
		} else {
			return null;
		}
	}
}