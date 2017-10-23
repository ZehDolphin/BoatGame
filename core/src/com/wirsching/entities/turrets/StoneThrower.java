package com.wirsching.entities.turrets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.graphics.Graphics;

/**
 * 
 * @author zehdolphin
 */
public class StoneThrower extends Turret {

	// TODO - Change StoneThrower texture
	TextureRegion texture = new TextureRegion(new Texture("textures/singleplayer.png"));
	
	public StoneThrower() {
		super();
		setRotationSpeed(720);
		setProjectile("Stone");
		setThrowForce(300f);
		setFirerate(2.0f);
	}
	
	@Override
	public void draw() {
		Graphics.drawTexture(texture, getParent().getWorldCoordinates(getPosition()), getRotation() - 90);
	}
	
	

}
