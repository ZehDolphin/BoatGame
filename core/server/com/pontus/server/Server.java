package com.pontus.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

public class Server implements Runnable, ApplicationListener {

	public static int port = 4321;

	@Override
	public void run() {
		ServerSocketHints serverSocketHint = new ServerSocketHints();
		
		// 0 means no timeout.
		serverSocketHint.acceptTimeout = 1000;
		
		
	
		ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, port, serverSocketHint);
		
		while (true) {
			Socket socket = serverSocket.accept(null);
			
			BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			try {
				System.out.println("[" + socket.getRemoteAddress() + "] > " + buffer.readLine());
			} catch(IOException e) {
				e.printStackTrace();
			}
			
		}		
	}

	@Override
	public void create() {
		new Thread(this, "server_socket").start();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
