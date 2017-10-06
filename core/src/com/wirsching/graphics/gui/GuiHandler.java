package com.wirsching.graphics.gui;

import java.util.ArrayList;

import com.wirsching.graphics.Graphics;

public class GuiHandler {

	public static ArrayList<GuiComponent> guis = new ArrayList<GuiComponent>();
	
	
	public static void addGui(GuiComponent gui) {
		guis.add(gui);
	}
	
	public static GuiComponent getGui(int index) {
		return guis.get(index);
	}
	
	public static GuiComponent getGui(String id) {
		for (GuiComponent g : guis)
			if (g.getId().equals(id))
				return g;
		return null;
	}
	
	public static void update() {
		for (GuiComponent g : guis)
			g.update();
	}
	
	public static void draw() {
		for (GuiComponent g : guis)
			g.draw();
	}

}
