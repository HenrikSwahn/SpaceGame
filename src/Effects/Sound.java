package Effects;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * Created by Nilj on 15-01-15.
 */

public class Sound {

    private Clip explosion;
    private Clip laser;
    private Clip laserCannon;
    private Clip menuMusic;

    public Sound() {
        try {
            File f = new File("explosionSound.wav");
            if(f.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(f);
                explosion = AudioSystem.getClip();
                explosion.open(sound);
            }

            f = new File("laser.wav");
            if(f.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(f);
                laser = AudioSystem.getClip();
                laser.open(sound);
            }

            f = new File("laserCannon.wav");
            if(f.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(f);
                laserCannon = AudioSystem.getClip();
                laserCannon.open(sound);
            }

            f = new File("MenuMusic.wav");
            if(f.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(f);
                menuMusic = AudioSystem.getClip();
                menuMusic.open(sound);
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void playExplosion() {
        explosion.stop();
        explosion.setFramePosition(0);
        explosion.start();
    }
    public void playLaser() {
        laser.stop();
        laser.setFramePosition(0);
        laser.start();
    }
    public void playLaserCannon() {
        laserCannon.stop();
        laserCannon.setFramePosition(0);
        laserCannon.start();
    }
    public void playMenuMusic() {
        if(menuMusic.isRunning()) {
            menuMusic.stop();
        }else {
            menuMusic.setFramePosition(0);
            menuMusic.start();
        }
    }
}

