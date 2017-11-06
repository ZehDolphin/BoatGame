package com.wirsching;

import com.badlogic.gdx.ApplicationAdapter;
import com.wirsching.graphics.ScreenManager;
import com.wirsching.graphics.screens.GameScreen;
import com.wirsching.network.Client;

public class BoatGame extends ApplicationAdapter {

	public static Client client;

	@Override
	public void create() {
		
		client = new Client();
		
		client.connectTo("192.168.0.16", 4321);

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
