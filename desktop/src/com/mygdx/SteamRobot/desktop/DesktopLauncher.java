package com.mygdx.SteamRobot.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.SteamRobot.MyGdxSteamRobot;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		//set screen settings
		config.height=900;
		config.width=1500;
		config.resizable=false;
		
		//call game screen
		new LwjglApplication(new MyGdxSteamRobot(), config);
	}
}
