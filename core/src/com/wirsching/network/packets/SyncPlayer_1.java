package com.wirsching.network.packets;

import com.wirsching.captains.Player;
import com.wirsching.net.packets.Packet;

public class SyncPlayer_1 extends Packet {

	public SyncPlayer_1(Player player) {
		setID("game_syncplayer");
		putData("name", player.getName());
		
		
		
		
		
		
//		putData("position_x", position.getX());
//		putData("position_y", position.getY());
//		putData("rotation", rotation);
	}

}
