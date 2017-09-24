package com.wirsching.entities.ships;

import com.wirsching.entities.Entity;
import com.wirsching.entities.EntityHandler;
import com.wirsching.graphics.Graphics;
import com.wirsching.math.Math;

public class Ship extends Entity {

	/**
	 * Rotation relative to the world.
	 */
	public float rotation = 0.0f;

	/**
	 * Measured in degrees per second.
	 */
	public float rotationSpeed = 45.0f;

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

	public Ship(float x, float y) {
		setX(x);
		setY(y);
	}

	public Ship(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public float radius = Float.max(getWidth(), getHeight()) / 2 + 1;

	@Override
	public void update() {

		radius = Float.max(getWidth(), getHeight()) / 2 + 0;

		currentSpeed *= (55f * Graphics.getDelta());
		if (Math.abs(currentSpeed) < 0.1) {
			currentSpeed = 0;
		}
		

		setX((float) (getX() + Math.cos(rotation + 90) * currentSpeed * Graphics.getDelta()));
		setY((float) (getY() + Math.sin(rotation + 90) * currentSpeed * Graphics.getDelta()));

		
		for (Entity e : EntityHandler.entities) {
			if (!(e instanceof Ship))
				continue;
			Ship s = (Ship) e;
			if (e == this)
				continue;
			float distance = Math.getDistance(getPosition(), e.getPosition());
			if (distance > radius * 10)
				continue;

			float angle = Math.getAngle(getPosition(), e.getPosition());

			if (distance - (radius) - (s.radius) < 0) {
				e.setX((float) (e.getX() + Math.cos(angle) * (distance * 10) * Graphics.getDelta()));
				e.setY((float) (e.getY() + Math.sin(angle) * (distance * 10) * Graphics.getDelta()));

				setCurrentSpeed(getCurrentSpeed() * 0.9f);
			}
		}
	}
	
	@Override
	public void draw() {


	}

	public void setRotation(float angle) {
		rotation = angle;
	}

	public float getRotation() {
		return rotation;
	}

	public void setCurrentSpeed(float speed) {
		currentSpeed = speed;
	}

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public void setAcceleration(float acc) {
		acceleration = acc;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public void rotateRight() {
		rotation -= rotationSpeed * Graphics.getDelta();
		// if (rotation > 360) rotation = 0;
	}

	public void rotateLeft() {
		rotation += rotationSpeed * Graphics.getDelta();
		// if (rotation < 0) rotation = 360;
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
