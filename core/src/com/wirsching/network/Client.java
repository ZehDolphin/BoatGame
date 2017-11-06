package com.wirsching.network;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

/**
 * The client will keep contact with the selected game server.
 * 
 * @author Pontus Wirsching
 *
 */
public class Client {
	
	
	/**
	 * The connection timeout when connecting to a server. <br>
	 */
	private int timeout = 1000;
	
	/**
	 * The client socket. <br>
	 */
	private Socket socket = null;
	

	public Client() {
		
	}
	
	/**
	 * Connects to a server. <br>
	 * 
	 * @return
	 */
	public boolean connectTo(String ip, int port) {
		
		SocketHints sh = new SocketHints();
		sh.connectTimeout = timeout;
		
		socket = Gdx.net.newClientSocket(Protocol.TCP, ip, port, sh);
		
		send("connect");
		
		return false;
	}
	
	/**
	 * Sends a byte message to the server if connected. <br>
	 * @param bytes
	 */
	public boolean send(Object object) {
		System.out.println("'"+object + "' > [" + socket.getRemoteAddress() +"]");
		try {
			socket.getOutputStream().write(new String(object+"\n").getBytes());
			return true;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
