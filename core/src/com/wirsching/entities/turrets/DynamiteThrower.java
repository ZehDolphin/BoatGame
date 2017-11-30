package com.wirsching.entities.turrets;

import com.wirsching.Resources;
import com.wirsching.graphics.Graphics;
import com.wirsching.graphics.animation.Animation;

public class DynamiteThrower extends Turret {

	Animation a;
	
	public DynamiteThrower() {
		super();
		setRotationSpeed(720);
		setProjectile("Dynamite");
		setThrowForce(300f);
		setFirerate(0.5f);
		
		a = new Animation(Resources.get("animation_dynamitethrower")).setFPS(60f);
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
