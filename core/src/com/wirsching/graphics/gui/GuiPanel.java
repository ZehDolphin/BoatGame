package com.wirsching.graphics.gui;

import com.wirsching.Resources;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.screens.GameScreen;
import com.wirsching.math.Point2f;

public class GuiPanel extends GuiComponent {

	
	
	public GuiPanel(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public GuiPanel() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw() {
		Graphics.drawStaticTexture(Resources.getTextureRegion("gui_panel/0:0"), getX(), getY());
	}

}
