package com.wirsching.entities;

import java.util.ArrayList;
import java.util.Comparator;

import com.wirsching.entities.ships.Ship;

public class EntityHandler {

	public static String generateEntityID() {
		String answer = "undefined";
		while (!checkID(answer)) {
			answer += 1;
		}
		return answer;
	}

	private static boolean checkID(String id) {
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
		entities.sort(new Comparator<Entity>() {
			@Override
			public int compare(Entity o1, Entity o2) {
				return new Integer(o1.getRenderOrder()).compareTo(new Integer(o2.getRenderOrder()));
			};
		});
	}

	/**
	 * Returns an entity from the list.
	 */
	public static Entity getEntity(int index) {
		return entities.get(index);
	}

	public static Ship getNextShip(Ship ship, String player) {
		Ship next = null;
		int startIndex = entities.indexOf(ship);
		for (Entity e : getEntitiesByTag(Tag.SHIP)) {
			if (entities.indexOf(e) > startIndex) {
				Ship s = (Ship) e;
				if (s.getPlayer() == player) {
					next = s;
					break;
				}
			}
		}
		if (next == null) {
			for (Entity e : getEntitiesByTag(Tag.SHIP)) {
				Ship s = (Ship) e;
				if (s.getPlayer() == player) {
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
			if (e.hasTag(tag))
				result.add(e);
		return result;
	}

	public static void update() {
		for (int i = 0; i < entities.size(); i++) {
			if (i < entities.size()) {
				if (getEntity(i).shouldRemove()) {
					entities.remove(i);
				}
			}
		}

		for (Entity e : entities) {
			e.update();
		}
	}

	public static void render() {
		for (int i = 0; i < entities.size(); i++) {
			if (i >= entities.size()) break;
			Entity e = getEntity(i);
			e.draw();
		}
	}

}
