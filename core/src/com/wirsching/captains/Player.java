package com.wirsching.captains;

import com.badlogic.gdx.Gdx;
import com.wirsching.BoatGame;
import com.wirsching.entities.ships.Ship;
import com.wirsching.entities.turrets.Turret;
import com.wirsching.graphics.Graphics;
import com.wirsching.input.Keys;
import com.wirsching.input.Mouse;
import com.wirsching.input.TouchSurface;
import com.wirsching.math.Math;
import com.wirsching.math.Point2f;
import com.wirsching.network.packets.SyncPlayer;

/**
 * The local player.
 *
 */
public class Player extends Captain {

	public Player(String name) {
		super(name);

	}

	boolean toggle = false;

	boolean forward = false;
	boolean backward = false;
	boolean right = false;
	boolean left = false;
	
	float synctime = 0;

	public void update() {

		if (Gdx.input.isKeyPressed(Keys.RIGHT_ARROW) && toggle) {
			toggle = false;
			controlShip(getNextShip());
		} else if (!Gdx.input.isKeyPressed(Keys.RIGHT_ARROW) && !toggle)
			toggle = true;

		if (getCurrentShip() == null)
			return;

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

//		if (Gdx.input.isKeyPressed(Keys.FORWARD) && !forward) {
//			forward = true;
//			BoatGame.client.sendPacket(new SendAction(SendAction.MOVE_FORWARD));
//		} else if (!Gdx.input.isKeyPressed(Keys.FORWARD) && forward) {
//			forward = false;
//			BoatGame.client.sendPacket(new SendAction(SendAction.MOVE_FORWARD_STOP));
//		}
//
//		
//		if (Gdx.input.isKeyPressed(Keys.BACKWARD) && !backward) {
//			backward = true;
//			BoatGame.client.sendPacket(new SendAction(SendAction.MOVE_BACKWARD));
//		} else if (!Gdx.input.isKeyPressed(Keys.BACKWARD) && backward) {
//			backward = false;
//			BoatGame.client.sendPacket(new SendAction(SendAction.MOVE_BACKWARD_STOP));
//		}
//
//		
//		if (Gdx.input.isKeyPressed(Keys.RIGHT) && !right) {
//			right = true;
//			BoatGame.client.sendPacket(new SendAction(SendAction.ROTATE_RIGHT));
//		} else if (!Gdx.input.isKeyPressed(Keys.RIGHT) && right) {
//			right = false;
//			BoatGame.client.sendPacket(new SendAction(SendAction.ROTATE_RIGHT_STOP));
//		}
//		
//		if (Gdx.input.isKeyPressed(Keys.LEFT) && !left) {
//			left = true;
//			BoatGame.client.sendPacket(new SendAction(SendAction.ROTATE_LEFT));
//		} else if (!Gdx.input.isKeyPressed(Keys.LEFT) && left) {
//			left = false;
//			BoatGame.client.sendPacket(new SendAction(SendAction.ROTATE_LEFT_STOP));
//		}
		
		boolean moved = false;
		
		if (Gdx.input.isKeyPressed(Keys.FORWARD)) {
			getCurrentShip().moveForward();
			moved = true;
		}

		if (Gdx.input.isKeyPressed(Keys.BACKWARD)) {
			getCurrentShip().moveBackward();
			moved = true;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			getCurrentShip().rotateRight();
			moved = true;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			getCurrentShip().rotateLeft();
			moved = true;
		}
		
//		if (moved) {
//			Ship s = getCurrentShip();
//			synctime += Graphics.getDelta();
//			if (synctime > 1 / BoatGame.syncrate) {
//				synctime = 0;
//				BoatGame.client.sendPacket(new SyncPlayer(this));
//			}
//		} else synctime = 1 / BoatGame.syncrate;

	}

}
