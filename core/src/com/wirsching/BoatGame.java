package com.wirsching;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wirsching.entities.boats.OakBoat;

public class BoatGame extends ApplicationAdapter {
	
	private SpriteBatch batch;
	
	private OrthographicCamera camera;
	
	OakBoat boat;
	
	@Override
	public void create () {
		
		float virtualWidth = 128;
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(virtualWidth, virtualWidth * (h / w));
		
		Resources.load();
		
		batch = new SpriteBatch();
		boat = new OakBoat();
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(124f / 255f, 166f / 255f, 255f / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();

		boat.draw(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		Resources.dispose();

	}
}
