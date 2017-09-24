package com.wirsching.captains;

import com.wirsching.entities.ships.Ship;

/**
 * A captain can be in control over a ship, for now the only captain is the player. <br>
 * Later on the AI and NPCs will also be created as captains. <br>
 *
 */
public class Captain {

	/**
	 * This captains name. <br>
	 */
	private String name = "unnamed";

	/**
	 * The ship that this captain is currently in control over. <br>
	 */
	private Ship currentShip = null;
	
	public Captain(String name) {
		setName(name);
	}
	
	/**
	 * Select which ship this captain should have control over. <br>
	 */
	public void controlShip(Ship ship) {
		this.currentShip = ship;
	}

	/**
	 * Returns the ship that this captain is currently controlling. <br>
	 */
	public Ship getCurrentShip() {
		return currentShip;
	}
	
	/**
	 * Set the name of this captain. <br>
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the name of this captain. <br>
	 */
	public String getName() {
		return name;
	}

}