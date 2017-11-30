package com.wirsching.network.packets;

import com.wirsching.net.packets.Packet;

public class Disconnect extends Packet {

	public Disconnect(String name) {
		setID("game_disconnect");
		putData("username", name);
	}

}
