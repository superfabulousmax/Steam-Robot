package com.mygdx.SteamRobot.PathFinding;

import com.mygdx.SteamRobot.Entities.GameEntity;

/**
 * Pathfinding interface that allows the user 
 * to implement a path finder from the start to the
 * goal position
 * @author Sinead
 *
 */
public interface PathFinder {
	/**
	 * 
	 * @param entity The entity to take the path
	 * @param startX The x position of the start of the path
	 * @param startY The y position of the start of the path
	 * @param endX The x position of the end of the path
	 * @param endY The y position of the end of the path
	 * @return the path from the start x and y  to the end x and y
	 */
	public Path getPath( GameEntity entity, int startX, int startY, int endX, int endY);
}
