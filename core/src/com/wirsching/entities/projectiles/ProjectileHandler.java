package com.wirsching.entities.projectiles;

import java.util.ArrayList;

public class ProjectileHandler {

	/**
	 * Every Projectile loaded in game.
	 */
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	/**
	 * Adds a new Projectile to the list.
	 */
	public static void addProjectile(Projectile e) {
		projectiles.add(e);
	}
	
	/**
	 * Returns an Projectile from the list.
	 */
	public static Projectile getProjectile(int index) {
		return projectiles.get(index);
	}
	
	public static void update() {
		
		for (int i = 0; i < projectiles.size(); i++) {
			if (i < projectiles.size()) {
				if (getProjectile(i).shouldRemove()) {
					projectiles.remove(i);
				}
			}
		}
		
		for (Projectile e : projectiles) {
			e.update();
		}
	}
	
	public static void render() {
		for (Projectile e : projectiles) {
			e.draw();
		}
	}
	

}
