package com.mygdx.SteamRobot.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.SteamRobot.GameState;
import com.mygdx.SteamRobot.MyGdxSteamRobot;
import com.mygdx.SteamRobot.TileBackground;
import com.mygdx.SteamRobot.PathFinding.MapWriter;
import com.mygdx.SteamRobot.PathFinding.TileMap;
/**
 * GUI screen to make a map to play 
 * press left click to place obstacle
 * press right click to remove obstacle
 * enter to save map to map1.txt
 * @author Sinead
 *
 */
public class MapMakerScreen  implements Screen{
	private MyGdxSteamRobot game;
	private ShapeRenderer shape;
	private TileMap map;
	private TileBackground background;


	public MapMakerScreen(MyGdxSteamRobot game) {

		this.game = game;
		shape = new ShapeRenderer();
		map = new TileMap(GameState.screenWidth / 50, GameState.screenHeight / 50);
		background = new TileBackground(50f, 50f);

	}


	@Override
	public void show() {


	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);//set screen to white
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//clear colour buffer

		background.render();
		input();
		background.render(map);
	}

	public void input() {

		int x  = Gdx.input.getX();
		int y = GameState.screenHeight - Gdx.input.getY();
		int row =  y/50;
		int col = x/50;
		if(Gdx.input.isKeyJustPressed(Keys.ENTER))
		{
			MapWriter.writeMapToFile("map1.txt", map);
		}
		if( checkInBounds(row, col)){ 
			if(Gdx.input.isButtonPressed(Buttons.LEFT))
			{



				if(map.getBitmapOfMap()[row][col] == 0)
					map.setObstacle(row, col);


			}
			else if (Gdx.input.isButtonPressed(Buttons.RIGHT))
			{
				if(map.getBitmapOfMap()[row][col] == 1)
					map.removeObstacle(row, col);
			}
		}

	}
	public boolean checkInBounds(int rows, int cols) {

		if(rows < 0 | cols < 0)
			return false;
		if(rows >= map.getHeightInTiles()| cols >= map.getWidthInTiles())
			return false;
		return true;
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
