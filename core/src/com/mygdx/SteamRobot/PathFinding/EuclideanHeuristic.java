package com.mygdx.SteamRobot.PathFinding;
/**
 * euclidean metric to use
 * for the heuristic implementation
 * @author Sinead
 *
 */
public class EuclideanHeuristic implements AStarHeuristic{

	@Override
	public float getCost(int startX, int startY, int goalX,
			int goalY) {
		float dx =	goalX - startX;
		float dy = goalY - startY;
		
		float result = (float) (Math.sqrt( (dx*dx) +(dy*dy)));
		
		return result;
	}

}
