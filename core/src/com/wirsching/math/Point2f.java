package com.wirsching.math;

public class Point2f {
	
	private float x, y;
	
	public Point2f() {
		set(0, 0);
	}
	
	public Point2f(float x, float y) {
		set(x, y);
	}
	
	public void set(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

}
