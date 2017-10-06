package com.wirsching;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.graphics.SpriteSheet;

public class Resources {

	private static HashMap<String, SpriteSheet> spriteSheets = new HashMap<String, SpriteSheet>();
	
	public static void add(String id, SpriteSheet sheet) {
		spriteSheets.put(id, sheet);
	}
	
	public static SpriteSheet get(String id) {
		return spriteSheets.get(id);
	}
	
	public static TextureRegion getTextureRegion(String id) {
		String[] content = id.split("/");
		try {
			return get(content[0]).get(content[1]);
		} catch(Exception e) {
			System.err.println("Could not find the TextureRegion: '" + id + "'");			
			if (content.length < 2) System.err.println("Reason: SpriteSheet path error. '" + id + "'");
			if (get(content[0]) == null) System.err.println("Reason: SpriteSheet '" + content[0] + "' could't be found!");
			if (get(content[0]).get(content[1]) == null) System.err.println("Reason: TextureRegion '" + content[1] + "' could't be found!");
			
			e.printStackTrace();
			Gdx.app.exit();
			return null;
		}
	}
	
	public static void load() {
		add("boats", new SpriteSheet("textures/boats"));
		add("gui_panel", new SpriteSheet(new Texture("textures/window.png")).loadGrid(10, 10));
	}
	
	
	
	public static void dispose() {
		get("boats").dispose();
	}

}
