package com.minecraftsquare;

import org.bukkit.Material;
import org.bukkit.entity.Player;

/*
 * neatly packages plugin, player, player name, ip address, quantity, material to send to workers
 */
public class MC2_Payload {
	public MinecraftSquare plugin;

	public Player player = null;
	
	public String player_name = null;
	public String player_ip = null;
	
	public int quantity = -1;
	public String quantityS;
	public Material material = null;
	
	public void msg(String txt) {
		// this.player.sendMessage( ChatColor.YELLOW + "------ MC^2 ---------------");
		this.player.sendMessage( txt );
	}
	
	private void name_ip() {
		this.player_name = this.player.getName().toString();
		this.player_ip = this.player.getAddress().getHostName();
	}
	
	MC2_Payload(
		MinecraftSquare plugin,	Player player
	) {
		this.plugin = plugin;
		this.player = player;
		this.name_ip();
	}

	MC2_Payload(
		MinecraftSquare plugin,	Player player, int quantity, Material material
	) {
		this.plugin = plugin;
		this.player = player;
		
		this.quantity = quantity;
		this.quantityS = String.valueOf(this.quantity);
		
		this.material = material;
		this.name_ip();
	}
	
}
