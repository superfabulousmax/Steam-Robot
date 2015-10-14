package com.mygdx.SteamRobot.Behaviours;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.SteamRobot.Entities.EnemyEntity;

/**
 * aimless wandering behaviour
 * @author Sinead
 *
 */
public class Wander extends EnemyBehaviours {
	private Vector2 acceleration;
	private float accelerationRate = 400f;
	
	public Wander()
	{
		acceleration = new Vector2(0f,0f);
	}
	
	@Override
	public void applyBehaviour(float delta, EnemyEntity entity) {
		//generate a random vector, and normalise it and multiply by accelRate
		 float randomX = MathUtils.random(-1000,1000);
		 float randomY = MathUtils.random(-1000,1000);
		 Vector2 randomAccelerationVector = new Vector2(randomX, randomY);
		 randomAccelerationVector.nor();
		 randomAccelerationVector.scl(delta);
		 randomAccelerationVector.scl(accelerationRate);
		 //add random vector to accel
		 acceleration.add(randomAccelerationVector);
		 //add accel to vel
		 Vector2 accelCopy = new Vector2(acceleration);
		 accelCopy.scl(delta);
		 entity.setVelocity(entity.getVelocity().add(accelCopy));
		 
		
	}

	
}
