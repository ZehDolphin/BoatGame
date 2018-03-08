package com.wirsching.graphics;

import java.util.ArrayList;

import com.wirsching.graphics.gui.GuiComponent;
import com.wirsching.input.Mouse;

/**
 * Each different screen.
 *
 */
public abstract class Screen {

	private String id = "undefined";
	
	public Camera camera;
	public GuiCamera guiCamera;
	
	public Screen() {
		
	}
	
	public void selected() {
		camera = new Camera();
		guiCamera = new GuiCamera().setVirtualWidth(1280);
		Mouse.setCurrentCamera(camera.camera);
		Mouse.setCurrentGuiCamera(guiCamera.camera);
	}
	
	public String getId() {
		return id;
	}
	
	protected void setId(String id) {
		this.id = id;
	}
	
	public abstract void update();
	
	public abstract void render();
	
	public abstract void dispose();
	
	public void resize(int width, int height) {
		
	}
	
	public ArrayList<GuiComponent> guis = new ArrayList<GuiComponent>();
	
	
	public void addGui(GuiComponent gui) {
		guis.add(gui);
	}
	
	public GuiComponent getGui(int index) {
		return guis.get(index);
	}
	
	public GuiComponent getGui(String id) {
		for (GuiComponent g : guis)
			if (g.getId().equals(id))
				return g;
		return null;
	}
	
	public void updateGUI() {
		for (GuiComponent g : guis)
			g.update();
	}
	
	public void drawGUI() {
		for (GuiComponent g : guis)
			g.draw();
	}
	
}
