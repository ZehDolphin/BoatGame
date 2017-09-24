package com.wirsching.entities;

import java.util.ArrayList;

import com.wirsching.entities.ships.Ship;

public class EntityHandler {


	public static int generateEntityID() {
		int answer = entities.size();
		while (!checkID(answer)) {
			answer += 1;
		}
		return answer;
	}
	
	private static boolean checkID(int id) {
		for (Entity e : entities) {
			if (id == e.id) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Every entity loaded in game.
	 */
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	/**
	 * Adds a new Entity to the list.
	 */
	public static void addEntity(Entity e) {
		entities.add(e);
	}
	
	/**
	 * Returns an entity from the list.
	 */
	public static Entity getEntity(int index) {
		return entities.get(index);
	}
	
	public static Ship getNexShip(Ship ship) {
		Ship next = null;
		int startIndex = entities.indexOf(ship);
		for (Entity e : entities) {
			if (e instanceof Ship) {
				if (entities.indexOf(e) > startIndex) {
					Ship s = (Ship) e;
					next = s;
					break;
				}
			}
		}
		if (next == null) {
			for (Entity e : entities) {
				if (e instanceof Ship) {
					Ship s = (Ship) e;
					next = s;
					break;
				}
			}
		}
		return next;
	}
	
	/**
	 * Returns a list of all entities with the same tag.
	 */
	public static ArrayList<Entity> getEntitiesByTag(Tag tag) {
		ArrayList<Entity> result = new ArrayList<Entity>();
		for (Entity e : entities)
			if (e.hasTag(tag)) result.add(e);
		return result;
	}

	public static void update() {
		for (Entity e : entities) {
			e.update();
		}
	}
	
	public static void render() {
		for (Entity e : entities) {
			e.draw();
		}
	}
	
}
