/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.kostyan.marry;

import java.util.HashMap;
import java.util.List;
import static javafx.scene.input.KeyCode.K;
import static javafx.scene.input.KeyCode.V;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author krogon500
 */
public class MarriageCommand implements CommandExecutor {
    Main plugin = Main.instance;
    public static MarriageCommand marry;
    public HashMap<String, String> invites = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        marry = this;
        Player p = (Player) cs;
        if(args.length == 2){
            if(args[0].equals("divorce")){
                    Player p2 = Bukkit.getPlayer(args[1]);
                    if(plugin.getConfig().getString(p.getName() + ".partner").equals(p2.getName())){
                        plugin.getConfig().set(p.getName() + ".partner", "NoPartner");
                        plugin.getConfig().set(p2.getName() + ".partner", "NoPartner");
                        p.sendMessage("§aВы развелись с " + p2.getName());
                        Bukkit.broadcastMessage("§cИгрок " + p.getName() + " §сразвёлся с " + p2.getName());
                        plugin.saveConfig();
                        plugin.reloadConfig();
                    } else {
                        p.sendMessage("§cВы не женаты на этом игроке!");
                    }
            }
            if(args[0].equals("invite")){
                    Player p2 = Bukkit.getPlayer(args[1]);
                    if(p2.getName() != p.getName()){
                        if(p2.isOnline()){
                            if(plugin.getConfig().getString(p.getName() + ".partner").equals("NoPartner") && plugin.getConfig().getString(p2.getName() + ".partner").equals("NoPartner")){
                                invites.put(p.getName(), p2.getName());
                                p.sendMessage("§aВы предложили игроку " + p2.getName() + " §aженится.");
                                p2.sendMessage("§aИгрок " + p.getName() + " §aпредложил вам пожениться.");
                                p2.sendMessage("§aПринять запрос §b/marry accept " + p.getName());
                                p2.sendMessage("§aОтклонить запрос §b/marry deny " + p.getName());
                            } else {
                                p.sendMessage("§cИгрок женат или вы женаты.");
                            }
                        } else {
                            p.sendMessage("§cИгрок оффлайн.");
                        }
                    } else {
                        p.sendMessage("§cВы не можете жениться на себе.");
                    }
            }
            if(args[0].equals("accept")){
                Player p2 = Bukkit.getPlayer(args[1]);
                if(p2.getName() != p.getName()){
                    if(invites.containsKey(p2.getName()) && invites.containsValue(p.getName())){
                        plugin.getConfig().set(p.getName() + ".partner", p2.getName());
                        plugin.getConfig().set(p2.getName() + ".partner", p.getName());
                        plugin.saveConfig();
                        plugin.reloadConfig();
                        invites.remove(p2.getName(), p.getName());
                        p.sendMessage("§aВы женились на " + p2.getName());
                        p2.sendMessage("§aИгрок " + p.getName() + " §aпринял ваше предложение о свадьбе");
                        Bukkit.broadcastMessage("§aИгрок " + p2.getName() + " §aженился на " + p.getName());
                    } else {
                        p.sendMessage("§cЭтот игрок вас не приглашал.");
                    }   
                } else {
                    p.sendMessage("§cВы не можете жениться на себе.");
                }
            }
            if(args[0].equals("deny")){
                Player p2 = Bukkit.getPlayer(args[1]);
                if(p2.getName() != p.getName()){
                    if(invites.containsKey(p2.getName()) && invites.containsValue(p.getName())){
                        invites.remove(p2.getName(), p.getName());
                        p.sendMessage("§cВы не стали жениться на " + p2.getName());
                        p2.sendMessage("§cИгрок " + p.getName() + " отклонил предложение о свадьбе.");
                    } else {
                        p.sendMessage("§cЭтот игрок вас не приглашал.");
                    } 
                } else {
                    p.sendMessage("§cВы не можете жениться на себе.");
                }
            }
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("help1")));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("help2")));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("help3")));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("help4")));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("help5")));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("help6")));
        }
        return false;
    }
    
}