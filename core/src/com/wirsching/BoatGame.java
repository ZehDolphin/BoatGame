package com.wirsching;

import com.badlogic.gdx.ApplicationAdapter;
import com.wirsching.graphics.ScreenManager;
import com.wirsching.graphics.screens.GameScreen;

public class BoatGame extends ApplicationAdapter {


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

	@Override
	public void render() {
		ScreenManager.update();
		ScreenManager.render();
		
	}

	@Override
	public void dispose() {
		ScreenManager.dispose();
	}
}
