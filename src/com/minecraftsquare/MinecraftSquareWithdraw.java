package com.minecraftsquare;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class MinecraftSquareWithdraw extends BukkitRunnable {

	private MC2_Payload target;	
	
	public MinecraftSquareWithdraw(MC2_Payload target) {
		this.target = target;
	}
	
	public void run() {
		MC2_Fetch q = new MC2_Fetch(
			"http://www.minecraftsquare.com/vault.php?mode=withdraw" +
			"&player=" + this.target.player_name +
			"&ip=" + this.target.player_ip +
			"&quantity=" + this.target.quantityS + 
			"&item=" + this.target.material.name()
		);
		
		// server returns a json-object with quantity + item_name
		// check if it's a valid material
		String temp = (String) q.obj.get("item");
		Material mat2 = Material.matchMaterial(temp);
		if( mat2 == null ) {
			this.target.msg(
				"error: cant identify remote material: " + temp
			);
			return;
		}
		
		// try inserting into inventory, check return
		PlayerInventory inventory = this.target.player.getInventory();
		int quantity = Integer.parseInt( String.valueOf( q.obj.get("quantity"))); 
		ItemStack items = new ItemStack( mat2, quantity );
		HashMap<Integer, ItemStack> ret = inventory.addItem( items );
		
		// all items made it through
		if( ret.isEmpty() ) {
			this.target.msg(
				"withdrew " + String.valueOf(quantity) + " x " + mat2.name().toLowerCase()
			);
			return;
		}
		
		// ERROR: some items made it through, but not all
		int amount_cant_fit = ret.get(0).getAmount();
		int amount_accepted = quantity - amount_cant_fit;
		
		this.target.msg(
			"Withdrew " + String.valueOf(amount_accepted) + " x " + mat2.name().toLowerCase() +
			" (" + String.valueOf(amount_cant_fit) + " items cant fit, returning)"
		);

		@SuppressWarnings("unused")
		MC2_Fetch qret = new MC2_Fetch(
			"http://www.minecraftsquare.com/vault.php?mode=withdraw_rollback" +
			"&player=" + this.target.player_name +
			"&ip=" + this.target.player_ip +
			"&quantity=" + this.target.quantityS + 
			"&item=" + this.target.material.name() +
			"&return_quantity=" + String.valueOf(amount_cant_fit)
		);
			
		// TODO: check error first before ack'ing ?
	}
}

