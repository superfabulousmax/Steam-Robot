package com.mygdx.SteamRobot.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.SteamRobot.GameState;
import com.mygdx.SteamRobot.MyGdxSteamRobot;
import com.mygdx.SteamRobot.TileBackground;
import com.mygdx.SteamRobot.Entities.GameEntity;
import com.mygdx.SteamRobot.Entities.GearEntity;
import com.mygdx.SteamRobot.Music.MusicPlayer;


public class PlayScreen implements Screen{
	private MyGdxSteamRobot game;

	//fields
	public static SpriteBatch batch;
	GameState world;
	MusicPlayer player;
	boolean isPaused;
	int score;
	TileBackground background;
	String text;

	//font
	BitmapFont screenFont;

	public PlayScreen(MyGdxSteamRobot game) {
		super();
		this.game = game;
		batch = new SpriteBatch();
		world = new GameState();
		player = new MusicPlayer();
		isPaused = false;
		screenFont = new BitmapFont(Gdx.files.internal("1942.fnt"),
				Gdx.files.internal("1942.png"),false);
		score = 0;
		background = new TileBackground(new Texture(Gdx.files.internal("tile2.jpeg")));
		//background = new TileBackground();
		text = "Jeepers! I awoke to find my office in ruins. "
				+ "Lots of bottles everywhere, papers scattered."
				+ " Remembering the devastation of the previous day, "
				+ "I ran into my laboratory to look for the steam robot. "
				+ "It broke my heart, he was in pieces. "
				+ "Desperate to see what happened I played my laboratory’s projector. "
				+ "What I saw was not pretty.. me completely inebriated completely trashing the place. "
				+ "I was going on about inanimate steam objects coming to life."
				+ " I guess I had a bit too much to drink last night. ";
	}

	@Override
	public void show() {
		player.playThroughSongs();

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);//set screen to white
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//clear colour buffer
		background.render();
		update(delta);
		world.render(delta);
		batch.begin();
		if(world.isGameOver())
		{
			
			screenFont.setColor(Color.WHITE);
			screenFont.draw(batch, "game over\nNew Game? y or n.", GameState.screenWidth/2 -100, GameState.screenHeight/2);
			
			
		}
		if(isPaused) {
			
			screenFont.setColor(Color.WHITE);
			screenFont.draw(batch, "paused", GameState.screenWidth/2-100, GameState.screenHeight/2);


		}
		
		if(score == 100)
		{
	
			world.setWin(true);
			screenFont.setColor(Color.WHITE);
			screenFont.draw(batch, "You Win!\nPress c to continue", GameState.screenWidth/2-100, GameState.screenHeight/2);
		}

		
		screenFont.setColor(Color.WHITE);
		screenFont.draw(batch, "score: "+score, GameState.screenWidth -300, 60);

		batch.end();



	}

	/**
	 * check player input for in game screen requests
	 */
	public void playerInput()
	{
		if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			isPaused = !isPaused;
			if(isPaused == false)
				player.playSong();
		}

		if(world.isGameOver() & Gdx.input.isKeyJustPressed(Input.Keys.Y)) {

			world = new GameState();
			player.playThroughSongs();
			score = 0;
		}

		if(world.isGameOver() & Gdx.input.isKeyJustPressed(Input.Keys.N)) {
			world = new GameState();
			score = 0;
			player.stopSong();
			game.setScreen(game.menuScreen);
			

		}
		
		if(world.isWin() & Gdx.input.isKeyJustPressed(Input.Keys.C))
		{
			
			world = new GameState();
			score = 0;
			//player.stopSong();
			game.setScreen(new DialogueScreen(game, text , 10, true));
			
		}


	}

	/**
	 * Update game play screen
	 * @param delta
	 */
	public void update(float delta)
	{
		playerInput();

		if(world.isGameOver()==true | world.isWin())
		{
			if(world.isGameOver()==true) world.playerEntity.animateBlood(batch);
			for(GameEntity entity: world.getEntities())
			{
				if(entity instanceof GearEntity)
					entity.update(delta);
			}
			return;
		}
		
		if(isPaused){
			player.pauseSong();
			return;
		}
		


		world.update(delta);

		//check for collision when player swipes
		if(world.isCollisionWithRobotArm(world.playerEntity) & world.playerEntity.isSwiping()) {
			world.removeEntityFromWorld(world.getPositionOfCollidingEntity());
			score++;

		}
		if(world.isCollision(world.playerEntity))
		{
			world.setGameOver(true);
		}

		world.changeGearDirection();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {


	}
}
