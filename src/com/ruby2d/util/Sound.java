package com.ruby2d.util;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	// Sound effects
	public Clip keyCollectSound, doorUnlockSound;
	
	// Music
	public Clip backgroundMusic;
	
	public Sound() {
		keyCollectSound = loadSound("res/assets/Blue Boy Adventure/Sound/coin.wav");
		doorUnlockSound = loadSound("res/assets/Blue Boy Adventure/Sound/dooropen.wav");
		
		backgroundMusic = loadSound("res/assets/Blue Boy Adventure/Sound/BlueBoyAdventure.wav");
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	private Clip loadSound(String path) {
		File keyCollectSoundFile = new File(path);
		AudioInputStream audioStream = null;
		try {
			 audioStream = AudioSystem.getAudioInputStream(keyCollectSoundFile);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Clip soundClip = null;
		try {
			soundClip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		try {
			soundClip.open(audioStream);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return soundClip;
	}
}
