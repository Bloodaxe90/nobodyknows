package io.github.game;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {
    
    private HashMap<String, Sound> sounds;
    private HashMap<String, Music> tracks;
    private Music currentMusic;
    public boolean musicEnabled = true;
    private float soundVolume = 1.0f;
    private float musicVolume = 1.0f;

    public AudioPlayer() {
        sounds = new HashMap<>();
        tracks = new HashMap<>();
        createTrack("bgm", "music/bgm.mp3");

    }

    public void createSound(String key, String path) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
        sounds.put(key, sound);
    }

    public void createTrack(String key, String path) {
        Music track = Gdx.audio.newMusic(Gdx.files.internal(path));
        track.setLooping(true);
        tracks.put(key, track);
    }

    public void playTrack(String key) {
        if (musicEnabled) {
            // If there's a track playing stop it first
            if (currentMusic != null && currentMusic.isPlaying()) {
                currentMusic.stop();
            }

            // Set new music
            currentMusic = tracks.get(key);
            currentMusic.setVolume(musicVolume);
            currentMusic.play();
        }
    }

    public void playSound(String key) {
        Sound sound = sounds.get(key);
        sound.play(soundVolume);
    }

    public void setMusicEnabled(boolean status) {
        musicEnabled = status;
        if (status == false) {
            currentMusic.pause();
        }
        else {
            currentMusic.play();
        }
    }

    public void setSoundVolume(float volume) {
        soundVolume = volume;
    }

    public void setMusicVolume(float volume) {
        musicVolume = volume;
    }

}
