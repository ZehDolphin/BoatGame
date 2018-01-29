package com.wirsching.entities.turrets;

import java.lang.reflect.Constructor;

import com.wirsching.entities.Entity;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.projectiles.Projectile;
import com.wirsching.entities.ships.Ship;
import com.wirsching.graphics.screens.GameScreen;
import com.wirsching.math.Point2f;

public abstract class Turret extends Entity {

	private Ship parent;
	
	protected String projectile;
	
	private float throwForce = 100f;
	
	/**
	 * Shots per second
	 */
	private float firerate = 2.0f;
	
	/**
	 * When was this turret last fired, measured in milliseconds.
	 */
	protected long last_fired = 0;
	
	private float targetRotation = 0;
	
	public Turret() {}
	
	@Deprecated
	public Turret(float x, float y) {
		setX(x);
		setY(y);
	}
	
	@Deprecated
	public Turret(Ship s, float x, float y) {
		parent = s;
		setPosition(x, y);
	}

	public void fire() {
		
		long current = System.currentTimeMillis();
		if ((current - last_fired) > 1000 / getFirerate()) {
			last_fired = current;
			justFired();
			if (projectile != null) {
				Projectile p;
				Class<?> clazz;
				try {
					clazz = Class.forName("com.wirsching.entities.projectiles." + getProjectile());

					
					Constructor<?>[] c = clazz.getConstructors();
					
					
					Constructor<?> ctor = c[0];
					Point2f turretPos = getParent().getWorldCoordinates(getPosition());
					p = (Projectile) ctor.newInstance(turretPos.getX(), turretPos.getY(), getRotation(), throwForce, this);
					
					
					
					EntityHandler.addEntity(p);
				} catch (Exception e) {
					try {
						throw new CouldNotFindProjectileException(getProjectile());
					} catch (CouldNotFindProjectileException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		
	}
	
	public abstract void justFired();
	
	@Override
	public void update() {
		super.update();
		
		if (!GameScreen.getPlayer().hasShip(parent)) {

		
		rotateToTarget(getTargetRotation() % 360 + 90);
		}
	}
	
	@Override
	public void draw() {

	}

	public float getTargetRotation() {
		return targetRotation;
	}

	public void setTargetRotation(float targetRotation) {
		this.targetRotation = targetRotation;
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
