package com.wirsching.graphics.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.graphics.Graphics;

public class GuiIcon extends GuiButton {

	private TextureRegion texture = null;
	
	private float size = 16 * 2;
	
	public GuiIcon() {
		setDimensions(size, size);
	}
	
	public void setTexture(TextureRegion t) {
		texture = t;
	}

	public GuiIcon(float x, float y) {
		setPosition(x, y);
		setDimensions(size, size);
	}
	
	@Override
	public void draw() {
		super.update(gen);
		System.out.println(xOffset);
		Graphics.drawStaticTexture(texture, getX(), getY(), getWidth(), getHeight());
	}
	
}

