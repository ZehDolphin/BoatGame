package com.wirsching.entities.turrets;

import com.wirsching.Resources;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.animation.Animation;

/**
 * 
 * @author zehdolphin
 * @see com.wirsching.entities.projectiles.Stone
 */
public class StoneThrower extends Turret {

	Animation a;
	
	public StoneThrower() {
		super();
		setRotationSpeed(720);
		setProjectile("Stone");
		setThrowForce(300f);
		setFirerate(2.0f);
		
		a = new Animation(Resources.get("animation_stonethrower")).setFPS(60f);
	}
	
	@Override
	public void justFired() {
		a.playOnce();
	}
	
	@Override
	public void draw() {
		Graphics.drawTexture(a.getCurrentFrame(), getParent().getWorldCoordinates(getPosition()), getRotation());
	}
	
	

}
