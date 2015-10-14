package com.mygdx.SteamRobot.PathFinding;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.mygdx.SteamRobot.Entities.GameEntity;
/**
 * A* algorithm implementation to
 * determine a path to take for a given heuristic
 * @author Sinead
 *
 */
public class AStar implements PathFinder{
	// Lists of nodes to consider during path finding
	private ArrayList<Node> closed;
	private PriorityQueue<Node> open;
	//map to search
	private TileMap map;
	//nodes over the map
	private Node[][]nodes;
	//heuristic to govern search
	private AStarHeuristic heuristic;

	public AStar(TileMap map, AStarHeuristic heuristic) {
		closed = new ArrayList<Node>();
		open = new PriorityQueue<Node>();
		this.map = map;
		this.heuristic = heuristic;
		nodes = new Node[map.getHeightInTiles()][map.getWidthInTiles()];
		for(int y = 0; y < map.getHeightInTiles(); y++) {

			for(int x = 0; x < map.getWidthInTiles(); x++) {
				nodes[y][x] = new Node(x, y);
			}
		}
	}


	@Override
	public Path getPath(GameEntity entity, int startX, int startY, int endX,
			int endY) {

		//check if tile blocked
		//if it is path there invalid
		if(!map.getTilesInMap()[endY][endX].isMovable()) return null;

		//initialise lists of search
		//add starting node to list
		nodes[startY][startX].setCost(0);
		nodes[startY][startX].setDepth(0);
		open.clear();
		open.add(nodes[startY][startX]);
		closed.clear();

		nodes[startY][startX].setParent(null);

		while(open.size()> 0) {

			Node current = open.poll();
			if(current == nodes[endY][endX]) break;

			open.remove(current);
			closed.add(current);

			//get neighbours of current node

			for( int x = -1; x < 2; x++) {
				for(int y = -1; y < 2; y++) {

					//skip current tile which is  0,0 
					if((x == 0) && (y==0)) continue;
					// get neighbour position 

					int neighbourX = x + current.getX();
					int neighbourY = y + current.getY();

					if(isValidPosition(startX, startY, neighbourX,neighbourY)) {
						// calculate the cost to get to the neighbour
						float nextStepCost = current.getCost() + 
								getMovementCost( current.getX(), current.getY(), neighbourX, neighbourY);

						Node neighbour = nodes [neighbourY][neighbourX];
						map.pathFinderVisited(neighbourX, neighbourY);
						//if cost for this node better than cost for current node
						//then it needs to be reevaluated

						if(nextStepCost < neighbour.getCost()) {
							if(open.contains(neighbour)) {
								open.remove(neighbour);
							}
							if(closed.contains(neighbour)) {
								closed.remove(neighbour);
							}

						}

						//if node has not been looked at yet

						if(!(open.contains(neighbour)) && !(closed.contains(neighbour))) {
							neighbour.setCost(nextStepCost);
							neighbour.setHeuristic( getHeuristic(neighbourX, neighbourY, endX, endY));
							neighbour.setParent(current);
							open.add(neighbour);
						}

					}

				}
			}



		}//end while


		//if no path return null
		if(nodes[endY][endX].getParent() == null) return null;
		//else found a path
		//find way from target to starting node
		//in order to get path to follow 
		
		Path path = new Path();
		Node target = nodes[endY][endX];
		while(target != nodes[startY][startX]) {
			
			path.addStepToTheStart(target.getX(), target.getY());
			target = target.getParent();
		}
		path.addStepToTheStart(startX, startY);
		
		return path;
	}
	
	/**
	 * check if valid position to move to
	 * @param startX
	 * @param startY
	 * @param xToCheck
	 * @param yToCheck
	 * @return true  if location valid
	 */
	public boolean isValidPosition(int startX, int startY, int xToCheck, int yToCheck) {
		boolean invalid = (xToCheck < 0) || (yToCheck < 0) 
				|| (xToCheck >= map.getWidthInTiles()) || (yToCheck >= map.getHeightInTiles());

		if((!invalid) && ((startX != xToCheck) || (startY != yToCheck)))
			invalid = !(map.getTilesInMap()[yToCheck][xToCheck].isMovable());

		return !invalid;
	}
	/**
	 * get cost to move through location given
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return the cost to move through given tile
	 */
	public float getMovementCost(int startX, int startY, int endX, int endY) {
		return map.getCost(startX, startY, endX, endY);
	}
	/**
	 * get heuristic cost for given start and end position
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return
	 */
	public float getHeuristic(int startX, int startY, int endX, int endY) {
		return heuristic.getCost(startX, startY, endX, endY);
	}

}
