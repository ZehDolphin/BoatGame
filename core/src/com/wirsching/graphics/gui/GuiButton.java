package com.wirsching.graphics.gui;

import com.wirsching.Resources;
import com.wirsching.graphics.Graphics;

public class GuiButton extends GuiComponent {

	private GuiLabel text;
	
	public GuiButton(float x, float y, float width, float height) {
		super(x, y, width, height);
		text = new GuiLabel(width / 2, height / 2).centerText().centerVertical();
		add(text);
	}
	
	public GuiButton() {
		
	}

	public GuiButton setText(String t) {
		text.setText(t);
		return this;
	}
	
	public GuiButton fitToText() {
		System.out.println(text.getWidth() + ":" + text.getHeight());
		setWidth(text.getWidth() + 40);
		setHeight(text.getHeight() + 40);
		text.setPosition(getWidth() / 2, getHeight() / 2);
		return this;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void draw() {
		
		String gui = hover ? "gui_button_hover" : "gui_button";
		
		
		float tileSize = Resources.getTextureRegion(gui + "/0:0").getRegionWidth() * 4.0f;

		// Corners
		Graphics.drawStaticTexture(Resources.getTextureRegion(gui + "/0:2"), getX(), getY(), tileSize, tileSize);
		Graphics.drawStaticTexture(Resources.getTextureRegion(gui + "/2:0"), getX() + getWidth() - tileSize, getY() + getHeight() - tileSize, tileSize, tileSize);
		Graphics.drawStaticTexture(Resources.getTextureRegion(gui + "/0:0"), getX(), getY() + getHeight() - tileSize, tileSize, tileSize);
		Graphics.drawStaticTexture(Resources.getTextureRegion(gui + "/2:2"), getX() + getWidth() - tileSize, getY(), tileSize, tileSize);

		// Edges
		Graphics.drawStaticTexture(Resources.getTextureRegion(gui + "/1:2"), getX() + tileSize, getY(), getWidth() - tileSize * 2, tileSize);
		Graphics.drawStaticTexture(Resources.getTextureRegion(gui + "/0:1"), getX(), getY() + tileSize, tileSize, getHeight() - tileSize * 2);
		Graphics.drawStaticTexture(Resources.getTextureRegion(gui + "/2:1"), getX() + getWidth() - tileSize, getY() + tileSize, tileSize, getHeight() - tileSize * 2);
		Graphics.drawStaticTexture(Resources.getTextureRegion(gui + "/1:0"), getX() + tileSize, getY() + getHeight() - tileSize, getWidth() - tileSize * 2, tileSize);
	
		// Center part
		Graphics.drawStaticTexture(Resources.getTextureRegion(gui + "/1:1"), getX() + tileSize, getY() + tileSize, getWidth() - tileSize * 2, getHeight() - tileSize * 2);

		super.draw();
		
	}

}
