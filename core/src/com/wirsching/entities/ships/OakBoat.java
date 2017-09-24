package com.wirsching.entities.ships;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.Resources;
import com.wirsching.entities.Tag;
import com.wirsching.graphics.Graphics;

public class OakBoat extends Ship {

	private TextureRegion texture;
	
	public OakBoat(float x, float y) {
		super(x, y);
		setWidth(15);
		setHeight(18);
		addTag(Tag.DYNAMIC);
		setRotationSpeed(180);
		setAcceleration(1000f);
		setMaxSpeed(100f);
		
		texture = Resources.getTextureRegion("boats/oak_boat");
	}

	@Override
	public void draw() {
		Graphics.drawTexture(texture, getPosition(), rotation);
	}
	
}
