package com.mygdx.SteamRobot;

import java.util.ArrayList;
import java.util.List;

import sun.security.acl.WorldGroupImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.SteamRobot.CollisionDetection.Collision;
import com.mygdx.SteamRobot.Entities.EnemyEntity;
import com.mygdx.SteamRobot.Entities.EnemyFactory;
import com.mygdx.SteamRobot.Entities.GameEntity;
import com.mygdx.SteamRobot.Entities.GearEntity;
import com.mygdx.SteamRobot.Entities.RobotoEntity;

/**
 * Contains the state of the game
 * i.e. what entities are on screen
 * and player state
 * This handles when game is over.
 * This also handles how many enemies on screen
 * Handles collisions of entities too.
 * @author Sinead
 * @version 9/18/2015
 */
public class GameState {
	//what is in game world i.e. state of game
	private List<GameEntity> entities;
	public RobotoEntity playerEntity;
	//All game objects should know dimensions of screen
	public static int screenWidth =  Gdx.graphics.getWidth();
	public static int screenHeight = Gdx.graphics.getHeight();
	private int positionOfCollidingEntity;
	private float timerForGeneratingEnemies;
	private boolean gameOver;
	private boolean win;
	


	public GameState() {
		//initialise game objects
		this.entities = new ArrayList<GameEntity>();
		this.playerEntity = new RobotoEntity(screenWidth/2, screenHeight/2);

		EnemyEntity entity = (EnemyEntity) EnemyFactory.generateEntity(EnemyFactory.STANDARD_PROBABILITIES);
		entity.setTarget(playerEntity);
		entity.setPositionOfTarget(playerEntity.getPosition());
		this.entities.add(entity);

		this.entities.add(playerEntity);
		timerForGeneratingEnemies = 0f;
		gameOver = false;
		setWin(false);
	}





	/** 
	 * Update game state i.e.
	 * All the entities 
	 */
	public void update(float delta)
	{
		//loop through my entities and render each one 
		timerForGeneratingEnemies += delta;
		for(GameEntity entity: entities)
		{
			entity.update(delta);
		}
		while(counterForEnemies() < 10 & timerForGeneratingEnemies > 1) {


			EnemyEntity entity = (EnemyEntity) EnemyFactory.generateEntity(EnemyFactory.STANDARD_PROBABILITIES);
			entity.setTarget(playerEntity);
			entity.setPositionOfTarget(playerEntity.getPosition());
			this.entities.add(entity);
			timerForGeneratingEnemies = 0;
		}


	}


	/**
	 * Draw current game state
	 */
	public void render(float delta)
	{
		//loop through my entities and render each one 

		for(GameEntity entity: entities)
		{
			entity.render(delta);
		}

		//render the player on top of everything
		playerEntity.render(delta);

	}

	/**
	 * check if gear entity is colliding
	 * with another gear entity and make them 
	 * go in opposite direction
	 */
	public void changeGearDirection(){

		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i) instanceof GearEntity) {
				GearEntity gear = (GearEntity) entities.get(i);

				for(int j = i+1; j < entities.size(); j++) {
					if(entities.get(j) instanceof GearEntity) {
						
						GearEntity other = (GearEntity) entities.get(j);

						
						Collision collision = new Collision(gear);
						if(collision.isGearCollidingWithGear(other)) {
							gear.setVelocity(gear.getVelocity().scl(-1));
							other.setVelocity(other.getVelocity().scl(-1));
						}
					}
				}
			}
		}
	}

		/**
		 * Checks whether sprite is colliding 
		 * with any other sprite
		 * @param sprite to check
		 * @return
		 */
		public boolean isCollision(GameEntity entity)
		{
			Collision collision = new Collision(entity);
			for(int i = 0; i < entities.size(); i++)
			{
				if(!entity.equals(entities.get(i)))
				{

					if(collision.isColliding(entities.get(i))){

						setPositionOfCollidingEntity(i);
						return true;
					}

				}

			}
			return false;
		}

		/**
		 * Checks whether robot arm  is colliding 
		 * with any other entity
		 * @param robot entity to check
		 * @return
		 */
		public boolean isCollisionWithRobotArm(RobotoEntity robot)
		{
			Collision collision = new Collision(robot);
			for(int i = 0; i < entities.size(); i++)
			{
				if(!robot.equals(entities.get(i)))
				{

					if(collision.isCollidingWithRobotArm(entities.get(i))){

						setPositionOfCollidingEntity(i);
						return true;
					}

				}

			}
			return false;
		}

		/**
		 * removes specified entity from the list
		 * @param position of entity to remove from entity list
		 */
		public void removeEntityFromWorld(int position)
		{
			entities.remove(position);

		}
		public int getPositionOfCollidingEntity() {
			return positionOfCollidingEntity;
		}


		public void setPositionOfCollidingEntity(int positionOfCollidingEntity) {
			this.positionOfCollidingEntity = positionOfCollidingEntity;
		}

		public int counterForEnemies()
		{
			return entities.size()-1;
		}
		public boolean isGameOver() {
			return gameOver;
		}


		public void setGameOver(boolean gameOver) {
			this.gameOver = gameOver;
		}

		public boolean isWin() {
			return win;
		}

		public void setWin(boolean win) {
			this.win = win;
		}
		public List<GameEntity> getEntities() {
			return entities;
		}

		public void setEntities(List<GameEntity> entities) {
			this.entities = entities;
		}

	}
