package com.wirsching.entities.projectiles;

import com.wirsching.Resources;
import com.wirsching.entities.turrets.Turret;
import com.wirsching.graphics.Graphics;

public class Dynamite extends Projectile {

	public Dynamite(float x, float y, float angle, float force, Turret parent) {
		super(x, y, angle, force, parent);
		setType(Type.AREA_DAMAGE);
		setTexture(Resources.getTextureRegion("projectiles/dynamite"));
		setDamage(200.0f);
		setWeight(1.0f);
		setRange(250.0f);
		removeWhenStopped();
	}

	@Override
	public void draw() {
		Graphics.drawTexture(getTexture(), getX(), getY());
	}
	

}
