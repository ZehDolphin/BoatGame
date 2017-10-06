package com.wirsching.entities.ships;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.Resources;
import com.wirsching.entities.Tag;
import com.wirsching.graphics.Graphics;

public class MetalBoat extends Ship {

	private TextureRegion texture;
	
	public MetalBoat(float x, float y) {
		super(x, y);
		setDimensions(15, 27);
		addTag(Tag.DYNAMIC);
		setRotationSpeed(150);
		setAcceleration(500f);
		setMaxSpeed(100f);
		
		setUpgradeSlots(new UpgradeSlot(0, -10), new UpgradeSlot(0, 6));
		
		texture = Resources.getTextureRegion("boats/metal_boat");
		
	}

	@Override
	public void draw() {
		Graphics.drawTexture(texture, getPosition(), getRotation());
		super.draw();

	}
	
}
