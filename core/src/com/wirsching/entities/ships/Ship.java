package com.wirsching.entities.ships;

import com.wirsching.entities.Entity;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.MovableEntity;
import com.wirsching.entities.turrets.Turret;
import com.wirsching.math.Math;
import com.wirsching.math.Point2f;

public class Ship extends MovableEntity {

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

	public float radius = Float.max(getWidth(), getHeight()) / 2 + 1;

	@Override
	public void update() {
		super.update();

		radius = Float.max(getWidth(), getHeight()) / 2 + 0;

		

		for (Entity e : EntityHandler.entities) {
			if (!(e instanceof Ship))
				continue;
			Ship s = (Ship) e;
			if (e == this)
				continue;
			float distance = Math.getDistance(getPosition(), e.getPosition());
			if (distance > radius * 10)
				continue;

			// float angle = Math.getAngle(getPosition(), e.getPosition());

			if (distance - (radius) - (s.radius) < 0) {
				s.driftDirection = getRotation();
				s.driftSpeed = (currentSpeed * 1.3f);

				// "break" the ship
				setCurrentSpeed(getCurrentSpeed() * 0.1f);
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

	

}
