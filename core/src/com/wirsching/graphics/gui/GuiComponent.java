package com.wirsching.graphics.gui;

import com.wirsching.math.Rectangle;

/**
 * Every GUI component extends to this class. <br>
 */
public abstract class GuiComponent extends Rectangle {

	private String id = "undefined";
	
	public GuiComponent() {
	}

	public GuiComponent(float x, float y, float width, float height) {
		setPosition(x, y);
		setDimensions(width, height);
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public abstract void update();
	public abstract void draw();

}
