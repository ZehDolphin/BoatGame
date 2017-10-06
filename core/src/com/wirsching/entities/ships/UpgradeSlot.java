package com.wirsching.entities.ships;

import com.wirsching.entities.turrets.Turret;

public class UpgradeSlot {

	public float x, y;
	
	public int size = 1;
	
	public Turret turret;
	
	public UpgradeSlot(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setTurret(Turret t) {
		turret = t;
	}
	
	public Turret getTurret() {
		return turret;
	}

}
