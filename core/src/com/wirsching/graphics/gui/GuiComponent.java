package com.wirsching.graphics.gui;

import java.util.ArrayList;

import com.wirsching.math.Rectangle;

/**
 * Every GUI component extends to this class. <br>
 */
public abstract class GuiComponent extends Rectangle {

	private String id = "undefined";
	
	private float xOffset, yOffset;
	
	private ArrayList<GuiComponent> children = new ArrayList<GuiComponent>();
	
	public GuiComponent() {
	}

	public GuiComponent(float x, float y, float width, float height) {
		setPosition(x, y);
		setDimensions(width, height);
	}
	
	
	public GuiComponent add(GuiComponent child) {
		children.add(child);
		return this;
	}
	
	public GuiComponent getChild(int index) {
		return children.get(index);
	}
	
	public GuiComponent getChild(String id) {
		for (GuiComponent child : children) {
			if (child.getId().equals(id))
				return child;
		}
		return null;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public float getX() {
		return super.getX() + xOffset;
	}

	@Override
	public float getY() {
		return super.getY() + yOffset;
	}
	
	private void updateOffset(float x, float y) {
		xOffset = x;
		yOffset = y;
	}
	
	public abstract void update();
	
	public void draw() {
		for (GuiComponent g : children) {
			g.updateOffset(getX(), getY());
			g.draw();
		}
	}

}
