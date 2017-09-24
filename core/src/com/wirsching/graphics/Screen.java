package com.wirsching.graphics;

/**
 * Each different screen.
 *
 */
public abstract class Screen {

	private String id = "undefined";
	
	public Screen() {
		
	}
	
	public String getId() {
		return id;
	}
	
	protected void setId(String id) {
		this.id = id;
	}
	
	public abstract void update();
	
	public abstract void render();
	
	public abstract void dispose();
	
	public void resize(int width, int height) {
		
	}
	
}
