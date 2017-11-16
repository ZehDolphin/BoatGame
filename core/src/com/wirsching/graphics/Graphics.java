package com.wirsching.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.wirsching.graphics.screens.GameScreen;
import com.wirsching.math.Point2f;

public class Graphics {

	private static SpriteBatch sb;
	private static ShapeRenderer sr;
	

	public static void setSpriteBatch(SpriteBatch sb) {
		Graphics.sb = sb;
	}
	
	public static SpriteBatch getSpriteBatch() {
		return sb;
	}
	
	public static void setShapeRenderer(ShapeRenderer sr) {
		Graphics.sr = sr;
	}
	
	public static ShapeRenderer getShapeRenderer() {
		return sr;
	}

	public static void setProjectionMatrix(Matrix4 projectionMatrix) {
		sb.setProjectionMatrix(projectionMatrix);
		sr.setProjectionMatrix(projectionMatrix);
	}

	public static void begin() {
		sb.begin();
	}

	public static void end() {
		sb.end();
	}
	
	public static void dispose() {
		sb.dispose();
	}

	
	// Geometry
	
	public static void drawRect(float x, float y, float width, float height) {
		sr.begin(ShapeType.Line);
		
		sr.box(x, y, 0, width, height, 0);
		
		sr.end();
	}
	
	
	
	
	
	
	
	// Draw methods
	
	public static void drawStaticTexture(TextureRegion tr, float x, float y) {
		Point2f p = GameScreen.camera.screenCoords(x, y);
		sb.draw(tr, p.getX() - tr.getRegionWidth() / 2, p.getY() - tr.getRegionHeight() / 2);
	}
	
	
	public static void drawTexture(TextureRegion tr, float x, float y) {
		sb.draw(tr, x - tr.getRegionWidth() / 2, y - tr.getRegionHeight() / 2);
	}
	
	public static void drawTexture(TextureRegion tr, float x, float y, float width, float height) {
		sb.draw(tr, x - tr.getRegionWidth() / 2, y - tr.getRegionHeight() / 2, 0, 0, width, height, 1, 1, 0);
	}
	
	public static void drawTexture(TextureRegion texture, float x, float y, float rotation) {
		sb.draw(texture, x - texture.getRegionWidth() / 2f, y - texture.getRegionHeight() / 2f, texture.getRegionWidth() / 2f, texture.getRegionHeight() / 2f, texture.getRegionWidth(), texture.getRegionHeight(), 1, 1, rotation);
	}
	
	public static void drawTexture(TextureRegion texture, Point2f p, float rotation) {
		drawTexture(texture, p.getX(), p.getY(), rotation);
	}

	public static float getDelta() {
		return Gdx.graphics.getDeltaTime();
	}
	
}
