package com.pontus.server;

public class Player {

	public String ip;
	public int port;
	public String username;
	
	public Player(String username, String ip, int port) {
		this.username = username;
		this.ip = ip;
		this.port = port;
	}
	
	public String getIP() {
		return ip;
	}
	
	public String getName() {
		return username;
	}

	public int getPort() {
		return port;
	}
	
}
