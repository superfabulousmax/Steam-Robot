package com.mygdx.SteamRobot.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.SteamRobot.GameState;
import com.mygdx.SteamRobot.GridGameState;
import com.mygdx.SteamRobot.MyGdxSteamRobot;
import com.mygdx.SteamRobot.TileBackground;
import com.mygdx.SteamRobot.PathFinding.AStar;
import com.mygdx.SteamRobot.PathFinding.EuclideanHeuristic;
import com.mygdx.SteamRobot.PathFinding.MapReader;
import com.mygdx.SteamRobot.PathFinding.Path;
import com.mygdx.SteamRobot.PathFinding.Path.Step;
import com.mygdx.SteamRobot.PathFinding.TileMap;
/**
 * A screen to play the pathfinding level
 * @author Sinead
 *
 */
public class PathFindingLevelScreen implements Screen{
	private MyGdxSteamRobot game;
	private ShapeRenderer shape;
	//private AStar pathFinder;
	public static SpriteBatch batch;
	private GridGameState world;

	public PathFindingLevelScreen(MyGdxSteamRobot game) {

		this.game = game;
		shape = new ShapeRenderer();
		
		
		//pathFinder = new AStar(map, new EuclideanHeuristic());
	
		batch = new SpriteBatch();

		world = new GridGameState();
	}


	@Override
	public void show() {


	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);//set screen to white
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//clear colour buffer

		
		world.render(delta);
		update(delta);
		
		
	}
	
	public void update(float delta) {
		
		world.update(delta);
	}

	

	@Override
	public void resize(int width, int height) {


	}

	@Override
	public void pause() {


	}

	@Override
	public void resume() {


	}

	@Override
	public void hide() {


	}

	@Override
	public void dispose() {


	}

}
