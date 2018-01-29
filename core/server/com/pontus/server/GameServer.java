package com.pontus.server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pontus.server.packets.SyncShips;
import com.wirsching.Resources;
import com.wirsching.entities.Entity;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.Tag;
import com.wirsching.entities.ships.Ship;
import com.wirsching.entities.turrets.Turret;
import com.wirsching.graphics.screens.GameScreen;
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

		Connection conn;

		try {

			// Connect to the database.
			conn = DriverManager.getConnection("jdbc:mysql://pontuswirsching.com/boat_game_eu1?user=webmaster&password=Fiskar99");

			Statement s = conn.createStatement();

			ResultSet rs = null;

			rs = s.executeQuery("SELECT * FROM users WHERE username='" + name + "'");
			rs.first();
			
			String ships = rs.getString("ships");

			print(ships);

			JSONArray arr = new JSONArray(ships);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject o = arr.getJSONObject(i);

				float x = o.getJSONObject("position").getFloat("x");
				float y = o.getJSONObject("position").getFloat("y");

				float rotation = o.getFloat("rotation");
				float health = o.getFloat("health");
				int ID = o.getInt("id");
				String player = o.getString("player");

				print(player);

				JSONArray tags = o.getJSONArray("tags");
				JSONArray upgradeSlots = o.getJSONArray("upgrade_slots");

				Ship ship;
				Class<?> clazz;
				try {
					clazz = Class.forName(o.getString("class"));

					Constructor<?>[] c = clazz.getConstructors();

					Constructor<?> ctor = c[0];
					ship = (Ship) ctor.newInstance(x, y);
					ship.setRotation(rotation);
					ship.setHealth(health);
					ship.setID(ID);
					ship.setPlayer(player);

					for (int j = 0; j < tags.length(); j++) {
						ship.addTag(Tag.valueOf(tags.getString(j)));
					}

					for (int j = 0; j < upgradeSlots.length(); j++) {

						Turret t;
						try {

							Class<?> clazz2 = Class.forName(upgradeSlots.getJSONObject(j).getString("class"));
							Constructor<?>[] c2 = clazz2.getConstructors();
							Constructor<?> ctor2 = c2[0];
							t = (Turret) ctor2.newInstance();

							ship.addTurret(j, t);

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					print("added: \n" + ship.toString());
					
					EntityHandler.addEntity(ship);
				} catch (Exception e) {
				}

			}
			
			System.out.println("Sync ships!");
			broadcast(new SyncShips());

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		// if (b == false && name.equals("ZehDolphin")) {
		// b = true;
		//
		// EntityHandler.addEntity(new OakBoat(100,
		// 100).setPlayer("ZehDolphin").addTurret(0, new StoneThrower()));
		// EntityHandler.addEntity(new MetalBoat(-100,
		// 100).setPlayer("ZehDolphin").addTurret(0, new
		// StoneThrower()).addTurret(1, new StoneThrower()));
		//
		// print(EntityHandler.getEntity(0).toString());
		// print(EntityHandler.getEntity(1).toString());
		//
		// }
		//
		// if (name.equals("bob")) {
		// }

	}

	public void run() {

		while (true) {

			// Ship s = (Ship) EntityHandler.getEntity(0);
			//
			// s.rotateRight();
			// s.moveForward();
			//

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

	public void broadcastExcluding(Packet packet, Player... players) {
		for (Player p : players) {
			boolean skip = false;
			for (int i = 0; i < players.length; i++)
				if (p.equals(players[i]))
					skip = true;
			if (!skip)
				server.sendPacket(packet, p.getIP(), p.getPort());
		}
	}

	public GameServer() {

		// Can't add entities before this line.
		Resources.dontLoadAssets();

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


					players.add(new Player(packet.getValue("username"), packet.getIP(), packet.getPort()));

					initPlayer(packet.getValue("username"));
					
					// Broadcast player join to all players.
					broadcast(packet);

					print(packet.getValue("username") + " has connected.");
				}

				if (id.equals("game_disconnect")) {
					
					
					
					
					
					
					Connection conn;

					try {

						// Connect to the database.
						conn = DriverManager.getConnection("jdbc:mysql://pontuswirsching.com/boat_game_eu1?user=webmaster&password=Fiskar99");

						Statement s = conn.createStatement();

						
						String string = "";
						
						boolean b = false;
						for (Entity e : EntityHandler.getEntitiesByTag(Tag.SHIP)) {
							Ship ship = (Ship) e;
							if (ship.getPlayer().equals(packet.getValue("username"))) {
								if (b) {
									string += ", ";
								}
								string += ship.toString();
								b = true;
								EntityHandler.entities.remove(ship);
							}
						}
						
						string = string.replaceAll("'", "\"");

						System.out.println(string);
						
						s.executeUpdate("UPDATE users SET ships = " + "'["+ string +"]'" + " WHERE username='"+packet.getValue("username")+"'");
						

					} catch (SQLException ex) {
						// handle any errors
						System.out.println("SQLException: " + ex.getMessage());
						System.out.println("SQLState: " + ex.getSQLState());
						System.out.println("VendorError: " + ex.getErrorCode());
					}
					
					
					
					
					
					
					for (Player p : players)
						if (p.username == packet.getValue("username")) {
							players.remove(p);
							break;
						}

					for (Entity e : EntityHandler.getEntitiesByTag(Tag.SHIP)) {
						Ship s = (Ship) e;
						if (s.getPlayer().equals(packet.getValue("username")))
							s.remove();
					}

					broadcast(packet);

					print(packet.getValue("username") + " has disconnected.");
				}

				if (id.equals("game_sync")) {
					// print(packet.getValue("name") + ", Ship: '" +
					// packet.getValue("ship_id") +
					// "'\n\tPosition: " + packet.getValue("position_x") + ", "
					// + packet.getValue("position_y") + "\n\tRotation: " +
					// packet.getValue("rotation"));

					if (packet.getValue("ship_id") != null) {

						for (Entity e : EntityHandler.getEntitiesByTag(Tag.SHIP)) {
							Ship s = (Ship) e;
							try {
								if (s.getID() == Integer.parseInt(packet.getValue("ship_id"))) {
									s.setPosition(Float.parseFloat(packet.getValue("position_x")), Float.parseFloat(packet.getValue("position_y")));
									s.setRotation(Float.parseFloat(packet.getValue("rotation")));
								}
							} catch (Exception ee) {
								print(packet.toString());
								ee.printStackTrace();
							}
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
			public void disconnected(Packet packet) {

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
		// frame.setLocationRelativeTo(null);
		frame.setLocation(10, 10);
		frame.setVisible(true);

		server.start();
		print("Server started!");

	}

	public static void main(String[] args) {
		new GameServer();
	}

}
