package com.wirsching;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wirsching.graphics.SpriteSheet;

public class Resources {

	private static boolean ignoreAssets = false;
	
	public static void dontLoadAssets() {
		ignoreAssets = true;
	}
	
	private static HashMap<String, SpriteSheet> spriteSheets = new HashMap<String, SpriteSheet>();
	
	public static void add(String id, SpriteSheet sheet) {
		spriteSheets.put(id, sheet);
	}
	
	public static SpriteSheet get(String id) {
		return spriteSheets.get(id);
	}
	
	public static TextureRegion getTextureRegion(String id) {
		if (ignoreAssets) return null;
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
		add("projectiles", new SpriteSheet("textures/projectiles"));
		add("gui_panel", new SpriteSheet(new Texture("textures/window.png")).loadGrid(10, 10));
		
		add("gui_button", new SpriteSheet(new Texture("textures/input.png")).loadGrid(2, 2));
		add("gui_button_hover", new SpriteSheet(new Texture("textures/input_hover.png")).loadGrid(2, 2));

		
		add("animation_stonethrower", new SpriteSheet(new Texture("textures/turrets/stoneThrower.png")).loadGrid(21, 21));
		add("animation_dynamitethrower", new SpriteSheet(new Texture("textures/turrets/dynamiteThrower.png")).loadGrid(21, 21));
	}
	
	/**
	 * Converts a TextureRegion to a Pixmap. <br>
	 */
	public static Pixmap convertToPixmap(TextureRegion textureRegion) {
		Pixmap p = new Pixmap(textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), Pixmap.Format.Alpha);
		Texture texture = textureRegion.getTexture();
		if (!texture.getTextureData().isPrepared()) {
		    texture.getTextureData().prepare();
		}
		Pixmap pixmap = texture.getTextureData().consumePixmap();
		for (int x = 0; x < textureRegion.getRegionWidth(); x++) {
		    for (int y = 0; y < textureRegion.getRegionHeight(); y++) {
		        int colorInt = pixmap.getPixel(textureRegion.getRegionX() + x, textureRegion.getRegionY() + y);
		        p.drawPixel(x, y, colorInt);
		    }
		}
		return p;
	}
	
	
	public static void dispose() {
		get("boats").dispose();
	}

}
