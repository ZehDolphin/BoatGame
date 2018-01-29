package com.wirsching.network.packets;

import com.wirsching.entities.ships.Ship;
import com.wirsching.net.packets.Packet;

public class SyncPlayer extends Packet {

	public SyncPlayer(Ship s) {
		setID("game_sync");

		if (s == null)
			return;
		
		putData("name", s.getPlayer());
		putData("ship_id", s.getID());
		putData("position_x", s.getPosition().getX());
		putData("position_y", s.getPosition().getY());
		putData("rotation", s.getRotation() - 360 * 10f);

		putData("num_slots", s.getSlots());
		for (int i = 0; i < s.getSlots(); i++) {
			if (s.getTurret(i) != null)
				putData("slot:" + i, s.getTurret(i).getRotation());
		}

	}

}
