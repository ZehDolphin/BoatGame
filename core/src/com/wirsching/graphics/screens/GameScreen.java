package com.wirsching.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wirsching.Resources;
import com.wirsching.captains.Player;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.ships.OakBoat;
import com.wirsching.graphics.Camera;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.Screen;

public class GameScreen extends Screen {

	public static Camera camera;
	
	public Player player;
	
	public GameScreen() {
		setId("GAME");
		
		Gdx.graphics.setVSync(true);
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		
		Graphics.setSpriteBatch(new SpriteBatch());

		camera = new Camera();

		
		EntityHandler.addEntity(new OakBoat(0, 0));
		
		
		player = new Player();
		
	}
	
	@Override
	public void update() {
		player.update();
		EntityHandler.update();
	}

	
	@Override
	public void render() {
		Gdx.gl.glClearColor(124f / 255f, 166f / 255f, 255f / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Graphics.setProjectionMatrix(camera.getProjectionMatrix());
		
		Graphics.begin();
		{
			
			EntityHandler.render();
			
			
		}
		Graphics.end();
	}
	
	@Override
	public void resize(int width, int height) {
		camera.resize(width, height);
	}
	
	@Override
	public void dispose() {
		Graphics.dispose();
		Resources.dispose();

	}

}
