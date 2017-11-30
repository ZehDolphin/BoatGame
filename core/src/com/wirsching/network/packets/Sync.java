package com.wirsching.network.packets;

import com.wirsching.math.Point2f;
import com.wirsching.net.packets.Packet;

public class Sync extends Packet {

	public Sync(String name, Point2f position, float rotation) {
		setID("game_sync");
		putData("name", name);
		putData("position_x", position.getX());
		putData("position_y", position.getY());
		putData("rotation", rotation);
	}

}
