package com.mygdx.SteamRobot.CollisionDetection;


import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.mygdx.SteamRobot.Entities.GameEntity;
import com.mygdx.SteamRobot.Entities.GearEntity;
import com.mygdx.SteamRobot.Entities.RobotoEntity;


/**
 * Using polygon class to check
 * whether two sprites are colliding
 * @author Sinead
 *
 */
public class Collision {
	//fields
	private Polygon polyBound;
	//private Shape test;

	public Collision(GameEntity entity){

		polyBound = entity.getBound();
	}
	
	public Collision(RobotoEntity robot){

		polyBound = robot.getArmBound();
	}
	
	public Collision(GearEntity gear){

		polyBound = gear.getBound();
	}

	/**
	 * 
	 * @param other another sprite object
	 * @return whether one sprite bound is intersecting with another sprite bounding polygon
	 */
	public boolean isColliding(GameEntity otherEntity) {
		Polygon otherBound = otherEntity.getBound();
		if(Intersector.overlapConvexPolygons(otherBound, polyBound))return true;
		return false;
	}
	
	/**
	 * @return whether a sprite is colliding with robot arm
	 */
	
	public boolean isCollidingWithRobotArm(GameEntity other)
	{
		Polygon otherBound = other.getBound();
		if(Intersector.overlapConvexPolygons(otherBound, polyBound))return true;
		return false;
	}
	
	/**
	 * @return whether a gear is colliding with another gear
	 */
	public boolean isGearCollidingWithGear(GearEntity other)
	{
		Polygon otherBound = other.getBound();
		if(Intersector.overlapConvexPolygons(otherBound, polyBound))return true;
		return false;
	}

}



