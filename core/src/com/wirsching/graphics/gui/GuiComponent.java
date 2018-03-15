package com.wirsching.graphics.gui;

import java.util.ArrayList;

import com.wirsching.graphics.Graphics;
import com.wirsching.input.Mouse;
import com.wirsching.math.Rectangle;

/**
 * Every GUI component extends to this class. <br>
 */
public class GuiComponent extends Rectangle {

	public static final float CENTER = Integer.MIN_VALUE * 0 + 20f;
	
	public GuiComponent parent = null;
	
	private String id = "undefined";

	protected float xOffset, yOffset;
	
	boolean hover = false;

	private ArrayList<GuiComponent> children = new ArrayList<GuiComponent>();

	public GuiComponent() {
	}

	public GuiComponent(float x, float y, float width, float height) {
		setPosition(x, y);
		setDimensions(width, height);
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
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
	
	public GuiComponent getParent() {
		return parent;
	}
	
	public GuiComponent searchChild(String id) {
		for (GuiComponent child : children) {
			if (child.getId().equals(id))
				return child;
			else
				return child.searchChild(id);
		}
		return null;
	}

	public GuiComponent setId(String id) {
		this.id = id;
		return this;
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

	private void setParent(GuiComponent g) {
		parent = g;
	}
	
	private void updateOffset(float x, float y) {
		xOffset = x;
		yOffset = y;
	}

	int gen = 0;
	
	public void update(int gen) {
		gen++;
		this.gen = gen;
		
		if (gen != 0) {
			for (int i = 0; i < gen; i++) System.out.print("\t\t");
			System.out.println("|");
			
			for (int i = 0; i < gen *2; i++) System.out.print("\t");
			System.out.println("-------> '" + getId() + "'");
			for (int i = 0; i < gen *2; i++) System.out.print("\t");
			System.out.println("         " + getClass());
		} else {
			System.out.println("\n\n'" + getId() + "'");
			System.out.println(getClass());
		}
		

		
		if (contains(Mouse.getGUIPosition()))
			hover = true;
		else
			hover = false;
		
//		if (getParent() == null) {
//			if (originalX == CENTER) setX(Graphics.getWidth() / 2 - getWidth() / 2);
//			if (originalY == CENTER) setY(Graphics.getHeight() / 2 - getHeight() / 2);
//		}
		
		for (GuiComponent g : children) {
			
//			if (g.originalX == CENTER) g.setX(getWidth() / 2 - g.getWidth() / 2);
//			if (g.originalY == CENTER) g.setY(getHeight() / 2 - g.getHeight() / 2);
			
			g.updateOffset(getX(), getY());
			g.setParent(this);
			g.update(this.gen);
			
		}
		

	}

	public void draw() {
		
		
		
		
		for (GuiComponent g : children) {
			g.updateOffset(getX(), getY());

			
			g.draw();
		}
	}

}
