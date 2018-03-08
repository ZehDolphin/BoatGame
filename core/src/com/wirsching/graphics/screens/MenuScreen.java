package com.wirsching.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.wirsching.Resources;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.Screen;
import com.wirsching.graphics.gui.GuiButton;
import com.wirsching.graphics.gui.GuiLabel;
import com.wirsching.graphics.gui.GuiPanel;

public class MenuScreen extends Screen {



	GuiLabel fpsLabel = new GuiLabel(0, 0);

	public MenuScreen() {
		setId("MENU");

		Gdx.graphics.setVSync(false);

		
		addGui(new GuiPanel(400, 200, 400, 400).setId("panel"));
		
		getGui("panel").add(new GuiButton(200, 200, 200, 75).setText("Test").fitToText());
		


		addGui(new GuiLabel(100, 170).setText("Hello World!").centerText().setScale(0.8f));

		addGui(fpsLabel);

		
	}


	
	@Override
	public void update() {
		
		fpsLabel.setY(Graphics.getHeight() - fpsLabel.getHeight() - 3);
		fpsLabel.setScale(0.8f);
		fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());

		
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(124f / 255f, 166f / 255f, 255f / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}

	@Override
	public void resize(int width, int height) {
		guiCamera.resize(width, height);
	}

	@Override
	public void dispose() {
		Graphics.dispose();
		Resources.dispose();
	}

}
