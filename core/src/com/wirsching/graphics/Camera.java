package com.wirsching.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.wirsching.math.Math;
import com.wirsching.math.Point2f;

public class Camera {

	public OrthographicCamera camera;
	
	private float virtualWidth = 400;
	private float cameraMultiplier = 3f;

	public Point2f target = new Point2f();
	
	public void setTarget(Point2f p) {
		target = p;
	}
	
	public void setTarget(float x, float y) {
		target.set(x, y);
	}
	
	public Camera() {
		camera = new OrthographicCamera();
	}
	
	
	
	
	public void update() {
		
		float angle = Math.getAngle(new Point2f(getX(), getY()), new Point2f(target.getX(), target.getY()));
		float distance = Math.getDistance(new Point2f(getX(), getY()), new Point2f(target.getX(), target.getY()));
		setX((float) (getX() + Math.cos(angle) * distance * cameraMultiplier * Graphics.getDelta()));
		setY((float) (getY() + Math.sin(angle) * distance * cameraMultiplier * Graphics.getDelta()));
		
	}
	
	
	
	
	public Camera setVirtualWidth(float width) {
		virtualWidth = width;
		return this;
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
//		Vector3 a = camera.unproject(new Vector3(f, g, 0));
		return new Point2f((getX() + f) - camera.viewportWidth / 2, (getY() - g) + camera.viewportHeight / 2);
	}
}
