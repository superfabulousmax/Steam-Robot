package com.mygdx.SteamRobot.Entities;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.SteamRobot.GameState;

/**
 * Genereate random enemies on the screen based on
 * their probality of occuring
 * @author Sinead
 * @version 9/18/2015
 */
public class EnemyFactory {
	
	//Basic fixed test probabilities
	
	
	public static final float[] STANDARD_PROBABILITIES = {1.0f};
	
	public static EnemyEntity generateEntity(float[] probabilities) {
		
		float randomProbability = MathUtils.random(0f, 1.0f);
		float sumOfProbabilities = 0f;
		EnemyEntity enemyEntity = new GearEntity(500, 100);
		
		for(int i = 0; i < probabilities.length; i++)
		{
			sumOfProbabilities += probabilities[i];
			if(sumOfProbabilities >= randomProbability) {
				//enemyProbability = probabilities[i];
				//GENERATE THIS ENEMY AND RETURN IT
				if( i == 0)
				{
					int pickASideX = MathUtils.random(0, 2); //whether to randomly generate it from left or right side or top or bottom side (off screen)
					//left side
					float x = MathUtils.random(-100, 0);
					float y = MathUtils.random(0, GameState.screenHeight + 20);
					//right side
					if(pickASideX ==0) {
						x = MathUtils.random(GameState.screenWidth, GameState.screenWidth+100);
						y = MathUtils.random(0, GameState.screenHeight + 20);
					}
					//top side
					if(pickASideX ==1) {
						x = MathUtils.random(-20, GameState.screenWidth + 20);
						y = MathUtils.random(GameState.screenHeight, GameState.screenHeight +20);
					}
					//bottom side
					if(pickASideX ==2) {
						x = MathUtils.random(-20, GameState.screenWidth + 20);
						y = MathUtils.random(-20, 0);
					}
					
					enemyEntity = new GearEntity(x, y);
				}
				break;
			}
			
				
		}
		
		return enemyEntity;
	}

}
