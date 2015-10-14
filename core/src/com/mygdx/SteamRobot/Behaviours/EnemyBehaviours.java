package com.mygdx.SteamRobot.Behaviours;

import com.mygdx.SteamRobot.Entities.EnemyEntity;

/**
 * Contains all the enemy behaviours
 * 
 * @author Sinead
 * @version 9/18/2015
 */
public abstract class EnemyBehaviours {
	
	/**
	 *each class implementing EnemyBehaviours
	 *will override this method with its
	 *specific behaviour e.g. chase, evade, shoot etc.
	 */
	public abstract void applyBehaviour(float delta, EnemyEntity entity);

}
