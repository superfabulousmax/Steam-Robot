package com.mygdx.SteamRobot.PathFinding;
/**
 * The type of heuristic to use
 * which determines search order of
 * the a* star algorithm
 * This interface describe the cost of a start tile
 * to a target tile
 * @author Sinead
 *
 */
public interface AStarHeuristic {
	
	public float getCost( int startX, int startY, int goalX, int goalY);
}
