package com.wirsching.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wirsching.BoatGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 1280;
		config.height = 720;
		config.title = "Boat Game";
		config.vSyncEnabled = false;
		
		new LwjglApplication(new BoatGame(), config);
	}
}
