package com.wirsching.captains;

import com.badlogic.gdx.Gdx;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.ships.Ship;
import com.wirsching.entities.turrets.Turret;
import com.wirsching.input.Keys;
import com.wirsching.input.Mouse;
import com.wirsching.math.Math;
import com.wirsching.math.Point2f;


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
		
		
		if (Mouse.isPressed()) {
			for (int i = 0; i < getCurrentShip().getSlots(); i++) {
				Turret t = getCurrentShip().getTurret(i);
				if (t != null) 
					t.fire();
			}
		}
		
		
		// Rotate all turrets on the ship to the mouse pointer.
		for (int i = 0; i < getCurrentShip().getSlots(); i++) {
			Turret t = getCurrentShip().getTurret(i);
			if (t != null) 
				t.rotateToTarget(Math.getAngle(new Point2f(getCurrentShip().getX() + t.getPosition().getX(), getCurrentShip().getY() + t.getPosition().getY()), Mouse.getPosition()));
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
