package com.wirsching.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.entities.MovableEntity;
import com.wirsching.math.Math;
import com.wirsching.math.Point2f;

/**
 * The projectile contains information about itself, like damage, weight and what type of projectile <br>
 * it is. Fire rate for example is controlled by the turret. <br>
 * 
 * @author zehdolphin
 * 
 *
 */
public class Projectile extends MovableEntity {

	/**
	 * Amount of damage points this projectile deals.
	 */
	private float damage = 1.0f;
	
	/**
	 * How much this projectile weighs.
	 * TODO - Implement projectile weight
	 */
	private float weight = 0.0f;
	
	/**
	 * How far this projectile will travel before it stops. <br>
	 * Measured in pixels. <br>
	 */
	private float range = 100.0f;
	
	/**
	 * The projectile type.
	 */
	private Type type = Type.DIRECT_CONTACT;
	
	private TextureRegion texture;
	
	private Point2f startingPoint;
	
	
	
	public Projectile(float x, float y, float angle, float force) {
		startingPoint = new Point2f(x, y);
		setPosition(x, y);
		setRotation(angle);
		setMaxSpeed(force);
	}

	@Override
	public void update() {
		super.update();
		moveForward();
		setCurrentSpeed(getMaxSpeed());
		if (Math.getDistance(startingPoint, getPosition()) > getRange()) {
			remove();
		}
	}
	
	public void setRange(float range) {
		this.range = range;
	}
	
	public float getRange() {
		return range;
	}
	
	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public TextureRegion getTexture() {
		return texture;
	}

	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}
	
	
	
	

}
