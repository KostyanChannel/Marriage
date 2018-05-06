/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.kostyan.marry;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author krogon500
 */
public class Main extends JavaPlugin implements Listener {
    public static Main instance;
    MarriageCommand mcmd = MarriageCommand.marry;
    
    public void onEnable(){
        instance = this;
        this.saveDefaultConfig();
        this.saveConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("marry").setExecutor(new MarriageCommand());
        this.getCommand("mchat").setExecutor(new MChat());
        this.getCommand("mgift").setExecutor(new MGift());
    }
    
    public void onDisable(){
        instance = null;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(getConfig().contains(e.getPlayer().getName())){
            getLogger().warning("Player " + e.getPlayer().getName() + " exists in config.");
        } else {
            getConfig().set(e.getPlayer().getName() + ".partner", "NoPartner");
            this.saveConfig();
            this.reloadConfig();
        }
    }
    
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        if(mcmd.invites.containsKey(e.getPlayer().getName()) || mcmd.invites.containsValue(e.getPlayer().getName())){
            mcmd.invites.remove(e.getPlayer().getName(), mcmd.invites.get(e.getPlayer().getName()));
        }
    }
}
