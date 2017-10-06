package com.wirsching.entities.turrets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.graphics.Graphics;

public class StoneThrower extends Turret {

	TextureRegion texture = new TextureRegion(new Texture("textures/singleplayer.png"));
	
	public StoneThrower() {
		super();
		setRotationSpeed(720);

	}
	
	@Override
	public void draw() {
		Graphics.drawTexture(texture, getParent().getWorldCoordinates(getPosition()), getRotation() - 90);
	}
	
	

}
