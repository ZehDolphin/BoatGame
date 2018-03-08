package com.wirsching.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.wirsching.graphics.gui.GuiComponent;
import com.wirsching.graphics.gui.GuiHandler;
import com.wirsching.graphics.screens.GameScreen;
import com.wirsching.math.Point2f;

public class Mouse {

	private static Camera currentCamera;
	private static Camera currentGuiCamera;

	private static TouchSurface lastTouched = TouchSurface.GAME;
	
	public static void setCurrentCamera(Camera c) {
		currentCamera = c;
	}
	
	public static void setCurrentGuiCamera(Camera c) {
		currentGuiCamera = c;
	}
	
	public static Point2f getPosition() {
		Vector3 v = currentCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		return new Point2f(v.x, v.y);
	}
	
	public static Point2f getGUIPosition() {
		Vector3 v = currentGuiCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		return new Point2f(v.x, v.y);
	}

	static boolean toggle = false;
	
	public static boolean isPressed(TouchSurface...surfaces) {
		
		if (Gdx.input.isTouched() && toggle) {
			toggle = false;
			
			
			Point2f p = new Point2f(Gdx.input.getX(), -Gdx.input.getY() + Gdx.graphics.getHeight());
			
			lastTouched = TouchSurface.GAME;
			for (GuiComponent g : GuiHandler.guis) {
				if (g.contains(p)) {
					lastTouched = TouchSurface.GUI;
				}
			}
			
		} else if (!Gdx.input.isTouched() && !toggle) {
			toggle = true;
		}
		
		
		
		
		
		
		
		if (surfaces.length != 0) {
			if (lastTouched == surfaces[0])
				return Gdx.input.isTouched();
		} else {
			return Gdx.input.isTouched();
		}
		
		return false;
		
	}
	
}
