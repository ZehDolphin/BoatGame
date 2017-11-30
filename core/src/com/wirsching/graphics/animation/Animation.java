package com.wirsching.graphics.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.SpriteSheet;

import static com.wirsching.graphics.animation.PlayState.*;

/**
 * Animation class to handle fram by frame animation. <br>
 * 
 * @author zehdolphin
 *
 */
public class Animation {

	TextureRegion[] sprites = null;

	/**
	 * How many frames should be displayed each second. <br>
	 */
	private float fps = 24.0f;

	/**
	 * The current frame.
	 */
	private int currentFrame = 0;

	private float time = 0.0f;

	private PlayState playState = STOP;

	public Animation(SpriteSheet sheet) {
		sprites = sheet.getAsArray();
	}
	
	/**
	 * Set the current frame to 0 which will reset the animation. <br>
	 */
	public void reset() {
		currentFrame = 0;
	}
	
	/**
	 * Stop the animation from playing, note that this won't reset the animation to its starting position. <br>
	 */
	public void stop() {
		playState = STOP;
	}
	
	/**
	 * Start playing the animation from the current position. <br>
	 * The animation will play until stopped. <br>
	 */
	public void play() {
		playState = LOOP;
	}
	
	/**
	 * Play the animation through from the beginning to end. <br>
	 */
	public void playOnce() {
		reset();
		playState = PLAY;
	}
	
	/**
	 * Return the current frame. <br>
	 */
	public TextureRegion getCurrentFrame(boolean... b) {
		if (b.length <= 0) update();
		return sprites[currentFrame];
	}
	
	/**
	 * Set the FPS. <br>
	 */
	public Animation setFPS(float fps) {
		this.fps = fps;
		return this;
	}
	
	public void update() {
		if (playState == LOOP) {
			time += Graphics.getDelta();
			if (time >= 1f / fps) {
				time = 0;
				currentFrame++;
				if (currentFrame >= sprites.length) {
					currentFrame = 0;
				}
			}
		}
		
		if (playState == PLAY) {
			time += Graphics.getDelta();
			if (time >= 1f / fps) {
				time = 0;
				currentFrame++;
				if (currentFrame >= sprites.length) {
					currentFrame = 0;
					playState = STOP;
				}
			}
		}
	}

	public void draw(float x, float y, float rotation) {
		Graphics.drawTexture(sprites[currentFrame], x, y, rotation);
	}

}
