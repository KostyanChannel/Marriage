/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.kostyan.marry;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author krogon500
 */
public class MChat implements CommandExecutor {
    Main plugin = Main.instance;
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        Player p = (Player) cs;
        if(args.length != 0){
            Player partner = Bukkit.getPlayerExact(plugin.getConfig().getString(p.getName() + ".partner"));
            StringBuilder msg = new StringBuilder(" ");
            for(int i = 0; i < args.length; i++){
                msg.append(args[i] + " ");
            }
            partner.sendMessage("§7[§f" + p.getName() + "§7]§8: §a" + msg);
            p.sendMessage("§7[§f" + p.getName() + "§7]§8:§a" + msg);
        } else {
            p.sendMessage("§cÂû íè÷åãî íå íàïèñàëè!");
        }
        return false;
    }
}
