package com.wirsching.graphics.gui;

import com.wirsching.Resources;
import com.wirsching.graphics.Graphics;

public class GuiPanel extends GuiComponent {

	
	
	public GuiPanel(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public GuiPanel() {
		
	}

	@Override
	public void draw() {
		float tileSize = Resources.getTextureRegion("gui_panel/0:0").getRegionWidth() * 4.0f;

		// Corners
		Graphics.drawStaticTexture(Resources.getTextureRegion("gui_panel/0:2"), getX(), getY(), tileSize, tileSize);
		Graphics.drawStaticTexture(Resources.getTextureRegion("gui_panel/2:0"), getX() + getWidth() - tileSize, getY() + getHeight() - tileSize, tileSize, tileSize);
		Graphics.drawStaticTexture(Resources.getTextureRegion("gui_panel/0:0"), getX(), getY() + getHeight() - tileSize, tileSize, tileSize);
		Graphics.drawStaticTexture(Resources.getTextureRegion("gui_panel/2:2"), getX() + getWidth() - tileSize, getY(), tileSize, tileSize);

		// Edges
		Graphics.drawStaticTexture(Resources.getTextureRegion("gui_panel/1:2"), getX() + tileSize, getY(), getWidth() - tileSize * 2, tileSize);
		Graphics.drawStaticTexture(Resources.getTextureRegion("gui_panel/0:1"), getX(), getY() + tileSize, tileSize, getHeight() - tileSize * 2);
		Graphics.drawStaticTexture(Resources.getTextureRegion("gui_panel/2:1"), getX() + getWidth() - tileSize, getY() + tileSize, tileSize, getHeight() - tileSize * 2);
		Graphics.drawStaticTexture(Resources.getTextureRegion("gui_panel/1:0"), getX() + tileSize, getY() + getHeight() - tileSize, getWidth() - tileSize * 2, tileSize);
	
		// Center part
		Graphics.drawStaticTexture(Resources.getTextureRegion("gui_panel/1:1"), getX() + tileSize, getY() + tileSize, getWidth() - tileSize * 2, getHeight() - tileSize * 2);

		
		super.draw();
		
	}

}
