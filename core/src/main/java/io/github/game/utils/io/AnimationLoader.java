package io.github.game.utils.io;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Load animations from a TextureAtlas
 *
 */
public final class AnimationLoader {

    /**
     * Loads animation frames from a texture atlas
     *
     * Each image in the atlas for an animation should have
     * a number at the end of its file name indicating its frame
     * in the animation (starting from frame 1)
     *
     * @param name the base name of the animation frames
     * @param duration how long each frame lasts (seconds)
     * @param atlas the atlas where the frames are stored
     * @param playMode how the animation should play
     * @return an Animation made from the frames we found
     */
    public static Animation<TextureRegion> getAnimation(String name, float duration, TextureAtlas atlas, Animation.PlayMode playMode) {

        // Must have frames like run1, run2, run3...
        Array<TextureRegion> frames = new Array<>();
        int frameIndex = 0;

        // It keeps looking for frames until it can't find one
        while (true) {
            TextureRegion currentFrame = atlas.findRegion(name + (frameIndex + 1));

            if (currentFrame == null) {
                break;
            }

            frames.add(currentFrame);
            frameIndex++;
        }

        // Makes the animation using all the frames found
        return new Animation<>(duration, frames, playMode);
    }
}
