package com.wirsching.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.wirsching.math.Point2f;

public class GuiCamera {

	public OrthographicCamera camera;
	
	private float virtualWidth = 400;
	
	public GuiCamera() {
		camera = new OrthographicCamera();
		setX(0);
		setY(0);
	}
	
	public GuiCamera setVirtualWidth(float width) {
		virtualWidth = width;
		return this;
	}
	
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		setX(0);
		setY(0);
		camera.update();
	}

	public Matrix4 getProjectionMatrix() {
		return camera.combined;
	}
	
	public void setX(float x) {
		camera.position.x = x + camera.viewportWidth / 2;
		camera.update();
	}
	
	public void setY(float y) {
		camera.position.y = y + camera.viewportHeight / 2;
		camera.update();
	}
	
	public float getX() {
		return camera.position.x;
	}
	
	public float getY() {
		return camera.position.y;
	}

	public Point2f screenCoords(float f, float g) {
//		Vector3 a = camera.unproject(new Vector3(f, g, 0));
		return new Point2f((getX() + f) - camera.viewportWidth / 2, (getY() - g) + camera.viewportHeight / 2);
	}
}
