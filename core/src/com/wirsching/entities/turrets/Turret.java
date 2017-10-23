package com.wirsching.entities.turrets;

import java.lang.reflect.Constructor;

import com.wirsching.entities.Entity;
import com.wirsching.entities.projectiles.Projectile;
import com.wirsching.entities.projectiles.ProjectileHandler;
import com.wirsching.entities.ships.Ship;
import com.wirsching.math.Point2f;

public class Turret extends Entity {

	private Ship parent;
	
	private String projectile;
	
	private float throwForce = 100f;
	
	/**
	 * Shots per second
	 */
	private float firerate = 2.0f;
	
	/**
	 * When was this turret last fired, measured in milliseconds.
	 */
	private long last_fired = 0;
	
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

	public void fire() {
		
		long current = System.currentTimeMillis();
		if ((current - last_fired) > 1000 / getFirerate()) {
			last_fired = current;
			if (projectile != null) {
				Projectile p;
				Class<?> clazz;
				try {
					clazz = Class.forName("com.wirsching.entities.projectiles." + getProjectile());

					
					Constructor<?>[] c = clazz.getConstructors();
					
					
					Constructor<?> ctor = c[0];
					Point2f turretPos = getParent().getWorldCoordinates(getPosition());
					p = (Projectile) ctor.newInstance(turretPos.getX(), turretPos.getY(), getRotation() - 90, throwForce);
					
					
					
					ProjectileHandler.addProjectile(p);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	@Override
	public void update() {

	}
	
	@Override
	public void draw() {

	}
	
	public void setThrowForce(float throwForce) {
		this.throwForce = throwForce;
	}
	
	public float getThrowForce() {
		return throwForce;
	}
	
	public void setFirerate(float firerate) {
		this.firerate = firerate;
	}
	
	public float getFirerate() {
		return firerate;
	}
	
	public Turret setProjectile(String p) {
		projectile = p;
		return this;
	}
	
	public String getProjectile() {
		return projectile;
	}
	
	public Turret setParent(Ship s) {
		parent = s;
		return this;
	}
	
	public Ship getParent() {
		return parent;
	}

}
