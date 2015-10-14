package com.mygdx.SteamRobot.PathFinding;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Write bit map to textfile to be used as map
 * when playing
 * @author Sinead
 *
 */
public class MapWriter {
	
	public MapWriter(){
		
		
	}

	public static void writeMapToFile(String filename, TileMap map){
		int [][] bitmapOfMap = map.getBitmapOfMap();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
			writer.write(map.getHeightInTiles()+" "+map.getWidthInTiles()+"\r\n");
			for(int rows = 0; rows < map.getHeightInTiles(); rows++)
			{

				for(int cols = 0; cols < map.getWidthInTiles() ; cols++)
				{

					writer.write(""+bitmapOfMap[rows][cols]);

				}
				writer.write("\r\n");

			}
			writer.close();

		} catch (IOException e) {

			e.printStackTrace();
		}


	}
}
