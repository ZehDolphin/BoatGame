package com.wirsching.network;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wirsching.entities.Entity;
import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.Tag;
import com.wirsching.entities.ships.Ship;
import com.wirsching.entities.turrets.Turret;
import com.wirsching.graphics.screens.GameScreen;
import com.wirsching.net.client.Client;
import com.wirsching.net.client.ConnectionHandler;
import com.wirsching.net.packets.Packet;
import com.wirsching.network.packets.Connect;
import com.wirsching.network.packets.Disconnect;

/**
 * The client will keep contact with the selected game server.
 * 
 * @author Pontus Wirsching
 *
 */
public class GameClient extends Client {

	public GameClient() {
		super();

		// Request the host from the user.
		String input = (String) JOptionPane.showInputDialog(null, "Host:", "Connect to chat server", JOptionPane.QUESTION_MESSAGE, null, null, "localhost");
		if (input == null || input.trim().length() == 0)
			System.exit(1);
		final String host = input.trim();

		addConnectionHandler(new ConnectionHandler() {

			@Override
			public void connected(Packet packet) {

				// Once connected, send the player info.
				sendPacket(new Connect(GameScreen.name));

			}

			@Override
			public void received(Packet packet) {

				String id = packet.getID().trim();

				if (id.equals("game_sync_ships")) {

					System.out.println(packet.toString());

					ArrayList<Entity> ships = EntityHandler.getEntitiesByTag(Tag.SHIP);
					ArrayList<Ship> skip = new ArrayList<Ship>();
					
					for (int i = 0; i < ships.size(); i++) {
						if (i >= ships.size())
							break;
						if (!GameScreen.getPlayer().getShips().contains(ships.get(i)))
							ships.get(i).remove();
						else 
							skip.add((Ship) ships.get(i));
					}

					for (int i = 0; i < Integer.parseInt(packet.getValue("num_ships")); i++) {
						String s = packet.getValue(String.valueOf(i));
						s = s.replaceAll("'", "\"");

						JSONObject o = new JSONObject(s);

						float x = o.getJSONObject("position").getFloat("x");
						float y = o.getJSONObject("position").getFloat("y");

						float rotation = o.getFloat("rotation");
						float health = o.getFloat("health");
						int ID = o.getInt("id");
						String player = o.getString("player");
						
						boolean skipThis = false;
						for (Ship ship : skip) {
							if (ship.getID() == ID) skipThis = true;
						}
						if (skipThis) continue;
						
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

							if (player.equals(GameScreen.getPlayer().getName()))
								EntityHandler.addShip(ship, GameScreen.getPlayer());
							else
								EntityHandler.addEntity(ship);
						} catch (Exception e) {
						}

					}
				}

				if (id.equals("game_sync")) {

					for (Entity e : EntityHandler.getEntitiesByTag(Tag.SHIP)) {
						Ship s = (Ship) e;
						if (GameScreen.getPlayer().getCurrentShip() != null)
							if (s.getID() == GameScreen.getPlayer().getCurrentShip().getID())
								continue;
						try {
						if (s.getID() == Integer.parseInt(packet.getValue("ship_id"))) {
							s.setTargetPosition(Float.parseFloat(packet.getValue("position_x")), Float.parseFloat(packet.getValue("position_y")));
							s.setTargetRotation(Float.parseFloat(packet.getValue("rotation")));

							int numSlots = Integer.parseInt(packet.getValue("num_slots"));
							for (int i = 0; i < numSlots; i++) {
								if (s.getTurret(i) != null)
									s.getTurret(i).setTargetRotation(Float.parseFloat(packet.getValue("slot:" + i)));
							}

						}
						} catch(Exception ee) {
							
						}
					}

				}

			}

			@Override
			public void error(Packet packet) {

			}

			@Override
			public void disconnected(Packet packet) {
				// TODO Auto-generated method stub
				
			}

		});

		connectToServer(host, 4321);

	}

	public void disconnect() {
		sendPacket(new Disconnect(GameScreen.name));
	}

}
