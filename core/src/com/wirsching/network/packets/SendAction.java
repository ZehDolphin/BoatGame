package com.wirsching.network.packets;

import com.wirsching.net.packets.Packet;

public class SendAction extends Packet {

	public static final int MOVE_FORWARD = 0;
	public static final int MOVE_BACKWARD = 1;
	public static final int ROTATE_RIGHT = 2;
	public static final int ROTATE_LEFT = 3;
	public static final int MOVE_FORWARD_STOP = 4;
	public static final int MOVE_BACKWARD_STOP = 5;
	public static final int ROTATE_RIGHT_STOP = 6;
	public static final int ROTATE_LEFT_STOP = 7;
	
	public SendAction(int action) {
		setID("send_action");
		putData("action", action);
		
	}

}
