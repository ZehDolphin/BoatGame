package com.wirsching.network.packets;

import com.wirsching.captains.Player;
import com.wirsching.entities.ships.Ship;
import com.wirsching.net.packets.Packet;

public class SyncPlayer extends Packet {

	public SyncPlayer(Player player) {
		setID("game_sync");
		putData("name", player.getName());
		
		Ship s = player.getCurrentShip();
		if (s == null) return;
		
		putData("ship_id", s.getID());
		putData("position_x", s.getPosition().getX());
		putData("position_y", s.getPosition().getY());
		putData("rotation", s.getRotation());
	}

}
