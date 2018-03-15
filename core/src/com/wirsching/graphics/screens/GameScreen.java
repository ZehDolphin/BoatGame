package com.wirsching.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.BoatGame;
import com.wirsching.Resources;
import com.wirsching.captains.Player;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.ships.Ship;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.Screen;
import com.wirsching.graphics.gui.GuiLabel;
import com.wirsching.network.GameClient;
import com.wirsching.network.packets.SyncPlayer;

public class GameScreen extends Screen {

	public static Player player;

	private TextureRegion grid;

	GuiLabel fpsLabel = new GuiLabel(0, 0);

	public GameScreen() {
		setId("GAME");

		Gdx.graphics.setVSync(false);

		// Request the user's name.
//		String input = (String) JOptionPane.showInputDialog(null, "Name:", "Connect to chat server",
//				JOptionPane.QUESTION_MESSAGE, null, null, "ZehDolphin");
//		if (input == null || input.trim().length() == 0)
//			System.exit(1);
//		name = input.trim();
		
		name = "ZehDolphin";

		// Pixmap pm = new Pixmap(Gdx.files.internal("textures/cursor.png"));
		// Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 32, 32));
		// pm.dispose();

		// Load grid texture.
		grid = new TextureRegion(new Texture("textures/grid.png"));

		// Create the local player.
		player = new Player(name);


		addGui(fpsLabel);

		
		// Start the client.
		BoatGame.client = new GameClient();
	}


	public static Player getPlayer() {
		return player;
	}
	

	public static String name = "undefined";

	
	private float synctime = 0;
	
	@Override
	public void update() {
		
		// Updates camera movement.
		camera.update();
		
		// Updates the local players actions.
		player.update();
		
		// Updates all entities localy.
		EntityHandler.update();
		
		fpsLabel.setY(Graphics.getHeight() - fpsLabel.getHeight() - 3);
		fpsLabel.setScale(0.8f);
		fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());

		
		
		if (player.getCurrentShip() != null) camera.setTarget(player.getCurrentShip().getPosition());
		
		
		BoatGame.syncrate = 60f / getPlayer().getShips().size();
		
		synctime += Graphics.getDelta();
		if (synctime > 1f / BoatGame.syncrate) {
			synctime = 0;
			
			for (Ship s : getPlayer().getShips()) {
				BoatGame.client.sendPacket(new SyncPlayer(s));
			}
			
			
		}		

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
		}
		Graphics.end();


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
