package com.minecraftsquare;

import java.sql.Struct;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("unused")
public final class MinecraftSquare extends JavaPlugin {
	
	public void onEnable() {
		getLogger().info( "MC^2 onEnabled...");
	}
	
	public void onDisable() {
		getLogger().info( "MC^2 onDisabled...");
	}
	
	public void usage(MC2_Payload target) {
		this.usage( target.player );
	}

	public void usage(Player target) {
		target.sendMessage( "minecraft square proper usage: " );
		target.sendMessage( "/square balance           shows what you have in mc^2 bank vault" );
		target.sendMessage( "/square deposit 10 dirt   sends 10 dirt from your inventory to bank vault" );
		target.sendMessage( "/square withdraw 10 dirt  gets 10 dirt from bank valut, puts in your inventory" );
	}
	
	// command: /square balance
	private void do_balance(Player target, String[] args) {
		if( args.length != 1) {
			this.usage(target);
			return;
		}
		
		BukkitTask task = new MinecraftSquareBalance(
			new MC2_Payload(this, target)
		).runTaskAsynchronously(
			this
		);
	}
	
	// command: /square deposit 10 dirt
	private void do_deposit(Player target, String[] args) {
		if( args.length != 3 && args.length != 1) {
			this.usage(target);
			return;
		}
		
		int quantity;
		Material material;
		
		// being held by player
		if( args.length == 1) {
			// target.sendMessage("derp");
			ItemStack holding = target.getItemInHand();
			
			//target.sendMessage(holding.getType().name());
			// target.sendMessage(String.valueOf(holding.getAmount()) );
			
			target.sendMessage(holding.serialize().toString());
			
			quantity = holding.getAmount();
			material = Material.matchMaterial(holding.getType().name());
		} else {
			quantity = Integer.valueOf(args[1]);
			material = Material.matchMaterial(args[2]);
		}
		
		
		if( quantity <= 0) {
			target.sendMessage( "error: quantity is <= 0");
			return;
		}
		
		if( material == null ) {
			target.sendMessage( "error: unknown material " + args[2]);
			return;
		}
		
		BukkitTask task = new MinecraftSquareDeposit(
			new MC2_Payload(this, target, quantity, material)
		).runTaskAsynchronously(
			this
		);
	}
	
	// command: /square withdraw 10 dirt
	private void do_withdraw(Player target, String[] args) {
		if( args.length != 3) {
			this.usage(target);
			return;
		}
		
		int quantity = Integer.valueOf(args[1]);
		Material material = Material.matchMaterial(args[2]);
		
		if( quantity <= 0) {
			target.sendMessage( "error: quantity is <= 0");
			return;
		}
		
		if( material == null ) {
			target.sendMessage( "error: unknown material " + args[2]);
			return;
		}
		
		BukkitTask task = new MinecraftSquareWithdraw(
			new MC2_Payload(this, target, quantity, material)
		).runTaskAsynchronously(
			this
		);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

		// can't use as console command
		if( !(sender instanceof Player) ) return( true );
		
		Player target = sender.getServer().getPlayer(sender.getName());

		// make sure player is online
		if( target == null ) return( false );
		
		// balance in own thread
        if( cmd.getName().equalsIgnoreCase("square") ) {
        	
        	// generic - need some args
        	if( args.length == 0) {
        		this.usage(target);
        		return( true );
        	}
        	
        	if( args[0].equalsIgnoreCase("balance") ) this.do_balance(target, args);
        	if( args[0].equalsIgnoreCase("deposit") ) this.do_deposit(target, args);
        	if( args[0].equalsIgnoreCase("withdraw")) this.do_withdraw(target, args);

        }

        return true;

	}
}
