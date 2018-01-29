package com.wirsching.entities.ships;

import java.util.ArrayList;

import com.wirsching.entities.Entity;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.MovableEntity;
import com.wirsching.entities.Tag;
import com.wirsching.entities.turrets.Turret;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.screens.GameScreen;
import com.wirsching.math.Math;
import com.wirsching.math.Point2f;

public class Ship extends MovableEntity {
	
	{
		addTag(Tag.SHIP);
		setRenderOrder(0);
	}
	
	public Point2f targetPosition = new Point2f();
	public float targetRotation = getRotation();
	
	/**
	 * Name of the current player that owns this ship. <br>
	 */
	public String player = "undefined";
	
	public Ship setPlayer(String player) {
		this.player = player;
		return  this;
	}
	
	public String getPlayer() {
		return player;
	}
	
	private float health = 100.0f;
	
	public String toString() {
		
		String s = "{";
		
		s += "'class': '" + getClass().getName()  + "', ";
		s += "'position': {'x': '"+getX()+"', 'y': '"+getY()+"'}, ";
		s += "'rotation': '" + getRotation()  + "', ";
		s += "'health': '" + getHealth()  + "', ";
		s += "'player': '" + getPlayer()  + "', ";
		s += "'id': '" + getID()  + "', ";
		s += "'upgrade_slots': [";
		
		for (int i = 0; i < getSlots(); i++) {
			if (slots[i].turret == null) continue;
			s += "{";
			s += "'class': '"+slots[i].turret.getClass().getName()+"'";
			s += "}";
			if (i != getSlots() - 1) s += ", ";
		}
		
		s += "], ";
		
		s += "'tags': [";
		
		for (int i = 0; i < getTags().length; i++) {
			if (getTags()[i] == null) break;
			s += "'" + getTags()[i].name() + "'";
			if (i != getTags().length - 1) s += ", ";
		}
		
		s += "]";

		
		s += "}";
		
		return s;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
	
	public float getHealth() {
		return health;
	}
	
	public void damage(float amount) {
		health -= amount;
	}

	public UpgradeSlot[] slots = new UpgradeSlot[0];

	public void setUpgradeSlots(UpgradeSlot... slots) {
		this.slots = slots;
	}

	public int getSlots() {
		return slots.length;
	}

	public Ship addTurret(int slotIndex, Turret t) {
		slots[slotIndex].setTurret(t);
		t.setX(slots[slotIndex].x);
		t.setY(slots[slotIndex].y);
		t.setParent(this);
		return this;
	}

	public Turret getTurret(int slotIndex) {
		return slots[slotIndex].getTurret();
	}

	
	
	public Ship(float x, float y) {
		setX(x);
		setY(y);
	}

	public Ship(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	/**
	 * Converts the "ship-relative" coordinates to world coordinates. <br>
	 * Used for turrets and other add-ons. <br>
	 */
	public Point2f getWorldCoordinates(Point2f p) {
		float angle = getRotation() + 90;
		float ox = getX();
		float oy = getY();

		float x = ox + Math.cos(angle) * p.getY() - Math.sin(-angle) * p.getX();
		float y = oy + Math.sin(angle) * p.getY() - Math.cos(-angle) * p.getX();
		return new Point2f(x, y);
	}

	private float radius = Float.max(getWidth(), getHeight()) / 2 + 1;
	
	public float getRadius() {
		return radius;
	}
	
	public float getHitRadius() {
		return getRadius() * 0.80f;
	}
	
	private ArrayList<Ship> collidedWith = new ArrayList<Ship>();
	
	@Override
	public void update() {
		super.update();
		
		
		if (!GameScreen.getPlayer().hasShip(this)) {

			rotateToTarget(getTargetRotation() % 360 + 90);
			
			
			float xx = getTargetPosition().getX();
			float yy = getTargetPosition().getY();
			
			float m = 16;
			
			if (xx < getX()) setX(getX() - Math.abs(xx - getX()) * Graphics.getDelta() * m);
			if (xx > getX()) setX(getX() + Math.abs(xx - getX()) * Graphics.getDelta() * m);
			if (yy < getY()) setY(getY() - Math.abs(yy - getY()) * Graphics.getDelta() * m);
			if (yy > getY()) setY(getY() + Math.abs(yy - getY()) * Graphics.getDelta() * m);
			
			
		}
		

		radius = Float.max(getWidth(), getHeight()) / 2 + 0;

		// Remove the Ship if it's health is below or equal to zero.
		if (getHealth() <= 0) remove();

		for (Entity e : EntityHandler.entities) {
			if (!(e instanceof Ship))
				continue;
			Ship s = (Ship) e;
			if (e == this)
				continue;
			float distance = Math.getDistance(getPosition(), e.getPosition());
			if (distance > radius * 2 + s.radius * 2)
				continue;

			 float angle = Math.getAngle(getPosition(), e.getPosition());

			if (distance - (radius) - (s.radius) < 0) {

				// "break" the ship
				
//				setCurrentSpeed(-getCurrentSpeed());
				setX((float) (getX() + Math.cos(angle - 180) * -(distance - (radius) - (s.radius))));
				setY((float) (getY() + Math.sin(angle - 180) * -(distance - (radius) - (s.radius))));
				
			}
		}

		for (UpgradeSlot s : slots) {
			if (s.getTurret() != null)
				s.getTurret().update();
		}
	}

	@Override
	public void draw() {
		for (UpgradeSlot s : slots) {
			if (s.getTurret() != null)
				s.getTurret().draw();
		}

	}

	public Point2f getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(float x, float y) {
		targetPosition.set(x, y);
	}
	
	public void setTargetPosition(Point2f targetPosition) {
		this.targetPosition = targetPosition;
	}

	public float getTargetRotation() {
		return targetRotation;
	}

	public void setTargetRotation(float targetRotation) {
		this.targetRotation = targetRotation;
	}

	

}
