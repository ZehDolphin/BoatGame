package com.wirsching.graphics;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Keep track of all screens.
 *
 */
public class ScreenManager {

	private static HashMap<String, Screen> screens = new HashMap<String, Screen>();
	
	private static String selected = "undefined";
	
	public static void setSelected(String selected) {
		ScreenManager.selected = selected;
		getSelected().selected();
	}
	
	public static void add(Screen screen) {
		screens.put(screen.getId(), screen);
	}
	
	public static Screen get(String id) {
		return screens.get(id);
	}
	
	public static Screen getSelected() {
		return get(selected);
	}
	
	public static int size() {
		return screens.size();
	}
	
	public static void update() {
		getSelected().update();
		getSelected().updateGUI();
	}
	
	public static void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		getSelected().render();
		
		Graphics.getGuiSpriteBatch().setProjectionMatrix(getSelected().guiCamera.getProjectionMatrix());
		Graphics.getGuiSpriteBatch().begin();
		{
			getSelected().drawGUI();
		}
		Graphics.getGuiSpriteBatch().end();
	}
	
	public static void dispose() {
		getSelected().dispose();
	}
	
	public static void resize(int width, int height) {
		getSelected().resize(width, height);
	}
	
}
