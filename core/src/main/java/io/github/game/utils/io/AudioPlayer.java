package io.github.game.utils.io;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/** 
 * A class used to play sound effects and music
 * 
 */
public class AudioPlayer {

    //Must only play .mp3 files
    private static HashMap<String, Sound> sounds = new HashMap<>();
    private static HashMap<String, Music> tracks = new HashMap<>();

    public static Music currentMusic;
    private static boolean musicEnabled = true;
    public final static String MUSIC_PATH = "music/";
    public final static String SFX_PATH = "sfx/";


    /**
     * Adds a sound to the sounds hashmap
     * 
     * This function takes a string, and uses it as the key, and the value as the
     * file path to the corresponding mp3 file, and adds it to the sounds hashmap.
     * 
     * @param key The name of the sound file to play (without extensions or path)
     */
    private static void addSound(String key) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(SFX_PATH + key + ".mp3"));
        sounds.put(key, sound);
    }

    /**
     * Adds a music track to the tracks hashmap
     * 
     * Takes a string, and uses it as the key, and the value as the file path to
     * the corresponding mp3 file, and adds it to the tracks hashmap.
     * 
     * @param key The name of the music file to play (without extensions or path)
     */
    private static void addTrack(String key) {
        Music track = Gdx.audio.newMusic(Gdx.files.internal(MUSIC_PATH + key + ".mp3"));
        track.setLooping(true);
        tracks.put(key, track);
    }

    /**
     * Plays a music track
     * 
     * Plays a music track, stopping any currently playing tracks first.
     * 
     * @param key The name of the music file to play (without extensions or path)
     * @param volume The volume to play the music at from 0-1
     */
    public static void playTrack(String key, float volume) {
        // Add the track to the track list if it's not in it
        if (!tracks.containsKey(key)) {
            addTrack(key);
        }
        if (musicEnabled) {
            // If there's a track playing stop it first
            if (currentMusic != null && currentMusic.isPlaying()) {
                currentMusic.stop();
            }
            // Play the new music track
            currentMusic = tracks.get(key);
            currentMusic.setVolume(volume);
            currentMusic.play();
        }
    }


    /**
     * Plays a sound
     * 
     * @param key The name of the sound file to play (without extensions or path)
     * @param volume The volume from 0-1 to play the sound
     * @param pitch The pitch to play the sound at (1 being default)
     */
    public static void playSound(String key, float volume, float pitch) {
        // Add the sound to the sounds list if it's not in it
       if (!sounds.containsKey(key)) {
           addSound(key);
       }
       // Play the sound
       Sound sound = sounds.get(key);
       sound.play(volume, pitch, 1.0f);
   }

   /**
    * Plays a sound at the default pitch
    *
    * @param key The name of the sound file to play (without extensions or path)
    * @param volume The volume from 0-1 to play the sound
    */
   public static void playSound(String key, float volume) {
        playSound(key, volume, 1f);
    }

    /**
     * Pauses or plays the current music track
     * 
     * @param status A boolean representing music enabled (true) or disabled (false)
     */
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
