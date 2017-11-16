package com.wirsching.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.wirsching.math.Point2f;

public class Camera {

	public OrthographicCamera camera;
	
	private float virtualWidth = 400;
	
	public Camera() {
		camera = new OrthographicCamera();
	}
	
	public void resize(int width, int height) {
		camera.viewportWidth = virtualWidth;
		camera.viewportHeight = virtualWidth * height / width;
		camera.update();
	}

	public Matrix4 getProjectionMatrix() {
		return camera.combined;
	}
	
	public void setX(float x) {
		camera.position.x = x;
		camera.update();
	}
	
	public void setY(float y) {
		camera.position.y = y;
		camera.update();
	}
	
	public float getX() {
		return camera.position.x;
	}
	
	public float getY() {
		return camera.position.y;
	}

	public Point2f screenCoords(float f, float g) {
		Vector3 a = camera.unproject(new Vector3(f, g, 0));
		return new Point2f(a.x, a.y);
	}
}
