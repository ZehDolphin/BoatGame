package com.wirsching.math;

public class Rectangle {

	private Point2f position = new Point2f();
	private float width, height;
	
	public Rectangle() {
		setPosition(0, 0);
		setDimensions(0, 0);
	}
	
	public Rectangle(float x, float y, float width, float height) {
		setPosition(x, y);
		setDimensions(width, height);
	}
	
	public Point2f getPosition() {
		return position;
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y);
	}
	
	public void setPosition(Point2f position) {
		this.position = position;
	}
	
	public void setDimensions(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void setX(float x) {
		position.setX(x);
	}
	
	public void setY(float y) {
		position.setY(y);
	}
	
	public float getX() {
		return position.getX();
	}
	
	public float getY() {
		return position.getY();
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	
}
