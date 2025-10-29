package io.github.game.utils.io;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {

    //Must only play .mp3 files
    private static HashMap<String, Sound> sounds = new HashMap<>();
    private static HashMap<String, Music> tracks = new HashMap<>();

    public static Music currentMusic;
    public static boolean musicEnabled = true;
    public static float soundVolume = 1.0f;
    public static float musicVolume = 1.0f;
    public final static String PATH = "music/";

    private static void addSound(String key) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(PATH + key + ".mp3"));
        sounds.put(key, sound);
    }

    private static void addTrack(String key) {
        Music track = Gdx.audio.newMusic(Gdx.files.internal(PATH + key + ".mp3"));
        track.setLooping(true);
        tracks.put(key, track);
    }

    public static void playTrack(String key) {
        if (!tracks.containsKey(key)) {
            addTrack(key);
        }
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

    public static void playSound(String key) {
        if (!sounds.containsKey(key)) {
            addSound(key);
        }
        Sound sound = sounds.get(key);
        sound.play(soundVolume);
    }

    public static void setMusicEnabled(boolean status) {
        musicEnabled = status;
        if (!status) {
            currentMusic.pause();
        }
        else {
            currentMusic.play();
        }
    }
}
