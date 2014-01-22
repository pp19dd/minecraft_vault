package com.minecraftsquare;

import org.bukkit.scheduler.BukkitRunnable;

public class MinecraftSquareBalance extends BukkitRunnable {
	
	private MC2_Payload target;
	
	public MinecraftSquareBalance(MC2_Payload target) {
		this.target = target;
	}
	
	public void run() {
		MC2_Fetch q = new MC2_Fetch(
			"http://www.minecraftsquare.com/vault.php?mode=balance" +
			"&player=" + this.target.player_name + 
			"&ip=" + this.target.player_ip 
		);
		
		this.target.msg( q.obj.get("balance").toString() );
		
		this.target.player.getWorld().strikeLightningEffect(
			this.target.player.getLocation()
		);
		
		//Location loc;
		//this.target.player.playEffect(loc, Effect.CLICK1, 5);
		
		//this.target.player.getWorld().playSound( this.target.player.getWorld().lo
		// Bukkit.getPlayer(playerName).getWorld().playSound(location,Sound.BLAZE_DEATH,1, 0);
		
		/*this.target.player.sendMessage(
			ChatColor.RED +
			q.obj.get("balance").toString() 
		);*/
	}
}
