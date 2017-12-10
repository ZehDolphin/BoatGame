package com.pontus.server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import com.pontus.server.packets.SyncEntities;
import com.wirsching.Resources;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.ships.MetalBoat;
import com.wirsching.entities.ships.OakBoat;
import com.wirsching.entities.turrets.StoneThrower;
import com.wirsching.net.client.ConnectionHandler;
import com.wirsching.net.packets.Packet;
import com.wirsching.net.server.Server;

public class GameServer extends Thread {

	public Server server;

	public static int port = 4321;

	public ArrayList<Player> players = new ArrayList<Player>();

	JTextArea console = new JTextArea();

	public void print(String message) {
		console.append(message + "\n");
	}

	public void initPlayer(String name) {
		if (name == "ZehDolphin") {

			EntityHandler.addEntity(new OakBoat(100, 100).addTurret(0, new StoneThrower()).setPlayer("ZehDolphin")
					.setID("ZehDolphin_ship_001"));

		}
	}

	public void run() {
		while (true) {

			EntityHandler.update();

			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public GameServer() {

		// MysqlDataSource dataSource = new MysqlDataSource();
		// dataSource.setUser("");
		// dataSource.setPassword("tiger");
		// dataSource.setServerName("myDBHost.example.org");

		Resources.dontLoadAssets();

		EntityHandler.addEntity(new OakBoat(100, 100));

		server = new Server(port).setConnectionHandler(new ConnectionHandler() {

			@Override
			public void connected(Packet packet) {

			}

			@Override
			public void received(Packet packet) {

				String id = packet.getID().trim();

				if (id.equals("game_connect")) {
					players.add(new Player(packet.getValue("username"), packet.getIP(), packet.getPort()));

					server.sendPacket(new SyncEntities(), packet.getIP(), packet.getPort());

					initPlayer(packet.getValue("username"));

					// TODO - broadcast player join to all players.

					print(packet.getValue("username") + " has connected.");
				}

				if (id.equals("game_disconnect")) {
					for (Player p : players)
						if (p.username == packet.getValue("username")) {
							players.remove(p);
							break;
						}
					print(packet.getValue("username") + " has disconnected.");
				}

				if (id.equals("game_sync")) {
					print(packet.getValue("name") + "\n\tPosition: " + packet.getValue("position_x") + ", "
							+ packet.getValue("position_y") + "\n\tRotation: " + packet.getValue("rotation"));
				}

			}

			@Override
			public void error(Packet packet) {

			}

			@Override
			public void disconnected() {

			}

		});

		JFrame frame = new JFrame("Game Server");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent evt) {
				System.exit(0);
			}
		});

		console.setEditable(false);
		JScrollPane scroll = new JScrollPane(console);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		DefaultCaret caret = (DefaultCaret) console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		frame.getContentPane().add(scroll);
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		server.start();
		print("Server started!");

	}

	public static void main(String[] args) {
		new GameServer();
	}

}
