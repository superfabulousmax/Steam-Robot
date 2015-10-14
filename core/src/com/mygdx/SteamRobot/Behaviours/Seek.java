package com.mygdx.SteamRobot.Behaviours;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.SteamRobot.Entities.EnemyEntity;
import com.mygdx.SteamRobot.Entities.GameEntity;

/**
 * Enemy Gear entity's seek behaviour
 * Gear is not so clever and hovers near the Steam Roboto
 * 
 * @author Sinead
 * @version 9/18/2015
 */
public class Seek extends EnemyBehaviours{

	private GameEntity target;
	//private Vector2 positionOfTarget;
	

	private final float maximumSpeed = 100f;
	
	public Seek()
	{
	
		
	}
	

	@Override
	public void applyBehaviour(float delta, EnemyEntity seeker) {
		
		
		//Get vector between seeker and target
		Vector2 positionOfSeeker = new Vector2(seeker.getPosition());
		Vector2 positionOfTarget = new Vector2(target.getPosition());
		//need normalised vector between them
		
		Vector2 x = positionOfTarget.sub(positionOfSeeker);
		x.nor();
		x.scl(delta);
		x.scl(maximumSpeed);
		seeker.setVelocity(seeker.getVelocity().add(x));


	}

	public void setTarget(GameEntity target) {
		this.target = target;


	}
//	public Vector2 getPositionOfTarget() {
//		return positionOfTarget;
//	}
//
//	public void setPositionOfTarget(Vector2 positionOfTarget) {
//		this.positionOfTarget = positionOfTarget;
//	}

}


