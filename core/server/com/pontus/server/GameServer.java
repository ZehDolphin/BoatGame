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
import com.wirsching.entities.Entity;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.Tag;
import com.wirsching.entities.ships.MetalBoat;
import com.wirsching.entities.ships.OakBoat;
import com.wirsching.entities.ships.Ship;
import com.wirsching.entities.turrets.StoneThrower;
import com.wirsching.net.client.ConnectionHandler;
import com.wirsching.net.packets.Packet;
import com.wirsching.net.packets.PacketServerError;
import com.wirsching.net.server.Server;

public class GameServer extends Thread {

	public Server server;

	public static int port = 4321;

	public ArrayList<Player> players = new ArrayList<Player>();

	JTextArea console = new JTextArea();

	/**
	 * Returns the player that sent the packet.
	 */
	public Player getPlayer(Packet packet) {
		for (Player p : players) 
			if (p.ip.equals(packet.getIP()) && p.port == packet.getPort())
				return p;
		return null;
	}
	
	public void print(String message) {
		console.append(message + "\n");
	}

	boolean b = false;
	
	public void initPlayer(String name) {
		if (b == false && name.equals("ZehDolphin")) {
			b = true;

			EntityHandler.addEntity(new OakBoat(100, 100).setPlayer("ZehDolphin").addTurret(0, new StoneThrower()));
			EntityHandler.addEntity(new MetalBoat(-100, 100).setPlayer("ZehDolphin").addTurret(0, new StoneThrower()).addTurret(1, new StoneThrower()));

			print(EntityHandler.getEntity(0).toString());
			
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

	public void broadcast(Packet packet) {
		for (Player p : players) {
			server.sendPacket(packet, p.getIP(), p.getPort());
		}
	}
	
	public GameServer() {
		

		// MysqlDataSource dataSource = new MysqlDataSource();
		// dataSource.setUser("");
		// dataSource.setPassword("tiger");
		// dataSource.setServerName("myDBHost.example.org");

		
		
		// Can't add entities before this line.
		Resources.dontLoadAssets();
		
		
		EntityHandler.addEntity(new OakBoat(0, 0).setPlayer("bob"));


		server = new Server(port).setConnectionHandler(new ConnectionHandler() {

			@Override
			public void connected(Packet packet) {

			}

			@Override
			public void received(Packet packet) {

				String id = packet.getID().trim();
				Player player = getPlayer(packet);

				if (id.equals("game_connect")) {
					
					
					
					if (player != null) {
						print(packet.getValue("username") + " tried to connect but is already connected.");
						PacketServerError e = (PacketServerError) new PacketServerError();
						e.putData("error", "already_connected");
						server.sendPacket(e, packet.getIP(), packet.getPort());
						return;
					}
					
					
					
					initPlayer(packet.getValue("username"));
					
					players.add(new Player(packet.getValue("username"), packet.getIP(), packet.getPort()));

					server.sendPacket(new SyncEntities(), packet.getIP(), packet.getPort());


					// TODO - broadcast player join to all players.

					print(packet.getValue("username") + " has connected.");
				}

				if (id.equals("game_disconnect")) {
					for (Player p : players)
						if (p.username == packet.getValue("username")) {
							players.remove(p);
							break;
						}
					
					for (Entity e : EntityHandler.getEntitiesByTag(Tag.SHIP)) {
						Ship s = (Ship) e;
						if (s.getPlayer().equals(packet.getValue("username"))) s.remove();
					}
					
					print(packet.getValue("username") + " has disconnected.");
				}

				if (id.equals("game_sync")) {
//					print(packet.getValue("name") + ", Ship: '" + packet.getValue("ship_id") + 
//							"'\n\tPosition: " + packet.getValue("position_x") + ", "
//							+ packet.getValue("position_y") + "\n\tRotation: " + packet.getValue("rotation"));
					
					for (Entity e : EntityHandler.getEntitiesByTag(Tag.SHIP)) {
						Ship s = (Ship) e;
						if (s.getID() == Integer.parseInt(packet.getValue("ship_id"))) {
							s.setPosition(Float.parseFloat(packet.getValue("position_x")), Float.parseFloat(packet.getValue("position_y")));
							s.setRotation(Float.parseFloat(packet.getValue("rotation")));
						}
					}
					
					broadcast(packet);
					
					
				}
				
				if (id.equals("send_action")) {
					print(player.getName() + " sent action: " + packet.getValue("action"));
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
//		frame.setLocationRelativeTo(null);
		frame.setLocation(10, 10);
		frame.setVisible(true);

		server.start();
		print("Server started!");

	}

	public static void main(String[] args) {
		new GameServer();
	}

}
