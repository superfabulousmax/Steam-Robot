package com.mygdx.SteamRobot.Entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.SteamRobot.GameState;
import com.mygdx.SteamRobot.Screens.PlayScreen;

/**
 * Class that contains steam robot information
 * @author Sinead Urisohn
 * @version 8/15/2015
 *
 */
public class RobotoEntity extends GameEntity{

	//fields

	//animation

	private static final int FRAME_COLS=6;
	private static final int FRAME_ROWS=1;
	private Animation bloodAnimation;
	private Texture bloodSheet;
	private TextureRegion[] bloodFrames;
	private TextureRegion currentBloodFrame;
	private Rectangle blood;
	private float stateTime;



	private ShapeRenderer circle;
	float radiusOfCircle;
	private float speed;
	private Vector2 velocity;
	private Sprite torso;
	private Sprite arm;
	private float totalSwingAngle;
	private boolean isSwiping;
	private Polygon armBound;
	private Polygon torsoBound;
	private Polygon bloodBound;

	//constructor
	public RobotoEntity(float x, float y) {

		super(x, y);

		bloodSheet =  new Texture(Gdx.files.internal("robot/blood.png"));

		blood = new Rectangle();
		blood.width = bloodSheet.getWidth()/FRAME_COLS;
		blood.height = bloodSheet.getHeight()/FRAME_ROWS;	


		TextureRegion[][]tmp
		=TextureRegion.split(bloodSheet,bloodSheet.getWidth()/FRAME_COLS,bloodSheet.getHeight()/FRAME_ROWS);
		bloodFrames=new TextureRegion[FRAME_COLS*FRAME_ROWS];
		int index=0;

		for(int i =0;i<FRAME_ROWS;i++)
		{
			for(int j =0;j<FRAME_COLS;j++)
			{
				bloodFrames[index++]=tmp[i][j];
			}
		}
		bloodAnimation = new Animation(0.2f, bloodFrames);

		stateTime=0.4f;

		circle = new ShapeRenderer();
		speed=250.0f;
		radiusOfCircle=50.0f;
		velocity =  new Vector2(0f,0f);
		torso = new Sprite(new Texture(Gdx.files.internal("robot/torso.png")));

		arm = new Sprite(new Texture(Gdx.files.internal("robot/arm.png")));
		arm.setOrigin(arm.getWidth()/2, 10);

		totalSwingAngle = 0f;
		isSwiping = false;

		armBound = rectToPoly(arm.getBoundingRectangle());
		torsoBound = rectToPoly (torso.getBoundingRectangle());
		bloodBound = rectToPoly (blood);
		armBound.setOrigin(arm.getWidth()/2, 10);
		//set origin to be center
		torsoBound.setScale(0.5f, 0.9f);
		armBound.setScale(0.8f, 1f);
		torsoBound.setOrigin(torso.getWidth()/2, torso.getHeight()/2);
		bloodBound.setOrigin(blood.getWidth()/2, blood.getHeight()/2);
	}


	//methods
	/**
	 * render the robot 
	 * @param delta
	 */
	@Override
	public void render(float delta) {

		//draw robot
		PlayScreen.batch.begin();

		arm.draw(PlayScreen.batch);
		torso.draw(PlayScreen.batch);

		PlayScreen.batch.end();
		//draw bounding shape
		//		circle.begin(ShapeType.Line);
		//		circle.setColor(Color.BLUE);
		//		circle.polygon(torsoBound.getTransformedVertices());
		//		circle.polygon(armBound.getTransformedVertices());
		//		
		//		circle.end();


	}
	/**
	 * Animate blood flowing out
	 * robot when he dies
	 */

	public void animateBlood(SpriteBatch sb)
	{
		stateTime += Gdx.graphics.getDeltaTime();   
		currentBloodFrame = bloodAnimation.getKeyFrame(stateTime, true);
		sb.begin();

		sb.draw(currentBloodFrame, bloodBound.getX(),  bloodBound.getY());

		sb.end();
	}
	/**
	 * update robot position
	 * @param delta
	 */
	@Override
	public void update(float delta)
	{


		checkRobotWithinScreen();
		updateRobotPosition(delta);
		playerInput(delta);

	}

	/**
	 * update robot position to move towards the
	 * mouse pointer and stop
	 * once sufficiently close
	 */
	public void updateRobotPosition(float delta)
	{ 

		Vector2 mousePosition = new Vector2(Gdx.input.getX(), GameState.screenHeight - Gdx.input.getY());
		Vector2 mousePositionCopy = new Vector2(mousePosition);
		Vector2 robotPositionCopy =  new Vector2(getPosition());

		float distance = mousePositionCopy.sub(robotPositionCopy).len();
		Vector2 mousePositionCopy2 = new Vector2(mousePosition);
		Vector2 robotPositionCopy2 =  new Vector2(getPosition());

		torso.setCenterX(getPosition().x);
		torso.setCenterY(getPosition().y);
		torsoBound.setPosition(torso.getX(), torso.getY());
		bloodBound.setPosition(torso.getX(), torso.getY());


		float deltaAngle;
		if (distance < 4) deltaAngle = 0;
		else deltaAngle = adjustAngleForSmoothTurning(delta, torso.getRotation(), mousePositionCopy2.sub(robotPositionCopy2).angle());

		torso.setRotation(torso.getRotation() + deltaAngle);
		torsoBound.setRotation(torso.getRotation());
		arm.setRotation(torso.getRotation());
		armBound.setRotation(torso.getRotation());

		float angleOfArm = 90 + torso.getRotation();
		float y = (float) Math.sin(MathUtils.degreesToRadians*angleOfArm);
		float x = (float) Math.cos(MathUtils.degreesToRadians*angleOfArm);

		Vector2 armVec = new Vector2(x, y);
		armVec.nor();
		armVec.scl(torso.getHeight()/2.5f);
		//subtract from x and y to line the rendering with the rotation around the origin
		//of arm.setOrigin(arm.getWidth()/2, 10);
		arm.setX(armVec.x + getPosition().x - arm.getOriginX());
		arm.setY(armVec.y + getPosition().y - arm.getOriginY());
		armBound.setPosition(arm.getX(), arm.getY());

		if(distance<2)//set velocity to 0
		{
			setVelocity(new Vector2(0,0));

		}
		else
		{
			setVelocity(mousePosition.sub(getPosition()).nor().scl(speed));
		}
		//add to the position velocity multiplied by delta;
		Vector2 pos = new Vector2(this.getPosition());
		Vector2 vel = new Vector2(this.getVelocity());
		this.setPosition(pos.add(vel.scl(delta)));

	}



	public void checkRobotWithinScreen()
	{
		Vector2 robotPostionCopy = new Vector2(getPosition());

		if(robotPostionCopy.x < radiusOfCircle )
			setPosition(new Vector2(radiusOfCircle, robotPostionCopy.y));
		if(robotPostionCopy.x > GameState.screenWidth - radiusOfCircle)
			setPosition(new Vector2(GameState.screenWidth -radiusOfCircle, robotPostionCopy.y));

	}
	/**
	 * On mouse click
	 * swipe robot arm
	 */
	public void doSwipe(float delta)
	{
		float currentAngle = torso.getRotation();
		totalSwingAngle += 500*delta;
		if(totalSwingAngle > 160) {
			totalSwingAngle = 160;
			isSwiping =false;
		}
		float angle = currentAngle-totalSwingAngle;
		arm.setRotation(angle);
		armBound.setRotation(angle);


	}

	/**
	 * get angle between 0 and 360
	 */
	public float getAdjustedAngle(float angle)
	{
		while(angle < 0) angle += 360;
		while(angle > 360)angle -=360;
		return angle;
	}
	/**
	 * 
	 * @param delta
	 * @param robotAngle
	 * @param mouseAngle
	 * @return smooth rotation angle to make robot face the mouse
	 */
	public float adjustAngleForSmoothTurning(float delta, float robotAngle, float mouseAngle)
	{
		float deltaAngle = robotAngle - mouseAngle;
		deltaAngle = getAdjustedAngle(deltaAngle);

		if( deltaAngle >= 0 & deltaAngle <= 180) return -Math.min(delta*500, deltaAngle); //Math.min tells it to go as far as the mouse angle and no futher
		else return Math.min(delta*500, Math.abs(deltaAngle - 360));
	}

	/**
	 *  check player input for in game screen requests
	 */
	public void playerInput(float delta)
	{

		if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			isSwiping = true;
			doSwipe(delta);

		}
		else {
			arm.setRotation(torso.getRotation());
			totalSwingAngle = 0f;
			isSwiping = false;
		}


	}


	//getters and setters
	public boolean isSwiping() {
		return isSwiping;
	}


	public void setSwiping(boolean isSwiping) {
		this.isSwiping = isSwiping;
	}



	public Vector2 getVelocity() {
		return velocity;
	}


	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public Polygon getArmBound() {
		return armBound;
	}

	@Override
	public Polygon getBound() {

		return torsoBound;
	}



	/**
	 * add all sprites of robot
	 * and return list of sprites
	 */

	@Override
	public List<Sprite> getSprite() {
		List<Sprite> list = new  ArrayList<Sprite>();
		list.add(arm);
		list.add(torso);
		return list;
	}

	/**
	 * Generates a polygon from a rectangle
	 * @param r rectangle to convert
	 * @return polygon converted from rectangle
	 */
	private Polygon rectToPoly(Rectangle r) {

		return new Polygon(new float[]{
				r.x + r.width, r.y,
				r.x + r.width, r.y + r.height,
				r.x, r.y + r.height,
				r.x, r.y
		});

	}


}
