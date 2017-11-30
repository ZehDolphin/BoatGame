package com.wirsching.graphics.screens;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wirsching.BoatGame;
import com.wirsching.Resources;
import com.wirsching.captains.Player;
import com.wirsching.captains.SimpleBot;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.ships.MetalBoat;
import com.wirsching.entities.ships.OakBoat;
import com.wirsching.entities.ships.Ship;
import com.wirsching.entities.turrets.StoneThrower;
import com.wirsching.graphics.Camera;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.GuiCamera;
import com.wirsching.graphics.Screen;
import com.wirsching.graphics.gui.GuiHandler;
import com.wirsching.graphics.gui.GuiLabel;
import com.wirsching.graphics.gui.GuiPanel;
import com.wirsching.input.Keys;
import com.wirsching.input.Mouse;
import com.wirsching.math.Math;
import com.wirsching.math.Point2f;
import com.wirsching.network.GameClient;
import com.wirsching.network.packets.Sync;

public class GameScreen extends Screen {

	public static Camera camera;
	public static GuiCamera guiCamera;

	public Player player;

	private TextureRegion grid;

	GuiLabel fpsLabel = new GuiLabel(0, 0);

	public GameScreen() {
		setId("GAME");
		

		Gdx.graphics.setVSync(false);

		// Request the user's name.
		String input = (String) JOptionPane.showInputDialog(null, "Name:", "Connect to chat server",
				JOptionPane.QUESTION_MESSAGE, null, null, "ZehDolphin");
		if (input == null || input.trim().length() == 0)
			System.exit(1);
		name = input.trim();

		// Pixmap pm = new Pixmap(Gdx.files.internal("textures/cursor.png"));
		// Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 32, 32));
		// pm.dispose();

		Graphics.setSpriteBatch(new SpriteBatch());
		Graphics.setShapeRenderer(new ShapeRenderer());
		Graphics.setGuiSpriteBatch(new SpriteBatch());

		camera = new Camera();
		guiCamera = new GuiCamera().setVirtualWidth(1280);
		Mouse.setCurrentCamera(camera.camera);
		Mouse.setCurrentGuiCamera(guiCamera.camera);

//		EntityHandler.addEntity(new OakBoat(0, 0).addTurret(0, new StoneThrower()));
		EntityHandler.addEntity(new MetalBoat(0, 0).addTurret(0, new StoneThrower()));


		
		grid = new TextureRegion(new Texture("textures/grid.png"));

		player = new Player();


		GuiHandler.addGui(new GuiPanel(50, 50, 200, 200)
				.add(new GuiLabel(100, 170).setText("Hello World!").centerText().setScale(0.8f)));

		GuiHandler.addGui(fpsLabel);

		BoatGame.client = new GameClient();

	}

	float cameraTX = 0, cameraTY = 0;
	float cameraMultiplier = 1;

	boolean toggle = false;
	boolean fullscreen = false;

	public static String name = "undefined";

	@Override
	public void update() {

		if (Gdx.input.isKeyPressed(Keys.CLOSE))
			Gdx.app.exit();

		if (Gdx.input.isKeyPressed(Keys.FULLSCREEN) && toggle) {
			toggle = false;

			fullscreen = !fullscreen;
			if (fullscreen) {
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				// Gdx.graphics.setUndecorated(true);
				// Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width,
				// Gdx.graphics.getDisplayMode().height);
			} else {
				// Gdx.graphics.setUndecorated(false);
				Gdx.graphics.setWindowedMode(1280, 720);
			}

		} else if (!Gdx.input.isKeyPressed(Keys.FULLSCREEN) && !toggle) {
			toggle = true;
		}

		cameraTX = player.getCurrentShip().getX();
		cameraTY = player.getCurrentShip().getY();

		float angle = Math.getAngle(new Point2f(camera.getX(), camera.getY()), new Point2f(cameraTX, cameraTY));
		float distance = Math.getDistance(new Point2f(camera.getX(), camera.getY()), new Point2f(cameraTX, cameraTY));

		cameraMultiplier = 3f;

		camera.setX((float) (camera.getX() + Math.cos(angle) * distance * cameraMultiplier * Graphics.getDelta()));
		camera.setY((float) (camera.getY() + Math.sin(angle) * distance * cameraMultiplier * Graphics.getDelta()));

		player.update();
		EntityHandler.update();
		GuiHandler.update();

		fpsLabel.setY(Graphics.getHeight() - fpsLabel.getHeight() - 3);
		fpsLabel.setScale(0.8f);
		fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());

		Ship s = player.getCurrentShip();
		BoatGame.client.sendPacket(new Sync(name, s.getPosition(), s.getRotation()));

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(124f / 255f, 166f / 255f, 255f / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Graphics.setProjectionMatrix(camera.getProjectionMatrix());

		Graphics.begin();
		{
			Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

			int w = (int) (camera.camera.viewportWidth / grid.getRegionWidth()) + 5;
			int h = (int) (camera.camera.viewportHeight / grid.getRegionHeight()) + 5;
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					Graphics.drawTexture(grid,
							(x + (int) (camera.getX() / grid.getRegionWidth()) - 3) * grid.getRegionWidth(),
							(y + (int) (camera.getY() / grid.getRegionHeight()) - 2) * grid.getRegionHeight());
				}
			}

			EntityHandler.render();

			// Vector3 v = GameScreen.camera.screenCoords(0, 0);
			// font.draw(Graphics.getSpriteBatch(), "FPS: " +
			// Gdx.graphics.getFramesPerSecond(), v.x, v.y);

		}
		Graphics.end();

		Graphics.getGuiSpriteBatch().setProjectionMatrix(guiCamera.getProjectionMatrix());
		Graphics.getGuiSpriteBatch().begin();
		{
			GuiHandler.draw();
		}
		Graphics.getGuiSpriteBatch().end();

	}

	@Override
	public void resize(int width, int height) {
		camera.resize(width, height);
		guiCamera.resize(width, height);
	}

	@Override
	public void dispose() {
		Graphics.dispose();
		Resources.dispose();
		BoatGame.client.disconnect();
	}

}
