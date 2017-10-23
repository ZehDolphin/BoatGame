package com.wirsching.entities;

import java.util.ArrayList;

import com.wirsching.graphics.Drawable;
import com.wirsching.graphics.Graphics;
import com.wirsching.math.Math;
import com.wirsching.math.Rectangle;

public class Entity extends Rectangle implements Drawable {


	/**
	 * ArrayList of tags that can be used for sorting/dividing entities in different groups. <br>
	 */
	public ArrayList<Tag> tags = new ArrayList<Tag>();
	
	/**
	 * A unique identifier for each entity.
	 */
	public int id = EntityHandler.generateEntityID();
	
	
	/**
	 * Rotation measured in degrees relative to the world.
	 */
	private float rotation = 360f * 10000f;
	
	/**
	 * Measured in degrees per second.
	 */
	private float rotationSpeed = 180.0f;
	
	/**
	 * If this is set to true, the entity will be removed. <br>
	 */
	private boolean delete = false;
	
	public Entity() {
	}

	public Entity(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	/**
	 * Remove this entity before the next frame starts. <br>
	 */
	public void remove() {
		delete = true;
	}
	
	public boolean shouldRemove() {
		return delete;
	}
	
	/**
	 * Sets the rotation and adds 3600000 deg to it.
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation + 360 * 10000f;
	}
	
	/**
	 * Get the current rotation.
	 */
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * Get the current rotation speed, to get the speed for one frame, multiply with delta.
	 */
	public float getRotationSpeed() {
		return rotationSpeed;
	}

	/**
	 * Set the current rotation speed.
	 * @param rotationSpeed - deg per second
	 */
	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}
	
	/**
	 * Rotate by a certain amount of degrees.
	 */
	public void rotate(float deg) {
		rotation += deg;
	}
	
	/**
	 * Rotate to the right using the rotation speed.
	 */
	public void rotateRight() {
		rotation -= rotationSpeed * Graphics.getDelta();
	}

	/**
	 * Rotate to the left using the rotation speed.
	 */
	public void rotateLeft() {
		rotation += rotationSpeed * Graphics.getDelta();
	}
	
	/**
	 * Rotate to a certain angle using the rotation speed.
	 */
	public void rotateToTarget(float targetAngle) {
		float angle = getRotation() % 360;
		
		float angleDelta = (angle - targetAngle);
		if (angleDelta > 180) angleDelta -= 360;
		if (angleDelta < -180) angleDelta += 360;

		if (angleDelta < 0)
			rotateLeft();
		if (angleDelta > 0)
			rotateRight();
		if (Math.abs(angleDelta) < getRotationSpeed() * Graphics.getDelta())
			setRotation(targetAngle);
	}
	
	/**
	 * Draws the entity.
	 */
	public void draw() {
	}
	
	/**
	 * Updates the entity.
	 */
	public void update() {
	}
	
	/**
	 * Returns true if this entity has the selected tag.
	 */
	public boolean hasTag(Tag tag) {
		return tags.contains(tag);
	}
	
	/**
	 * Adds a new tag to this entity.
	 */
	public void addTag(Tag... tags) {
		for (Tag t : tags) this.tags.add(t);
	}

	/**
	 * Returns the complete list of tags as an array.
	 */
	public Tag[] getTags() {
		Tag[] tags = new Tag[this.tags.size()];
		for (Tag t : this.tags) tags[this.tags.indexOf(t)] = t;
		return tags;
	}

	
	
	
	
}
