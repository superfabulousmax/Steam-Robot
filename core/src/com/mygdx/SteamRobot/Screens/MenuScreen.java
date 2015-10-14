package com.mygdx.SteamRobot.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.SteamRobot.GameState;
import com.mygdx.SteamRobot.MyGdxSteamRobot;

/**
 * Game menu screen
 * @author Sinead
 *
 */
public class MenuScreen implements Screen {
	
	
	private MyGdxSteamRobot game;

	private SpriteBatch sb;
	//texures, fonts and buttons
	private Texture menuBackground;
	private Texture menuBackgroundText;
	private Texture play;
	private Stage stage;
	private TextButton button;
	private BitmapFont font;
	private TextButtonStyle textButtonStyle;
	private Skin skin;
	private TextureAtlas buttonAtlas;
	private float counterTime;

	//music
	private Music backgroundSong;

	public MenuScreen(MyGdxSteamRobot game) {
		super();
		

		this.game = game;
		sb= new SpriteBatch();
		stage = new Stage();
		menuBackground = new Texture(Gdx.files.internal("SteamRobotLogo.png"));
		menuBackgroundText = new Texture(Gdx.files.internal("logoTitleText.png"));
		play = new Texture(Gdx.files.internal("ppButtons/play.png"));
		Gdx.input.setInputProcessor(stage);
		font = new BitmapFont(Gdx.files.internal("Fipps20.fnt"),Gdx.files.internal("Fipps20.png"),false);
		skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("ppButtons/myButtons.atlas"));
		skin.addRegions(buttonAtlas);
		TextButtonStyle style = new TextButtonStyle();
		style.up= skin.getDrawable("play");
		style.down = skin.getDrawable("play");
		style.font=font;
		button = new TextButton(null, style);
		button.setPosition(1000,240); //** Button location **//
		button.setHeight(play.getHeight()); //** Button Height **//
		button.setWidth(play.getWidth()); //** Button Width **//
		stage.addActor(button);
		backgroundSong= Gdx.audio.newMusic(Gdx.files.internal("planet_lonely.mp3"));
		backgroundSong.setLooping(true);
		counterTime = 0f;

	}


	/**
	 * called when this screen becomes the current screen
	 */
	@Override
	public void show() {
		//add button back onto stage
		stage.addActor(button);
		// start the playback of the background music immediately
		backgroundSong.play();
		

	}

	@Override
	public void render(float delta) {
		counterTime += delta;
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		// This line clears the screen.
		sb.begin();
		sb.draw(menuBackground, GameState.screenWidth/2 - menuBackground.getWidth()/2, 0);
		sb.draw(menuBackgroundText, (float) (700 + 5*Math.sin(counterTime*1.1)), (float) (700 + 20*Math.sin(counterTime)));
		sb.end();


		stage.draw();
		button.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				dispose();
				game.setScreen(game.dialogueScreen);

			}
		});


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
		backgroundSong.dispose();
		stage.clear();
//		font.dispose();
//		stage.dispose();
//		menuBackground.dispose();
//		menuBackgroundText.dispose();
	}

}
