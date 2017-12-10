package com.wirsching.captains;

import com.badlogic.gdx.Gdx;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.ships.Ship;
import com.wirsching.entities.turrets.Turret;
import com.wirsching.input.Keys;
import com.wirsching.input.Mouse;
import com.wirsching.input.TouchSurface;
import com.wirsching.math.Math;
import com.wirsching.math.Point2f;


/**
 * The local player.
 *
 */
public class Player extends Captain {
	
	public Player(String name) {
		super(name);
		
		controlShip((Ship) EntityHandler.getEntity(0));
		
	}

	boolean toggle = false;
	
	public void update() {
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT_ARROW) && toggle) {
			toggle = false;
			
			controlShip(EntityHandler.getNextShip(getCurrentShip(), getName()));
			
		} else if (!Gdx.input.isKeyPressed(Keys.RIGHT_ARROW) && !toggle) {
			toggle = true;
		}
		
		
		// Fire turrets when mouse is pressed.
		if (Mouse.isPressed(TouchSurface.GAME)) {
			for (int i = 0; i < getCurrentShip().getSlots(); i++) {
				Turret t = getCurrentShip().getTurret(i);
				if (t != null) 
					t.fire();
			}
		}
		
		
		// Rotate all turrets on the ship to the mouse pointer.
		for (int i = 0; i < getCurrentShip().getSlots(); i++) {
			Turret t = getCurrentShip().getTurret(i);
			if (t != null) {
				Point2f p = getCurrentShip().getWorldCoordinates(t.getPosition());
				t.rotateToTarget(Math.getAngle(new Point2f(p.getX(), p.getY()), Mouse.getPosition()));
			}
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
