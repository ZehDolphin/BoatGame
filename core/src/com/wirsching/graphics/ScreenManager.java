package com.wirsching.graphics;

import java.util.HashMap;

/**
 * Keep track of all screens.
 *
 */
public class ScreenManager {

	private static HashMap<String, Screen> screens = new HashMap<String, Screen>();
	
	private static String selected = "undefined";
	
	public static void setSelected(String selected) {
		ScreenManager.selected = selected;
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
	}
	
	public static void render() {
		getSelected().render();
	}
	
	public static void dispose() {
		getSelected().dispose();
	}
	
	public static void resize(int width, int height) {
		getSelected().resize(width, height);
	}
	
}
