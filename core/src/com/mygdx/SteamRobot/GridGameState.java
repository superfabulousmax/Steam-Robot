package com.mygdx.SteamRobot;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.SteamRobot.Entities.GameEntity;
import com.mygdx.SteamRobot.Entities.PaperEntity;
import com.mygdx.SteamRobot.PathFinding.AStar;
import com.mygdx.SteamRobot.PathFinding.EuclideanHeuristic;
import com.mygdx.SteamRobot.PathFinding.MapReader;
import com.mygdx.SteamRobot.PathFinding.Path;
import com.mygdx.SteamRobot.PathFinding.TileMap;
import com.mygdx.SteamRobot.PathFinding.Path.Step;

/**
 * Contains game world for the path finding grid game
 * 
 * @author Sinead
 *
 */
public class GridGameState {
	//what is in game world i.e. state of game
	private List<GameEntity> entities;
	//public RobotoEntity playerEntity;
	private TileMap map;
	private AStar pathFinder;
	private TileBackground background;

	//TODO test code
	private ShapeRenderer shape;
	Path path;
	PaperEntity entity;
	int goalX, goalY;
	private int tileWidth;
	private int tileHeight;
	//---
	public GridGameState() {

		//initialise game objects
		this.entities = new ArrayList<GameEntity>();
		tileWidth = TileMap.tileWidth;
		tileHeight = TileMap.tileHeight;
		map = new TileMap(GameState.screenWidth / tileWidth, GameState.screenHeight / tileHeight);
		map.setBitmapOfMap(MapReader.readFileToMap("map1.txt"));
		map.changeTilesToMap();
		background = new TileBackground(tileWidth, tileHeight);

		goalX = 26;
		goalY = 12;
		pathFinder = new AStar(map, new EuclideanHeuristic());

		path = pathFinder.getPath(
				null, 6,  9 +1, goalX, goalY);

		entity = new PaperEntity(6*tileWidth, 9*tileHeight+1, path);
		//entity.setPath(path);

		this.entities.add(entity);

		shape = new ShapeRenderer();

	}

	/** 
	 * Update game state i.e.
	 * All the entities 
	 */
	public void update(float delta)
	{
		for(GameEntity entity: entities)
		{
			entity.update(delta);
		}
		moveGoal();
	}


	/**
	 * Draw current game state
	 */
	public void render(float delta)
	{
		for(GameEntity entity: entities)
		{
			entity.render(delta);
		}

		background.render();

		background.render(map);

		shape.begin(ShapeType.Filled);

		shape.setColor(Color.PURPLE);

		shape.rect(goalX *tileWidth,goalY*tileHeight +1 , tileWidth-1, tileHeight -1);
		shape.setColor(Color.YELLOW);

		if(moveGoal()) {
			path = pathFinder.getPath(null, (int)(entity.getPosition().x/tileWidth), (int)(entity.getPosition().y/tileHeight), goalX, goalY);
			entity.setPath(path);
		}
		
		if(path!=null) {
			for(Step stepToTake: path.getStepsInThePath()) {
				shape.rect(tileWidth * stepToTake.getX() + ((1.0f/4)*tileWidth) , (tileHeight) * stepToTake.getY()+1 +((1.0f/4)*tileHeight), (tileWidth-1)/2, (tileHeight -1)/2);
			}
		}
		shape.end();
	}
	public boolean moveGoal() {
		int x  = Gdx.input.getX();
		int y = GameState.screenHeight - Gdx.input.getY();
		int row =  TileMap.convertYPositionToRow(y);
		int col =  TileMap.convertXPositionToCol(x);
		if(checkInBounds(row, col)) {
			if(Gdx.input.isButtonPressed(Buttons.LEFT)) {

				goalX = col;
				goalY = row;
				return true;
			}
		}
		return false;
	}
	public boolean checkInBounds(int rows, int cols) {

		if(rows < 0 | cols < 0)
			return false;
		if(rows >= map.getHeightInTiles()| cols >= map.getWidthInTiles())
			return false;
		return true;
	}




}
