package com.wirsching.entities;

import com.wirsching.graphics.Graphics;
import com.wirsching.math.Math;

public class MovableEntity extends Entity {

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
	
	/**
	 * How much the entity should be slowed down every second.
	 */
	protected float drag = 200f;


	public MovableEntity() {
		
	}
	
	public MovableEntity(float x, float y, float width, float height) {
	
	}
	
	@Override
	public void update() {
		if (currentSpeed > 2)
			currentSpeed = currentSpeed - (drag) * Graphics.getDelta();
		else if (currentSpeed < -2)
			currentSpeed = currentSpeed + (drag) * Graphics.getDelta();
		else
			currentSpeed = 0;

		System.out.println(currentSpeed);
		
		

		driftSpeed *= (57f * Graphics.getDelta());
		if (Math.abs(driftSpeed) < 0.1) {
			driftSpeed = 0;
		}

		setX((float) (getX() + Math.cos(getRotation() + 90) * currentSpeed * Graphics.getDelta()
				+ Math.cos(driftDirection + 90) * driftSpeed * Graphics.getDelta()));
		setY((float) (getY() + Math.sin(getRotation() + 90) * currentSpeed * Graphics.getDelta()
				+ Math.sin(driftDirection + 90) * driftSpeed * Graphics.getDelta()));
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
	
	public float getMaxSpeed() {
		return maxSpeed;
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
