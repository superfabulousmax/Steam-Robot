package com.mygdx.SteamRobot.Behaviours;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.SteamRobot.Entities.EnemyEntity;
import com.mygdx.SteamRobot.PathFinding.Path;
import com.mygdx.SteamRobot.PathFinding.Path.Step;
import com.mygdx.SteamRobot.PathFinding.TileMap;

/**
 * behaviour that allows the entity to
 * walk the path found by the a* algorithm
 * @author Sinead
 *
 */
public class WalkPath extends EnemyBehaviours{

	private Path path;
	private Step currentStep;
	private Step nextStep;
	private final float maximumSpeed = 100f;

	public WalkPath(Path path) {

		setPath(path);

	}
	@Override
	public void applyBehaviour(float delta, EnemyEntity entity) {
		//get position of entity to walk path
		Vector2 positionOfEntity = new Vector2(entity.getPosition());
		if(path==null){
			//check if is at goal before stopping
			float distance = currentStep.distanceToStep(positionOfEntity.x, positionOfEntity.y, nextStep.getX(), nextStep.getY());
			
			//at goal stop
			if(distance<=1f)
				entity.setVelocity(new Vector2(0,0));
			return;
		}


		nextStep = path.getNextStep(currentStep);
		if (nextStep == null) {
			entity.setVelocity(new Vector2(0,0));
			return;
		}
		
		//check if entity is at the step in the path
		//if it is make it move to the next step
		if(!currentStep.atStep(nextStep, positionOfEntity.x, positionOfEntity.y)) {


			Vector2 positionOfStep = new Vector2(nextStep.getX() *TileMap.tileWidth + 1.0f/2*TileMap.tileWidth, nextStep.getY()*TileMap.tileHeight + 1.0f/2*TileMap.tileHeight);
			//get vector between 2 vectors
			Vector2 result = positionOfStep.sub(positionOfEntity);

			result.nor();
			//result.scl(delta);
			result.scl(maximumSpeed);
			entity.setVelocity(result);


		}
		else {
			currentStep = nextStep;
			
		}

	}

	/**
	 * Set the current step in the path
	 * @param currentStep The current step in the path
	 */
	public void setCurrentStep(Step currentStep) {
		this.currentStep = currentStep;
	}
	/**
	 * Set the next step in the path
	 * @param nextStep The next step in the path
	 */
	public void setNextStep(Step nextStep) {
		this.nextStep = nextStep;
	}
	/**
	 * Set Path to walk
	 * 
	 */
	public void setPath(Path path) {

		this.path = path;
		
		if(path!=null)
			currentStep = path.getStepInPath(0);
	}



}
