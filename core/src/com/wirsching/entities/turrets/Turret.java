package com.wirsching.entities.turrets;

import com.wirsching.entities.Entity;
import com.wirsching.entities.ships.Ship;

public class Turret extends Entity {

	private Ship parent;
	
	public Turret() {

	}
	
	public Turret(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public Turret(Ship s, float x, float y) {
		parent = s;
		setPosition(x, y);
	}
	
	@Override
	public void update() {

	}
	
	@Override
	public void draw() {

	}
	
	public Turret setParent(Ship s) {
		parent = s;
		return this;
	}
	
	public Ship getParent() {
		return parent;
	}

}
