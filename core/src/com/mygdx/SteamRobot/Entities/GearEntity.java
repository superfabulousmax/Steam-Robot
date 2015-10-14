package com.mygdx.SteamRobot.Entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.SteamRobot.Behaviours.EnemyBehaviours;
import com.mygdx.SteamRobot.Behaviours.Seek;
import com.mygdx.SteamRobot.Behaviours.Shy;
import com.mygdx.SteamRobot.Behaviours.Wander;
import com.mygdx.SteamRobot.CollisionDetection.Collision;
import com.mygdx.SteamRobot.Screens.PlayScreen;

/**
 * Enemy Gear entity
 * @author Sinead
 * @version 9/18/2015
 */
public class GearEntity extends EnemyEntity{
	//fields related to Gear enemy
	private Sprite gearSprite;
	private ShapeRenderer shape;
	private float radiusOfGear;

	//give gear entity seek behaviour
	private GameEntity target;
	private EnemyBehaviours seek;
	private EnemyBehaviours wander;
	private EnemyBehaviours shy;
	//degrees to rotate gear by
	private float totalRotation;

	private Circle boundingCircle;
	private Polygon boundingPoly;

	//bounding shape for collision




	//has own set of behaviours
	public GearEntity(float x, float y) {
		super(x, y);

		gearSprite = new Sprite(new Texture(Gdx.files.internal("enemies/gear.png")));
		seek = new Seek();
		wander = new Wander();
		shy = new Shy();
		totalRotation = 0f;
		gearSprite.setOriginCenter();
		radiusOfGear = gearSprite.getWidth()/2;
		boundingCircle = new Circle(0, 0, radiusOfGear);// 0 0 relatice to sprites position
		boundingPoly = circleToPolygon(boundingCircle, 20);

		shape = new ShapeRenderer();
		
	}

	@Override
	public void render(float delta) {

		//draw gear on main batch
		PlayScreen.batch.begin();


		gearSprite.setCenterX(getPosition().x);
		gearSprite.setCenterY(getPosition().y);
		gearSprite.draw(PlayScreen.batch);

		PlayScreen.batch.end();
		//draw bounding shape
//		shape.begin(ShapeType.Line);
//		shape.setColor(Color.BLACK);
//		shape.polygon(boundingPoly.getTransformedVertices());
//		shape.end();



	}



	@Override
	public void update(float delta) {
		seek.applyBehaviour(delta, this);
		//shy.applyBehaviour(delta, this);
		getVelocity().clamp(0, 100);

		//add the velocity back onto the position
		//delta x = velocity * time
		Vector2 x = new Vector2(getVelocity());
		x.scl(delta);
		setPosition(getPosition().add(x));
		boundingCircle.setPosition(getPosition());	
		boundingPoly.setPosition(getPosition().x, getPosition().y);
		totalRotation +=200f*delta;
		gearSprite.setRotation(totalRotation);
		boundingPoly.setRotation(totalRotation);

	}

	@Override
	public void setTarget(GameEntity target) {
		super.setTarget(target);

		//set the target of seek
		((Seek) seek).setTarget(target);
	}
	
	public Polygon circleToPolygon(Circle circle, int n) {
		float x = circle.x;
		float y = circle.y;
		float radius = circle.radius;
		float[] points = new float[n*2];

		float  currentAngle = 0;
		for (int i = 0; i < n; i++) {

			points[i*2] = (float) (x + radius * Math.cos( MathUtils.degreesToRadians*currentAngle));
			points[i*2 + 1] = (float) (y + radius * Math.sin(MathUtils.degreesToRadians* currentAngle));
			currentAngle += 360.0f / n;


		}
		return new Polygon(points);
	}

	@Override
	public List<Sprite> getSprite() {
		List<Sprite> list = new  ArrayList<Sprite>();
		list.add(gearSprite);
		return list;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}
	

	@Override
	public Polygon getBound() {
		
		return boundingPoly;
	}

	


	

}
