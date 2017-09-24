package com.wirsching.captains;

import com.badlogic.gdx.Gdx;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.ships.Ship;
import com.wirsching.input.Keys;


/**
 * The local player.
 *
 */
public class Player extends Captain {

	public Player() {
		super("player");
		
		controlShip((Ship) EntityHandler.getEntity(0));
		
	}

	boolean toggle = false;
	
	public void update() {
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT_ARROW) && toggle) {
			toggle = false;
			
			controlShip(EntityHandler.getNexShip(getCurrentShip()));
			
		} else if (!Gdx.input.isKeyPressed(Keys.RIGHT_ARROW) && !toggle) {
			toggle = true;
		}
		
		
		
		
		
		if (Gdx.input.isKeyPressed(Keys.FORWARD)) {
			getCurrentShip().moveForward();
		}
		
		if (Gdx.input.isKeyPressed(Keys.BACKWARD)) {
			getCurrentShip().moveBackward();
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			getCurrentShip().rotateRight();
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			getCurrentShip().rotateLeft();
		}
		
		
		
	}

}
