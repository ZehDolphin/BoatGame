package com.wirsching.captains;

import java.util.Random;

import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.Tag;
import com.wirsching.entities.ships.Ship;
import com.wirsching.graphics.Graphics;
import com.wirsching.math.Math;

class Action {
	static final int MOVE = 0;
	static final int ATTACK = 1;
	static final int STAY = 2;
	static final int FOLLOW = 3;
}

public class SimpleBot extends Captain {

	Ship target;
	
	public SimpleBot(String name) {
		super(name);
		
		controlShip((Ship) EntityHandler.getEntity(1));
		// Hardcode target the first ship.
		target = (Ship) EntityHandler.getEntitiesByTag(Tag.SHIP).get(0);
	}
	
	Random random = new Random();
	
	float actionInterval = 5;
	float directionInterval = 2;
	
	
	
	
	
	
	float actionTimer = 0;
	float directionTimer = 0;
	float currentAction = Action.STAY;
	float direction = 0.0f;
	
	
	
	public void update() {
		
		actionTimer += Graphics.getDelta();
		if (actionTimer > actionInterval) {
			actionTimer = 0;
			currentAction = (int) (random.nextInt(3));
		}
		
//		System.out.println(currentAction);
		
		if (currentAction == Action.MOVE) {
			getCurrentShip().moveForward();
			directionTimer += Graphics.getDelta();
			if (directionTimer > directionInterval) {
				directionTimer = 0;
				direction = random.nextFloat() * 360f;
			}
		}
		
		
		
		float angle = Math.getAngle(getCurrentShip().getPosition(), target.getPosition());
		
		
		
		getCurrentShip().rotateToTarget(direction);
	}

}
