package com.wirsching.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wirsching.graphics.Drawable;
import com.wirsching.math.Point2f;
import com.wirsching.math.Vector2f;

public class Entity implements Drawable {

	public Point2f position = new Point2f();
	public Vector2f velocity = new Vector2f();
	
	private String id = "undefined";
	
	public Entity() {
		
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void draw(SpriteBatch batch) {
		
	}
	
	
	
}
