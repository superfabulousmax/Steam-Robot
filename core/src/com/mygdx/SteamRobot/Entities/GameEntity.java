package com.mygdx.SteamRobot.Entities;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * An abstract game entity class
 * with x and y positions of game entity 
 * and render methods 
 * as well as sprite lists of entities
 * 
 * 
 * @author Sinead Urisohn
 * @version 8/15/2015
 */
public abstract class GameEntity {
	//fields

	private Vector2 position;

	public abstract Polygon getBound();
	
	/**
	 * All sub classes implement this
	 * with their sprites
	 * @return
	 */
	public abstract List<Sprite> getSprite();

	//constructor
	public GameEntity(float x, float y) {

		position = new Vector2();
		position.add(x,y);
		
	}

	//methods
	public abstract void render(float delta);
	public abstract void update(float delta);

	//getters and setters

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}


}
