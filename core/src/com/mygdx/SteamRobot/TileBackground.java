package com.mygdx.SteamRobot;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.SteamRobot.PathFinding.TileMap;
import com.mygdx.SteamRobot.Screens.PlayScreen;

/**
 * Class that takes a texture/shape and tiles the screens
 * background with it
 * @author Sinead
 *
 */
public class TileBackground {

	Texture tile;
	boolean useGrid;
	ShapeRenderer shape;
	float width;
	float height;
	SpriteBatch sb;

	public TileBackground(Texture tile)
	{
		this.tile = tile;
		width = tile.getWidth();
		height = tile.getHeight();
		sb = PlayScreen.batch;
		useGrid = false;
	}

	public TileBackground(float width, float height)
	{
		this.width = width;
		this.height = height;
		sb = PlayScreen.batch;
		this.useGrid = true;
		shape = new ShapeRenderer();
		

	}

	public void render()

	{
		if(useGrid) {

			shape.begin(ShapeType.Line);
			shape.setColor(Color.BLACK);

			for( int i = 0 ; i < GameState.screenWidth / width + 1 ; i++)
			{
				for(int j = (int) (GameState.screenHeight/ height); j >= 0 ; j--)
				{
					shape.rect(i*width, j*height, width, height);
				}
			}

			shape.end();


		}

		else {
			sb.begin();

			for( int i = 0 ; i < GameState.screenWidth / width + 1 ; i++)
			{
				for(int j = (int) (GameState.screenHeight/ height); j >= 0 ; j--)
				{
					sb.draw(tile, i*width, j*height,  width, height);
				}
			}



			sb.end();
		}
	}

	public void render(TileMap map) {

		shape.begin(ShapeType.Filled);
		shape.setColor(Color.BLUE);
		for(int j = map.getHeightInTiles() - 1 ; j >= 0; j--)
		{
			for( int i = 0 ; i < map.getWidthInTiles(); i++)
			{

				if(map.getBitmapOfMap()[j][i]==1)
					shape.rect(i*width, j*height, width, height);
			}
		}
		shape.end();


	}




}
