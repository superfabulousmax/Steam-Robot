package com.mygdx.SteamRobot;

import com.badlogic.gdx.Game;
import com.mygdx.SteamRobot.Screens.DialogueScreen;
import com.mygdx.SteamRobot.Screens.MapMakerScreen;
import com.mygdx.SteamRobot.Screens.MenuScreen;
import com.mygdx.SteamRobot.Screens.PathFindingLevelScreen;
import com.mygdx.SteamRobot.Screens.PlayScreen;
/**
 * Screens of Game
 * @author Sinead Urisohn
 *	@version
 */

public class MyGdxSteamRobot extends Game {

	//screens
	public MenuScreen menuScreen;
	public PlayScreen playScreen;
	public DialogueScreen dialogueScreen;
	public  MapMakerScreen mapMaker;
	public PathFindingLevelScreen pathLevelScreen;


	String text = 
			"What is happening to everything?? "+""
					+ "Why have these seemingly innocuous steam punk objects come to life?"
					+ " And they are attacking people. I Dr. Radionova must help."
					+ " I can use my latest invention.. the steam robot. "
					+ "With his glorious long swooping robot arm, he just could save us all. "
					+ "This airship must not fall. Good luck steam robot. "
					+ "Our fate is in your rusty robot hands.. Ahh I need some steam vodka.";

	@Override
	public void create() {
		//create screens and set current screen to menu
		menuScreen = new MenuScreen(this);
		playScreen= new PlayScreen(this);
		dialogueScreen = new DialogueScreen(this, text, 10,false);
		mapMaker = new MapMakerScreen(this);
		pathLevelScreen = new PathFindingLevelScreen(this);
		setScreen(menuScreen);
	}


}
