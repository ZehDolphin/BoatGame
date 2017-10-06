package com.wirsching.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.wirsching.math.Point2f;

public class Mouse {

	private static Camera currentCamera;
	
	public static void setCurrentCamera(Camera c) {
		currentCamera = c;
	}
	
	public static Point2f getPosition() {
		Vector3 v = currentCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		return new Point2f(v.x, v.y);
	}

}
