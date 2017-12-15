package com.wirsching.network;

import java.lang.reflect.Constructor;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wirsching.entities.EntityHandler;
import com.wirsching.entities.Tag;
import com.wirsching.entities.ships.Ship;
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

				System.out.println("Received: " + packet.toString());

				String id = packet.getID().trim();

				if (id.equals("game_sync_entities")) {
					for (int i = 0; i < Integer.parseInt(packet.getValue("num_entities")); i++) {
						String s = packet.getValue(String.valueOf(i));
						s = s.replaceAll("'", "\"");

						JSONObject o = new JSONObject(s);

						float x = o.getJSONObject("position").getFloat("x");
						float y = o.getJSONObject("position").getFloat("y");

						float rotation = o.getFloat("rotation");
						float health = o.getFloat("health");
						String ID = o.getString("id");
						String player = o.getString("player");
						
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
							
							EntityHandler.addShip(ship, GameScreen.getPlayer());
						} catch (Exception e) {
						}

					}
				}

			}

			@Override
			public void error(Packet packet) {

			}

			@Override
			public void disconnected() {

			}

		});

		connectToServer(host, 4321);

	}

	public void disconnect() {
		sendPacket(new Disconnect(GameScreen.name));
	}

}
