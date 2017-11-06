package com.wirsching.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pontus.server.Server;

public class ServerLauncher {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 500;
		config.height = 500;
		config.title = "Boat Game - Server";
		
		new LwjglApplication(new Server(), config);
	}

}
