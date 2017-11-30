package com.wirsching.graphics.gui;

public class Fonts {

	public static final Font ARIAL_BLACK = new Font("arial_black");
	public static final Font ARIAL = new Font("arial");
	
	/**
	 * Dispose all fonts when done.
	 */
	public static void dispose() {
		ARIAL_BLACK.dispose();
		ARIAL.dispose();
	}
	
}
