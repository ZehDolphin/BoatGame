package com.wirsching.entities.projectiles;

import com.wirsching.Resources;
import com.wirsching.entities.turrets.Turret;
import com.wirsching.graphics.Graphics;

/**
 * The simplest type of attack, players use this to get started in the game. <br>
 * 
 * @author Pontus Wirsching
 *
 */
public class Stone extends Projectile {
	
	
	public Stone(float x, float y, float angle, float force, Turret parent) {
		super(x, y, angle, force, parent);
		setType(Type.DIRECT_CONTACT);
		setTexture(Resources.getTextureRegion("projectiles/stone"));
		setDamage(10.0f);
		setWeight(1.0f);
		setRange(100.0f);
	}

	@Override
	public void draw() {
		Graphics.drawTexture(getTexture(), getX(), getY(), getRotation());
	}
	
}
