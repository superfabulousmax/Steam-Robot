package com.mygdx.SteamRobot.PathFinding;

/**
 * Node in the search graph
 * @author Sinead
 *
 */
public class Node implements Comparable<Node> {

	private float cost;
	private int x;
	private int y;
	private float heuristic;
	private int depth;// depth of node
	

	private Node parent; //parent of this node
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		cost = Float.MAX_VALUE;
//		
	}

	//TODO set parent method
	public void setParent(Node parent) {
//		depth = parent.depth + 1;
		this.parent = parent;
		//return depth;
		
	}
	

	/**
	 * Compare this node to another node
	 * compare by f cost
	 */
	@Override
	public int compareTo(Node other) {
		float f = cost + heuristic;
		float otherF = other.cost + other.heuristic;
		if(f < otherF) return -1;
		else if(f > otherF) return 1;
		else return 0;
	}
	
	//
	
	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	public float getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(float heuristic) {
		this.heuristic = heuristic;
	}

	public Node getParent() {
		return parent;
	}
}
