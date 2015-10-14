package com.mygdx.SteamRobot.PathFinding;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

/**
 * Keeps the path for entity to follow
 * with the steps to take
 * 
 * @author Sinead
 *
 */
public class Path {

	ArrayList<Step>stepsInThePath ; //keep track of how to get from start to goal.

	public ArrayList<Step> getStepsInThePath() {
		return stepsInThePath;
	}

	
	public Path() {
		stepsInThePath = new ArrayList<Step>();
	}

	public int getPathSize() {
		return stepsInThePath.size();
	}

	/**
	 * get a step in the path
	 * @param index the position of the step
	 * @return the specified step in the path
	 */
	public Step getStepInPath(int index) {
		return stepsInThePath.get(index);
	}
	
	/**
	 * 
	 * @param step The current step int the path
	 * @return The position of the given step in the path
	 */
	public int getPositionOfStepInPath(Step step) {
		return stepsInThePath.indexOf(step);
	}
	
	/**
	 * get the next step in the path
	 * @param currentStep The current step in the path
	 * @return The next step in the path from the current step, or null if it was the last one.
	 */
	public Step getNextStep(Step currentStep) {
		try {
			return stepsInThePath.get(getPositionOfStepInPath(currentStep)+1);
		} catch(Exception e) {
			return null;
		}
	}
	/**
	 * 
	 * @param index The index of the specified step
	 * @return the x position of the step
	 */
	public int getXOfStep(int index) {
		return stepsInThePath.get(index).getX();
	}
	
	/**
	 * 
	 * @param index The index of the specified step
	 * @return the y position of the step
	 */
	public int getYOfStep(int index) {
		return stepsInThePath.get(index).getY();
	}
	
	/**
	 * adds a step to the path beginning
	 * @param x The x position of the step
	 * @param y The y position of the step
	 */
	public void addStepToTheStart(int x, int y) {
		stepsInThePath.add(0, new Step(x, y));
	}
	
	/**
	 * adds a step to the path
	 * @param x The x position of the step
	 * @param y The y position of the step
	 */
	public void addStepToTheEnd(int x, int y) {
		stepsInThePath.add(new Step(x, y));
	}
	/**
	 * Check whether the path has this step
	 * @param x The x position of the step
	 * @param y The y position of the step
	 * @return true if the path already has 
	 * this step
	 */
	public boolean contains(int x, int y) {
		return stepsInThePath.contains(new Step(x , y));
	}


	/**
	 * A step to take in the path
	 * i.e. an x and y position to go to
	 * @author Sinead
	 *
	 */
	public class Step {

		private int x;
		private int y;
		private int stepWidth;
		private int stepHeight;


		public Step(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int getStepWidth() {
			return stepWidth;
		}
		public void setStepWidth(int stepWidth) {
			this.stepWidth = stepWidth;
		}
		public int getStepHeight() {
			return stepHeight;
		}
		public void setStepHeight(int stepHeight) {
			this.stepHeight = stepHeight;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		
		/**
		 * Check if a location is in the step
		 * @param x The x position of the center of the entity
		 * @param y The y position of the center of the entity
		 * @param step The step to check
		 * @return true if the position is in the
		 * grid block of the step
		 */
		public boolean atStep(Step step, float x, float y) {
			float distance = distanceToStep(x, y, step.getX(), step.getY());
			if(distance <= 1f)
				return true;
			else return false;
			
		}
		/**
		 * 
		 * @param x
		 * @param y
		 * @param stepX
		 * @param stepY
		 * @return distance to get inside grid step
		 */
		
		public float distanceToStep(float x, float y, float stepX, float stepY) {
			
			Vector2 entityPos = new Vector2(x, y);
			Vector2 stepPos = new Vector2(stepX*TileMap.tileWidth + TileMap.tileWidth/2.0f, stepY*TileMap.tileHeight + TileMap.tileHeight/2.0f);
			float distance = entityPos.sub(stepPos).len();
			return distance;
			
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			if (!(obj instanceof Step)) return false;
			if (obj == this) return true;
			
			Step step = (Step) obj;
			if (step.getX() == this.x && step.getY() == this.y)
				return true;
			return false;
		}


	}

}
