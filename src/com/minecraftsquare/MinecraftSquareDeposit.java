package com.minecraftsquare;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class MinecraftSquareDeposit extends BukkitRunnable {

	private MC2_Payload target;	

	public MinecraftSquareDeposit(MC2_Payload target) {
		this.target = target;
	}
	
	public void run() {
		PlayerInventory inventory = this.target.player.getInventory();
		ItemStack items = new ItemStack( this.target.material, this.target.quantity );
		
		if( !inventory.contains(items) ) {
			this.target.msg(
				"error: you do not have " + this.target.quantityS + " x " + 
				this.target.material.name().toLowerCase()  
			);
			return;
		}

		@SuppressWarnings("unused")
		MC2_Fetch q = new MC2_Fetch(
			"http://www.minecraftsquare.com/vault.php?mode=deposit" +
			"&player=" + this.target.player_name +
			"&ip=" + this.target.player_ip +
			"&quantity=" + this.target.quantityS +
			"&item=" + this.target.material.name()
		);
		
		// TODO: check if server ack'd the transfer
		inventory.remove( items );
		
		this.target.msg(
			"deposited " + this.target.quantityS + " x " + this.target.material.name().toLowerCase()
		);
		
	}
}
