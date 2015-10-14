package com.mygdx.SteamRobot.Entities;

import com.badlogic.gdx.math.Vector2;

public abstract class EnemyEntity extends GameEntity{
	private GameEntity target;
	private Vector2 velocity;
	private Vector2 positionOfTarget;
	//velocity
	
	public EnemyEntity(float x, float y) {
		super(x, y);
		velocity = new Vector2(0f, 0f);
	}
	
	
	
	//getters and setters
	
	public GameEntity getTarget() {
		return target;
	}



	public void setTarget(GameEntity target) {
		this.target = target;
	}



	public Vector2 getVelocity() {
		return velocity;
	}


	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	public Vector2 getPositionOfTarget() {
		return positionOfTarget;
	}

	public void setPositionOfTarget(Vector2 positionOfTarget) {
		this.positionOfTarget = positionOfTarget;
	}
	
	

}
