package com.wirsching.entities.projectiles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.entities.Entity;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.MovableEntity;
import com.wirsching.entities.Tag;
import com.wirsching.entities.ships.Ship;
import com.wirsching.entities.turrets.Turret;
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
	
	{
		addTag(Tag.PROJECTILE);
		setRenderOrder(1);
	}
	
	protected Turret parent = null;

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
	
	private boolean removeOnStop = false;
	
	public Projectile(float x, float y, float angle, float force, Turret parent) {
		startingPoint = new Point2f(x, y);
		setPosition(x, y);
		setRotation(angle);
		setMaxSpeed(force);
		setCurrentSpeed(force);
		this.parent = parent;
		drag = 400f;
	}

	public Turret getParent() {
		return parent;
	}
	
	public float getRadius() {
		return Float.max(getWidth(), getHeight()) / 2 + 1;
	}
	
	private ArrayList<Entity> hitList = new ArrayList<Entity>();
	
	@Override
	public void update() {
		super.update();
		moveForward();
		if ((Math.getDistance(startingPoint, getPosition()) > getRange()) || (removeOnStop && (getCurrentSpeed() < 0.1f))) {
			remove();
		}
		
		if (getType() == Type.DIRECT_CONTACT)
		for (Entity e : EntityHandler.entities) {
			if (!(e instanceof Ship)) continue;
			if (hitList.contains(e)) continue;
			Ship s = (Ship) e;
			
			if (getParent().getParent() == s) continue;
			
			float distance = Math.getDistance(getPosition(), s.getPosition());
			
			
			if (distance > s.getHitRadius() * 2) continue;
			
			
			if (distance - s.getHitRadius() < 0) {
				hitList.add(e);
				s.damage(getDamage());
				remove();
			}
			
			
		}
		
	}
	
	public void removeWhenStopped() {
		removeOnStop = true;
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
		if (type == Type.DIRECT_CONTACT)
			removeWhenStopped();
	}

	public TextureRegion getTexture() {
		return texture;
	}

	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}
	
	
	
	

}
