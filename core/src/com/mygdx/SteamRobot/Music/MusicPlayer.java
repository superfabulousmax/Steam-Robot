package com.mygdx.SteamRobot.Music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

/**
 * Generic music player that loops through
 * a set of songs during level play
 * @author Sinead
 *
 */
public class MusicPlayer implements OnCompletionListener {
	
	private Music currentSongPlaying;
	private String [] songsInJukeBox 
	= { "tree_song_acapella","the_grand_soul_flits_freely",
			"twinkling_photon", "crazy_in_love_light", "think_it_over",
			"catch_it_up", "night_sound", "dark_nebula", "gray_in_calculation",
			"planet_blue", "nightmare", "chase_to_it"};
	private int currentPositionOfSong;
	
	public MusicPlayer()
	{
		currentPositionOfSong = 0;
		currentSongPlaying= Gdx.audio.newMusic(Gdx.files.internal("music/"+songsInJukeBox[currentPositionOfSong]+".mp3"));
		currentSongPlaying.setLooping(false);
		currentSongPlaying.setOnCompletionListener(this);
		
	}
	
	public void playThroughSongs()
	{
		
		currentSongPlaying.play();
	}
	
	/** 
	 * set current position of song
	 * @param position of song
	 */
	public void setCurrentPositionOfSong(int position)
	{
		if(position > songsInJukeBox.length -1) this.currentPositionOfSong = 0;
		else this.currentPositionOfSong = position;
	}
	/** 
	 * @return position of song currently playing
	 */
	public int getCurrentPositionOfSong()
	{
		return currentPositionOfSong;
	}
	
	public void pauseSong()
	{
		currentSongPlaying.pause();
	}

	public void playSong()
	{
		currentSongPlaying.play();//if song was paused will resume playback
	}
	public void stopSong()
	{
		currentSongPlaying.stop();//next time play invoked song will start from beginning
	}

	@Override
	public void onCompletion(Music music) {
		music.dispose();
		setCurrentPositionOfSong(getCurrentPositionOfSong()+1);
		currentSongPlaying= Gdx.audio.newMusic(Gdx.files.internal("music/"+songsInJukeBox[currentPositionOfSong]+".mp3"));
		currentSongPlaying.setOnCompletionListener(this);
		playSong();
		
	}

}
