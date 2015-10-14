package com.mygdx.SteamRobot.PathFinding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Read a map from file to array
 * @author Sinead
 *
 */
public class MapReader {



	public MapReader() {

	}

	public static int [][] readFileToMap(String filename) {
		int heightInTiles;
		int widthInTiles;
		int [][] bitmapOfMap = new int[0][0];
		BufferedReader reader;
		try{
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			String [] arrayInformation = line.split(" ");
			heightInTiles = Integer.parseInt(arrayInformation[0]);
			widthInTiles = Integer.parseInt(arrayInformation[1]);
			bitmapOfMap = new int [heightInTiles][widthInTiles];
			for(int rows = 0; rows < heightInTiles; rows++)
			{
				line = reader.readLine();
				int cols = 0;
				for(char c: line.toCharArray())
					bitmapOfMap [rows][cols++] = c - '0';//Integer.parseInt(""+c);

			}
			reader.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return bitmapOfMap;

	}

}
