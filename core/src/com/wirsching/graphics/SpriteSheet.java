package com.wirsching.graphics;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.XmlReader;

/**
 * The SpriteSheet is an image devided up in smaller parts, "sprites". <br>
 * @author Pontus Wirsching
 *
 */
public class SpriteSheet {

	private Texture image;
	private FileHandle handle;
	
	private HashMap<String, TextureRegion> sprites = new HashMap<String, TextureRegion>();
	
	/**
	 * Creates a new spritesheet from a .png and .xml file.
	 * @param path - Path to the spritesheet without file extension.
	 */
	public SpriteSheet(String path) {
		
		// Load the .xml file associated to the spritesheet 
		handle = Gdx.files.internal(path + ".xml");
		
		// Load the image file
		image = new Texture(path + ".png");
		try {
			XmlReader xml = new XmlReader();
			XmlReader.Element xml_element = xml.parse(handle);
			
			Iterator<?> iterator_level = xml_element.getChildrenByName("Sprite").iterator();
			while(iterator_level.hasNext()){
			     XmlReader.Element sprite_element = (XmlReader.Element)iterator_level.next();
			     String id = sprite_element.getAttribute("id");
			     int x = Integer.parseInt(sprite_element.getAttribute("x"));
			     int y = Integer.parseInt(sprite_element.getAttribute("y"));
			     int width = Integer.parseInt(sprite_element.getAttribute("width"));
			     int height = Integer.parseInt(sprite_element.getAttribute("height"));
			     sprites.put(id, new TextureRegion(image, x, y, width, height));
			 }
			
			
		} catch (IOException e) {
			System.err.println("Could not laod spritesheet: '" + path +"'");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Create a new spritesheet with a plain image.
	 */
	public SpriteSheet(Texture image) {
		this.image = image;
	}
	
	
	/**
	 * Adds a textureregion to the sprites list.
	 * @param id - name of the sprite
	 * @param x - sheet x coordinate
	 * @param y - sheet y coordinate
	 * @param width - sprite width
	 * @param height - sprite height
	 */
	public void load(String id, int x, int y, int width, int height) {
		sprites.put(id, new TextureRegion(image, x, y, width, height));
	}
	
	/**
	 * Returns a TextureRegion from the sprites list.
	 */
	public TextureRegion get(String id) {
		return sprites.get(id);
	}
	
	/**
	 * Dispose the image.
	 */
	public void dispose() {
		image.dispose();
	}

	/**
	 * Loads a spritesheet as a grid.
	 */
	public SpriteSheet loadGrid(int width, int height) {
		int w = image.getWidth() / width;
		int h = image.getHeight() / height;
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				sprites.put(x + ":" + y, new TextureRegion(image, x * width, y * height, width, height));
			}
		}
		
		
		return this;
	}
	
}
