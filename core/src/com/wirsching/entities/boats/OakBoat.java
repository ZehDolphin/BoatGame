package com.wirsching.entities.boats;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.Resources;
import com.wirsching.entities.Entity;

public class OakBoat extends Entity {

	private TextureRegion texture;
	
	public OakBoat() {
		setId("oak_boat");
		texture = Resources.getTextureRegion("boats/oak_boat");
	}

	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		batch.draw(texture, position.getX() - texture.getRegionWidth() / 2f, position.getY() - texture.getRegionHeight() / 2f, texture.getRegionWidth() / 2f, texture.getRegionHeight() / 2f, texture.getRegionWidth(), texture.getRegionHeight(), 1, 1, 0);
		
		
		
	}
	
}
