package com.pontus.server.packets;

import com.wirsching.entities.Entity;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.Tag;
import com.wirsching.net.packets.Packet;

public class SyncShip extends Packet {

	public SyncShip() {
		setID("game_sync_ship");
		
		putData("num_ships", EntityHandler.getEntitiesByTag(Tag.SHIP).size());
		
		for (Entity e : EntityHandler.getEntitiesByTag(Tag.SHIP)) {
			putData(String.valueOf(EntityHandler.entities.indexOf(e)), e.toString());
		}
		
	}

}
