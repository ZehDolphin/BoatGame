package com.wirsching.graphics.gui;

import com.badlogic.gdx.graphics.Color;
import com.wirsching.graphics.Graphics;

public class GuiLabel extends GuiComponent {

	private String text = "";

	private Font font = Fonts.ARIAL;

	private boolean centered = false;


	public GuiLabel(float x, float y) {
		super(x, y, 0, 0);
		
		

	}

	public GuiLabel centerText() {
		centered = true;
		return this;
	}
	
	public GuiLabel setScale(float scale) {
		font.setScale(scale);
		return this;
	}
	
	public GuiLabel setText(String text) {
		this.text = text;
		return this;
	}

	public String getText() {
		return text;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw() {
		
		font = Fonts.ARIAL;
		
		font.setColor(Color.BLACK);
		
		Graphics.getGuiSpriteBatch().setShader(font.getShader());
		setWidth(font.getWidth(getText()));
		setHeight(font.getHeight(getText()));

		if (!centered)font.draw(Graphics.getGuiSpriteBatch(), getText(), getX(), getY() + getHeight());
		else font.draw(Graphics.getGuiSpriteBatch(), getText(), getX() - getWidth() / 2, getY() + getHeight());
		Graphics.getGuiSpriteBatch().setShader(null);
	}

}
