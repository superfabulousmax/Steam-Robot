package com.mygdx.SteamRobot.Screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.SteamRobot.GameState;
import com.mygdx.SteamRobot.MyGdxSteamRobot;
/**
 * Class that displays text to player 
 * moving through letters of text
 * about game dialogue on a screen
 * make the text display faster
 * or skipping through text
 * @author Sinead
 *
 */
public class DialogueScreen implements Screen{
	private MyGdxSteamRobot game;

	//Text, current position, and number of positions shown per seconds.
	private String text;
	private float currentIndex;
	private float indicesPerSeconds; // how many indices change per second
	private float pauseCounter; //If this is non-zero, instead of adding to currentIndex we subtract from this counter.
	private int lastPausedAt; //The last index we paused at
	private BitmapFont font;
	private SpriteBatch batch;
	//starting x to draw line
	private float x1;
	//ending x of a line
	private float x2;
	//starting y of dialogue
	private float y1;
	//ending y of dialogue before clearing screen and writing from top
	private float y2;
	//width of text per line on screen
	private float width;
	private GlyphLayout layout;
	//lines of text
	private List<String> lines;
	//music
	private Music backgroundSong;
	//play button
	private ShapeRenderer shapeBatch;
	private Rectangle rect;
	private boolean goToMenu;

	public DialogueScreen(MyGdxSteamRobot game, String text, float indicesPerSeconds, boolean goToMenu)
	{
		this.game = game;
		this.text = text;
		this.indicesPerSeconds = indicesPerSeconds;
		this.currentIndex = 0;
		this.pauseCounter = 0;
		this.lastPausedAt = -1;
		font = new BitmapFont(Gdx.files.internal("1942.fnt"),
				Gdx.files.internal("1942.png"),false);
		batch = new SpriteBatch();

		x1 = GameState.screenWidth / 4;
		x2 = 3*(GameState.screenWidth / 4);

		y1 = GameState.screenHeight - 20;
		y2 = 20;
		width = x2 - x1;
		layout = new GlyphLayout();
		//get list of text lines
		lines = splitTextByLine(text, width);
		backgroundSong= Gdx.audio.newMusic(Gdx.files.internal("music/memories.mp3"));
		backgroundSong.setLooping(true);
		shapeBatch = new ShapeRenderer();
		rect = new Rectangle();
		rect.x = GameState.screenWidth -300;
		rect.y = 50;
		rect.width = 200;
		rect.height = 60;
		this.goToMenu = goToMenu;
	}
	@Override
	public void show() {
		// start the playback of the background music immediately
		backgroundSong.play();

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);//set screen to black
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//clear colour buffer

		//Index tells us how much we have printed thus far.
		float index = 0;
		float currentY = y1;
		//render continue button in bottem right corner

		font.setColor(Color.WHITE);
	
		
		
		
		
		shapeBatch.begin(ShapeType.Line);
		shapeBatch.rect(rect.x, rect.y, rect.width, rect.height);
		shapeBatch.end();
		
		batch.begin();
		font.draw(batch, "continue", rect.x + 20, rect.y + 50);
		batch.end();
		

		

		for(String line : lines) {
	
			if(index +  line.length() < currentIndex) {

				batch.begin();
				font.draw(batch,line , x1, currentY);
				batch.end();

				index += line.length();

				//get height of line
				layout.setText(font, line);
				currentY -= layout.height;

			}
			else{


				batch.begin();
				font.draw(batch,line.substring(0,(int)(currentIndex - index)) , x1, currentY);
				batch.end();
				
				//Is the last character drawn a full-stop or a question mark etc? If so, pause.
				String pauseOnChars = ".?!,";
				int indexOfLastChar = (int)(currentIndex - index) - 1;
				if (indexOfLastChar > -1 && indexOfLastChar != lastPausedAt) {
					if (pauseOnChars.indexOf(line.charAt(indexOfLastChar)) > -1) {
						pauseCounter = .5f;
						lastPausedAt = indexOfLastChar;
					}
				}

				break;

			}

		}
		update(delta);




	}

	public void update(float delta) {
		//move twice as fast if player holds space down
		float indicesPerSecondPrime = indicesPerSeconds;
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) indicesPerSecondPrime *= 2;

		//move index foward and make it to text length
		if (pauseCounter > 0) pauseCounter -= delta;
		else currentIndex += delta*indicesPerSecondPrime;
		//if (currentIndex > text.length()) currentIndex = text.length();
		
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT))
		{
			Vector2 mousePosition = new Vector2(Gdx.input.getX(), GameState.screenHeight - Gdx.input.getY());
			if(rect.contains(mousePosition)) {
				dispose();
				if(goToMenu)
					game.setScreen(game.menuScreen);
				else
					game.setScreen(game.playScreen);
			
			
			}
			
				
		}


	}

	/**
	 * Split text into list to not overlap
	 * words between lines
	 */

	public List<String> splitTextByLine(String text , float width)
	{
		List <String> textLines = new ArrayList<String>();
		String [] words = text.split(" ");
		//let's say that can get a width of a text line
		float widthSofar = 0f;
		//accumulate line
		String line = "";
		for(int i = 0; i < words.length; i++) {


			if(widthSofar < width) {
				line += words[i]+" ";
				layout.setText(font, line);
				widthSofar = layout.width;
			}

			else {
				textLines.add(line);
				//get word for next line
				line = words[i]+" ";
				//get width of word
				layout.setText(font, line);
				widthSofar = layout.width;
			}
		}
		//if have last line hanging there
		if(line.length() > 0 )
			textLines.add(line);

		return textLines;
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
		this.currentIndex = 0;
		this.pauseCounter = 0;
		backgroundSong.dispose();
	}

}
