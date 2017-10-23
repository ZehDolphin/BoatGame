package com.wirsching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.wirsching.graphics.ScreenManager;
import com.wirsching.graphics.screens.GameScreen;

public class BoatGame extends ApplicationAdapter {


	@Override
	public void create() {
		
		
		
		ArrayList<String> addresses = new ArrayList<String>();
		try {
			
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface ni : Collections.list(interfaces)) {
				for (InetAddress address : Collections.list(ni.getInetAddresses())) {
					if (address instanceof Inet4Address) {
						addresses.add(address.getHostAddress());
					}
				}
			}
			
		} catch(SocketException e) {
			e.printStackTrace();
		}
		
		
		String ipAddress = new String("");
		for (String str : addresses) {
			ipAddress += str + "\n";
		}
		
		System.out.println(ipAddress);
		
		
		/*
		 * Create a thread that will listen to messages, this is the server.
		 */
		new Thread(new Runnable() {

			@Override
			public void run() {
				ServerSocketHints serverSocketHint = new ServerSocketHints();
				
				// 0 means no timeout.
				serverSocketHint.acceptTimeout = 0;
			
				ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 4321, serverSocketHint);
				
				while (true) {
					Socket socket = serverSocket.accept(null);
					
					BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
					try {
						System.out.println(buffer.readLine());
					} catch(IOException e) {
						e.printStackTrace();
					}
					
				}
				
			
			}
			
		}).start();
		
		String ip = "127.0.0.1";
		int port = 4321;
		
		String textToSend = "ping\n";
		
		
		SocketHints socketHints = new SocketHints();
		socketHints.connectTimeout = 4000;
		
		Socket socket = Gdx.net.newClientSocket(Protocol.TCP, ip, port, socketHints);
		try {
			socket.getOutputStream().write(textToSend.getBytes());
		} catch(IOException e) {
			e.printStackTrace();
		}
		

		// Loads all resources.
		Resources.load();
		
		
		ScreenManager.add(new GameScreen());

		ScreenManager.setSelected("GAME");

	}

	@Override
	public void resize(int width, int height) {
		ScreenManager.resize(width, height);
	}

	@Override
	public void render() {
//		ScreenManager.update();
//		ScreenManager.render();
		
	}

	@Override
	public void dispose() {
		ScreenManager.dispose();
	}
}
