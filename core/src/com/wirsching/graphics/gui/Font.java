package com.wirsching.graphics.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Font {

	private BitmapFont bitmapFont;
	private ShaderProgram shader;
	private GlyphLayout glyphLayout = new GlyphLayout();
	private Texture texture;
	
	public Font(String fontName) {
		texture = new Texture(Gdx.files.internal("fonts/" + fontName + "/font.png"), true);
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
		bitmapFont = new BitmapFont(Gdx.files.internal("fonts/" + fontName + "/font.fnt"), new TextureRegion(texture), false);
		shader = new ShaderProgram(Gdx.files.internal("fonts/" + fontName + "/font.vert"), Gdx.files.internal("fonts/" + fontName + "/font.frag"));
		if (!shader.isCompiled())
			Gdx.app.error("shader", "compilation failed:\n" + shader.getLog());
		
	}
	
	/**
	 * Sets the font color.
	 */
	public void setColor(Color color) {
		bitmapFont.setColor(color);
	}
	
	/**
	 * Sets the fonts scale.
	 */
	public void setScale(float scale) {
		getBitmapFont().getData().setScale(scale);
	}

	/**
	 * Returns the shader.
	 */
	public ShaderProgram getShader() {
		return shader;
	}
	
	/**
	 * Returns the GlyphLayout.
	 */
	public GlyphLayout getGlyphLayout() {
		return glyphLayout;
	}
	/**
	 * Returns the BitmapFont.
	 */
	public BitmapFont getBitmapFont() {
		return bitmapFont;
	}
	
	/**
	 * Returns the actual width of the font being rendered.
	 */
	public float getWidth(String text) {
		getGlyphLayout().setText(bitmapFont, text);
		return getGlyphLayout().width;
	}
	
	/**
	 * Returns the actual height of the font being rendered.
	 */
	public float getHeight(String text) {
		getGlyphLayout().setText(bitmapFont, text);
		return getGlyphLayout().height;
	}
	
	/**
	 * Draw text using the loaded font.
	 */
	public void draw(SpriteBatch sb, String text, float x, float y) {
		bitmapFont.draw(sb, getGlyphLayout(), x, y);
	}
	
	public void dispose() {
		texture.dispose();
		getBitmapFont().dispose();
		getShader().dispose();
	}
	
	

}
