package com.wirsching.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wirsching.Resources;
import com.wirsching.captains.Player;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.ships.OakBoat;
import com.wirsching.graphics.Camera;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.Screen;
import com.wirsching.input.Keys;
import com.wirsching.math.Math;
import com.wirsching.math.Point2f;

public class GameScreen extends Screen {

	public static Camera camera;
	
	public Player player;
	
	private TextureRegion grid;
	
	public GameScreen() {
		setId("GAME");
		
		Gdx.graphics.setVSync(true);
//		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		
		Graphics.setSpriteBatch(new SpriteBatch());
		Graphics.setShapeRenderer(new ShapeRenderer());
		
		camera = new Camera();

		
		EntityHandler.addEntity(new OakBoat(0, 0));
		EntityHandler.addEntity(new OakBoat(100, 0));
		
		grid = new TextureRegion(new Texture("textures/grid.png"));
		
		player = new Player();
		
	}
	
	
	float cameraTX = 0, cameraTY = 0;
	float cameraMultiplier = 1;
	
	boolean toggle = false;
	boolean fullscreen = false;
	
	@Override
	public void update() {
		
		if (Gdx.input.isKeyPressed(Keys.CLOSE))
			Gdx.app.exit();
		
		if (Gdx.input.isKeyPressed(Keys.FULLSCREEN) && toggle) {
			toggle = false;
			
			fullscreen = !fullscreen;
			if (fullscreen) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			else Gdx.graphics.setWindowedMode(1280, 720);
			
		} else if (!Gdx.input.isKeyPressed(Keys.FULLSCREEN) && !toggle) {
			toggle = true;
		}
		
		
		
		
		
		
		
		cameraTX = player.getCurrentShip().getX();
		cameraTY = player.getCurrentShip().getY();
		
		float angle = Math.getAngle(new Point2f(camera.getX(),  camera.getY()), new Point2f(cameraTX, cameraTY));
		float distance = Math.getDistance(new Point2f(camera.getX(),  camera.getY()), new Point2f(cameraTX, cameraTY));
		
		cameraMultiplier = 3f;
		
		camera.setX((float) (camera.getX() + Math.cos(angle) * distance * cameraMultiplier * Graphics.getDelta()));
		camera.setY((float) (camera.getY() + Math.sin(angle) * distance * cameraMultiplier * Graphics.getDelta()));

		
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
			
			
			int w = (int) (camera.camera.viewportWidth / grid.getRegionWidth()) + 3;
			int h = (int) (camera.camera.viewportHeight / grid.getRegionHeight()) + 3;
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					Graphics.drawTexture(grid, (x + (int) (camera.getX() / grid.getRegionWidth()) - 2) * grid.getRegionWidth(), (y + (int) (camera.getY() / grid.getRegionHeight()) - 1) * grid.getRegionHeight());
				}
			}
			

			
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
