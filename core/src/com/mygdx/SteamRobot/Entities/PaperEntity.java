package com.mygdx.SteamRobot.Entities;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.SteamRobot.Behaviours.EnemyBehaviours;
import com.mygdx.SteamRobot.Behaviours.Seek;
import com.mygdx.SteamRobot.Behaviours.WalkPath;
import com.mygdx.SteamRobot.PathFinding.AStar;
import com.mygdx.SteamRobot.PathFinding.EuclideanHeuristic;
import com.mygdx.SteamRobot.PathFinding.Path;
import com.mygdx.SteamRobot.PathFinding.TileMap;
/**
 * Paper entity with pathfinding and shooting behaviour
 * 
 * @author Sinead
 *
 */
public class PaperEntity extends EnemyEntity{

	private Sprite paperSprite;
	private ShapeRenderer shape;
	private GameEntity target;


	//give behaviour to entity
	private WalkPath walkPath;
	//TODO test code 
	private int tileWidth;
	private int tileHeight;
	public PaperEntity(float x, float y, Path path) {
		super(x, y);

		paperSprite = new Sprite(new Texture(Gdx.files.internal("enemies/paper.png")));
		shape = new ShapeRenderer();
		walkPath = new WalkPath(path);
		
		tileWidth = TileMap.tileWidth;
		tileHeight = TileMap.tileHeight;
	}

	@Override
	public Polygon getBound() {

		return null;
	}

	@Override
	public List<Sprite> getSprite() {

		return null;
	}

	@Override
	public void render(float delta) {
		shape.begin(ShapeType.Filled);

		shape.setColor(Color.ORANGE);
		shape.rect(getPosition().x - 1.0f/2*tileWidth, getPosition().y+1 - 1.0f/2*tileHeight, tileWidth-1, tileHeight -1);

		shape.end();


	}
	

	public void setPath(Path path) {
		((WalkPath) walkPath).setPath(path);
	}

	@Override
	public void update(float delta) {
		walkPath.applyBehaviour(delta, this);
		getVelocity().clamp(0, 100);
		//add the velocity back onto the position
		//delta x = velocity * time
		Vector2 x = new Vector2(getVelocity());
		x.scl(delta);
		setPosition(getPosition().add(x));



	}

}
