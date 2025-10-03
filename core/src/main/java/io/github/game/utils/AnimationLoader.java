package io.github.game.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public final class AnimationLoader {

    private AnimationLoader() {}

    public static Animation<TextureRegion> getAnimation(String name, float duration, TextureAtlas atlas, Animation.PlayMode playMode) {
        //  NOTE: each image in the atlas for that animation should have a number at the end of
        //  its file name indication its order in the animation (starting from frame 1)

        Array<TextureRegion> frames = new Array<>();
        int frameIndex = 0;

        while (true) {
            TextureRegion currentFrame = atlas.findRegion(name + (frameIndex + 1));

            if (currentFrame == null) {
                break;
            }

            frames.add(currentFrame);
            frameIndex++;
        }

        return new Animation<>(duration, frames, playMode);
    }
}

