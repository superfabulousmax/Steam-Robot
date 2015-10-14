package com.mygdx.SteamRobot.Behaviours;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.SteamRobot.Entities.EnemyEntity;
import com.mygdx.SteamRobot.Entities.GameEntity;
/**
 * Behaviour where enemy approaches player while
 * mantaining a certain distance from the player,
 * attacking from that distance
 * running away if player comes closer than that distance
 * @author Sinead
 *
 */
public class Shy extends EnemyBehaviours {

	private GameEntity target;
	private final float maximumApproachSpeed = 100f;
	private final float maximumEscapeSpeed = 150f;
	@Override
	public void applyBehaviour(float delta, EnemyEntity entity) {
		//get max speed
		float maxSpeed = maximumApproachSpeed;
		//Get vector between seeker and target
		Vector2 positionOfSeeker = new Vector2(entity.getPosition());
		Vector2 positionOfTarget = new Vector2(target.getPosition());
		
		Vector2 x = positionOfTarget.sub(positionOfSeeker);
		
		float distanceBetween = x.len();
		System.out.println(distanceBetween);
		if(distanceBetween < 250) {
			x.scl(-1);
			maxSpeed = maximumEscapeSpeed;
		}
		x.nor();
		x.scl(delta);
		x.scl(maxSpeed);
		entity.setVelocity(entity.getVelocity().add(x));
		
	}
	public void setTarget(GameEntity target) {
		this.target = target;


	}

}
