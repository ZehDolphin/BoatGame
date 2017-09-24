package com.wirsching.entities;

import java.util.ArrayList;

import com.wirsching.graphics.Drawable;
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
	
	public Entity() {
	}

	public Entity(float x, float y, float width, float height) {
		super(x, y, width, height);
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
