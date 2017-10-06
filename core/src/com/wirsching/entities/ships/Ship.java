package com.wirsching.entities.ships;

import com.wirsching.entities.Entity;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.turrets.Turret;
import com.wirsching.graphics.Graphics;
import com.wirsching.math.Math;
import com.wirsching.math.Point2f;

public class Ship extends Entity {

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

	/**
	 * Measured in pixels per second.
	 */
	public float maxSpeed = 20.0f;

	/**
	 * Measured in pixels per second per second.
	 */
	public float acceleration = 1.0f;

	/**
	 * Measured in pixels per second.
	 */
	public float currentSpeed = 0.0f;

	/**
	 * The direction of which this ship is drifting.
	 */
	public float driftDirection = 0.0f;

	/**
	 * The drift speed.
	 */
	public float driftSpeed = 0.0f;

	public Ship(float x, float y) {
		setX(x);
		setY(y);
	}

	public Ship(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

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

		radius = Float.max(getWidth(), getHeight()) / 2 + 0;

		currentSpeed *= (55f * Graphics.getDelta());
		if (Math.abs(currentSpeed) < 0.1) {
			currentSpeed = 0;
		}

		driftSpeed *= (57f * Graphics.getDelta());
		if (Math.abs(driftSpeed) < 0.1) {
			driftSpeed = 0;
		}

		setX((float) (getX() + Math.cos(getRotation() + 90) * currentSpeed * Graphics.getDelta()
				+ Math.cos(driftDirection + 90) * driftSpeed * Graphics.getDelta()));
		setY((float) (getY() + Math.sin(getRotation() + 90) * currentSpeed * Graphics.getDelta()
				+ Math.sin(driftDirection + 90) * driftSpeed * Graphics.getDelta()));

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

	public void setCurrentSpeed(float speed) {
		currentSpeed = speed;
	}

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public void setAcceleration(float acc) {
		acceleration = acc;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public void moveForward() {
		if (currentSpeed <= maxSpeed) {
			currentSpeed += acceleration * Graphics.getDelta();
		} else {
			currentSpeed = maxSpeed;
		}
	}

	public void moveBackward() {
		if (currentSpeed >= -maxSpeed) {
			currentSpeed -= acceleration * Graphics.getDelta();
		} else {
			currentSpeed = -maxSpeed;
		}
	}

}
