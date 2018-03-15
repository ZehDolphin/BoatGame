package com.wirsching.graphics.screens;

import static com.wirsching.graphics.gui.GuiComponent.CENTER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.wirsching.Resources;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.Screen;
import com.wirsching.graphics.gui.ButtonListener;
import com.wirsching.graphics.gui.GuiButton;
import com.wirsching.graphics.gui.GuiLabel;
import com.wirsching.graphics.gui.GuiPanel;
import com.wirsching.graphics.gui.icons.GuiClose;

public class MenuScreen extends Screen {



	GuiLabel fpsLabel = (GuiLabel) new GuiLabel(0, 0).setId("fps_label");
	GuiPanel panel = (GuiPanel) new GuiPanel(0, 0, 600, 400).setId("panel");

	GuiPanel server_menu = (GuiPanel) new GuiPanel(0, 0, 1000, 600).setId("servers");
	
	public MenuScreen() {
		setId("MENU");

		Gdx.graphics.setVSync(false);

		
		addGui(panel);
		addGui(server_menu);
		
		
		float buttonWidth = 300;
		float buttonHeight = 75;
		float buttonMargin = 10;
		
		panel.add(new GuiButton(CENTER, 40 + (buttonHeight + buttonMargin) * 2, buttonWidth, buttonHeight).setText("Multiplayer").setListener(new ButtonListener() {
			@Override
			public void clicked() {
				openServerMenu = true;
			}
		}).setId("mulitplayer_button"));
		panel.add(new GuiButton(CENTER, 40 + buttonHeight + buttonMargin, buttonWidth, buttonHeight).setText("Options").setId("options_button"));
		panel.add(new GuiButton(CENTER, 40, buttonWidth, buttonHeight).setText("Credits").setId("credits_button"));
		
		
		server_menu.add(new GuiButton(CENTER, 40 + buttonHeight + buttonMargin, buttonWidth, buttonHeight).setText("Options"));

		
		server_menu.add(new GuiClose(40, 40).setListener(new ButtonListener() {
			
			@Override
			public void clicked() {
				openServerMenu = false;
			}
		}));
		

		addGui(fpsLabel);

		
	}

	
	float serverTime = 0.0f;
	boolean openServerMenu = false;
	
	
	@Override
	public void update() {
		
		if (openServerMenu) serverTime += Graphics.getDelta() * 2;
		else serverTime -= Graphics.getDelta() * 2;
		if (serverTime >= 1) serverTime = 1;
		if (serverTime < 0) serverTime = 0;

		
		server_menu.setX(-Graphics.getWidth() + (Graphics.getWidth() * 1.5f - server_menu.getWidth() / 2) * serverTime);
		server_menu.setY(Graphics.getHeight() / 2 - server_menu.getHeight() / 2);
		
		panel.setX(Graphics.getWidth() / 2 - panel.getWidth() / 2);
		panel.setY(Graphics.getHeight() / 2 - panel.getHeight() / 2);
		
		
		getGui("panel").setDimensions(600, 400);
		
		
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
