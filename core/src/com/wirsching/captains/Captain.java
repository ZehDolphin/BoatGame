package com.wirsching.captains;

import java.util.ArrayList;

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
	private int currentShip = 0;
	
	/**
	 * This ArrayList keeps track of the captains available ships. <br>
	 */
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	
	public Captain(String name) {
		setName(name);
	}
	
	public void addShip(Ship s) {
		ships.add(s);
	}
	
	public Ship getShip(int index) {
		return ships.get(index);
	}
	
	public int getNumberOfShips() {
		return ships.size();
	}
	
	public Ship getNextShip() {
		return (currentShip == getNumberOfShips() - 1) ? getShip(0) : getShip(currentShip + 1);
	}
	
	/**
	 * Select which ship this captain should have control over. <br>
	 */
	public void controlShip(int ship) {
		this.currentShip = ship;
	}
	
	public void controlShip(Ship ship) {
		currentShip = ships.indexOf(ship);
	}

	/**
	 * Returns the ship that this captain is currently controlling. <br>
	 */
	public Ship getCurrentShip() {
		return getShip(currentShip);
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