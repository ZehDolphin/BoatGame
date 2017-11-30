package com.wirsching.network.packets;

import com.wirsching.net.packets.Packet;

public class Connect extends Packet {

	public Connect(String name) {
		setID("game_connect");
		putData("username", name);
	}

}
