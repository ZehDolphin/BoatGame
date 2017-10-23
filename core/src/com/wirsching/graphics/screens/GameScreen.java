package com.wirsching.graphics.screens;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wirsching.Resources;
import com.wirsching.captains.Player;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.projectiles.ProjectileHandler;
import com.wirsching.entities.ships.MetalBoat;
import com.wirsching.entities.ships.OakBoat;
import com.wirsching.entities.turrets.StoneThrower;
import com.wirsching.graphics.Camera;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.Screen;
import com.wirsching.graphics.gui.GuiHandler;
import com.wirsching.input.Keys;
import com.wirsching.input.Mouse;
import com.wirsching.math.Math;
import com.wirsching.math.Point2f;

public class GameScreen extends Screen {

	public static Camera camera;
	
	public Player player;
	
	private TextureRegion grid;
	
	public GameScreen() {
		setId("GAME");
		
		Gdx.graphics.setVSync(true);

		
		Graphics.setSpriteBatch(new SpriteBatch());
		Graphics.setShapeRenderer(new ShapeRenderer());
		
		camera = new Camera();
		Mouse.setCurrentCamera(camera.camera);

		
		EntityHandler.addEntity(new OakBoat(0, 0).addTurret(0, new StoneThrower()));
		EntityHandler.addEntity(new OakBoat(100, 0));
		EntityHandler.addEntity(new MetalBoat(-100, 0).addTurret(0, new StoneThrower()).addTurret(1, new StoneThrower()));
		
		grid = new TextureRegion(new Texture("textures/grid.png"));
		
		player = new Player();
		
		
//		GuiHandler.addGui(new GuiPanel(100, 100, 100, 100));
		
		
	
		
		
		
		
		
		
		
		
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
			if (fullscreen) {
//				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				Gdx.graphics.setUndecorated(true);
				Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
			}
			else {
				Gdx.graphics.setUndecorated(false);
				Gdx.graphics.setWindowedMode(1280, 720);
			}
			
			
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
		ProjectileHandler.update();
		GuiHandler.update();
		
		System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond() + "\nProjectiles: " + ProjectileHandler.projectiles.size());
		
	}

	
	@Override
	public void render() {
		Gdx.gl.glClearColor(124f / 255f, 166f / 255f, 255f / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Graphics.setProjectionMatrix(camera.getProjectionMatrix());
		
		
		
		Graphics.begin();
		{
			
			
			int w = (int) (camera.camera.viewportWidth / grid.getRegionWidth()) + 5;
			int h = (int) (camera.camera.viewportHeight / grid.getRegionHeight()) + 5;
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					Graphics.drawTexture(grid, (x + (int) (camera.getX() / grid.getRegionWidth()) - 3) * grid.getRegionWidth(), (y + (int) (camera.getY() / grid.getRegionHeight()) - 2) * grid.getRegionHeight());
				}
			}
			

			
			EntityHandler.render();
			ProjectileHandler.render();
			GuiHandler.draw();
			
			
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
