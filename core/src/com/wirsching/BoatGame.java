package com.wirsching;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.wirsching.graphics.ScreenManager;
import com.wirsching.graphics.screens.GameScreen;
import com.wirsching.input.Keys;
import com.wirsching.network.GameClient;

public class BoatGame extends ApplicationAdapter {

	public static GameClient client;
	
	/**
	 * How many times per second should the client sync their data to the server. <br>
	 */
	public static float syncrate = 16f;
	
	@Override
	public void create() {

		// Loads all resources.
		Resources.load();

		ScreenManager.add(new GameScreen());

		ScreenManager.setSelected("GAME");

	}

	@Override
	public void resize(int width, int height) {
		ScreenManager.resize(width, height);
	}

	boolean toggle = false;
	boolean fullscreen = false;

	@Override
	public void render() {

		if (Gdx.input.isKeyPressed(Keys.FULLSCREEN) && toggle) {
			toggle = false;

			fullscreen = !fullscreen;
			if (fullscreen)
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			else
				Gdx.graphics.setWindowedMode(1280, 720);

		} else if (!Gdx.input.isKeyPressed(Keys.FULLSCREEN) && !toggle) {
			toggle = true;
		}

		if (Gdx.input.isKeyPressed(Keys.CLOSE))
			Gdx.app.exit();

		ScreenManager.update();
		ScreenManager.render();

	}

	@Override
	public void dispose() {
		ScreenManager.dispose();
	}
}
