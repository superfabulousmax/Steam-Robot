package com.mygdx.SteamRobot.PathFinding;


/**
 * Stores the internal representation
 * of the tile map
 * @author Sinead
 *
 */
public class TileMap {

	//width and height of tiles
	public static final int tileWidth = 50;
	public static final int tileHeight= 50;
	//This will be used by node class to know how many walkable nodes to create
	private int widthInTiles;//total number of tiles to cover width of map
	private int heightInTiles;//total number of tiles to cover height of map

	private int [][] bitmapOfMap; //use this to make maps

	private Tile [][] tilesInMap;


	public TileMap( int widthInTiles, int heightInTiles) {

		//this.tileSize = tileSize;
		this.widthInTiles = widthInTiles;
		this.heightInTiles = heightInTiles;
		bitmapOfMap = new int [heightInTiles][widthInTiles];
		tilesInMap = new Tile [heightInTiles][widthInTiles];
		makeClearMap();
	}



	/**
	 * makes a clear bitmap of zeroes
	 * meaning that there are no obstacles on the map
	 */
	public void makeClearMap()
	{
		for(int rows = 0; rows < heightInTiles; rows++)
		{
			for(int cols = 0; cols < widthInTiles; cols++)
			{
				bitmapOfMap[rows][cols] = 0;
				tilesInMap[rows][cols] = new Tile(cols, rows, true);
			}
		}
	}

	/**
	 * 
	 * @param row The row in the tile map to set the obstacle
	 * @param column The column in the map to set the obstacle
	 */
	public void setObstacle(int row, int column)
	{

		bitmapOfMap[row][column] = 1;
		tilesInMap[row][column].setMovable(false);
	}

	/**
	 * 
	 * @param row The row in the tile map to remove the obstacle
	 * @param column The column in the map to remove the obstacle
	 */
	public void removeObstacle(int row, int column)
	{

		bitmapOfMap[row][column] = 0;
		tilesInMap[row][column].setMovable(true);
	}
	/**
	 * when reading in a bitmap to make make
	 * need to set tiles to corresponding 
	 * obstacles. This does that.
	 */
	public void changeTilesToMap()
	{
		for(int rows = 0; rows < heightInTiles; rows++)
		{
			for(int cols = 0; cols < widthInTiles; cols++)
			{
				
				if(bitmapOfMap[rows][cols] ==1)
					tilesInMap[rows][cols].setMovable(false);
			}
		}
	}
	/**
	 * 
	 * @param x The x position of the tile that the path visited
	 * @param y The y position of the tile that the path visitied.
	 */
	public void pathFinderVisited(int x, int y) {


	}
	/**
	 * 
	 * @param y The y position to convert
	 * @return the correspinding grid row position
	 */
	public static int convertYPositionToRow(float y) {
		return (int)(y / tileHeight);
	}
	/**
	 * 
	 * @param x The x position to convert
	 * @return the correspinding grid column position
	 */
	public static int convertXPositionToCol(float x) {
		return (int)(x / tileWidth);
	}
	/**
	 * gets cost to move through the given location
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return 1 cost to move through a tile
	 */
	public float getCost(int startX, int startY, int endX, int endY) {
		return 1.0f;
	}


	public int getWidthInTiles() {
		return widthInTiles;
	}



	public void setWidthInTiles(int widthInTiles) {
		this.widthInTiles = widthInTiles;
	}



	public int getHeightInTiles() {
		return heightInTiles;
	}



	public void setHeightInTiles(int heightInTiles) {
		this.heightInTiles = heightInTiles;
	}


	public Tile[][] getTilesInMap() {
		return tilesInMap;
	}



	public void setTilesInMap(Tile[][] tilesInMap) {
		this.tilesInMap = tilesInMap;
	}	

	public int[][] getBitmapOfMap() {
		return bitmapOfMap;
	}



	public void setBitmapOfMap(int[][] bitmapOfMap) {
		this.bitmapOfMap = bitmapOfMap;
	}


	/**
	 * Contains information
	 * about a tile in the tile map
	 * @author Sinead
	 *
	 */

	public class Tile{

		public int xPos;
		public int yPos;
		public boolean movable;

		public Tile(int x, int y, boolean movable) {
			this.xPos = x;
			this.yPos = y;
			this.movable = movable;

		}

		public int getxPos() {
			return xPos;
		}

		public void setxPos(int xPos) {
			this.xPos = xPos;
		}

		public int getyPos() {
			return yPos;
		}

		public void setyPos(int yPos) {
			this.yPos = yPos;
		}

		public boolean isMovable() {
			return movable;
		}

		public void setMovable(boolean movable) {
			this.movable = movable;
		}

		public float getCost(int startX, int startY, int endX, int endY) {
			return 0.0f;
		}


	}


}
