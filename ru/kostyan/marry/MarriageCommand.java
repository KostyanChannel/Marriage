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
                        p.sendMessage("�a�� ��������� � " + p2.getName());
                        Bukkit.broadcastMessage("�c����� " + p.getName() + " ��������� � " + p2.getName());
                        plugin.saveConfig();
                        plugin.reloadConfig();
                    } else {
                        p.sendMessage("�c�� �� ������ �� ���� ������!");
                    }
            }
            if(args[0].equals("invite")){
                    Player p2 = Bukkit.getPlayer(args[1]);
                    if(p2.getName() != p.getName()){
                        if(p2.isOnline()){
                            if(plugin.getConfig().getString(p.getName() + ".partner").equals("NoPartner") && plugin.getConfig().getString(p2.getName() + ".partner").equals("NoPartner")){
                                invites.put(p.getName(), p2.getName());
                                p.sendMessage("�a�� ���������� ������ " + p2.getName() + " �a�������.");
                                p2.sendMessage("�a����� " + p.getName() + " �a��������� ��� ����������.");
                                p2.sendMessage("�a������� ������ �b/marry accept " + p.getName());
                                p2.sendMessage("�a��������� ������ �b/marry deny " + p.getName());
                            } else {
                                p.sendMessage("�c����� ����� ��� �� ������.");
                            }
                        } else {
                            p.sendMessage("�c����� �������.");
                        }
                    } else {
                        p.sendMessage("�c�� �� ������ �������� �� ����.");
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
                        p.sendMessage("�a�� �������� �� " + p2.getName());
                        p2.sendMessage("�a����� " + p.getName() + " �a������ ���� ����������� � �������");
                        Bukkit.broadcastMessage("�a����� " + p2.getName() + " �a������� �� " + p.getName());
                    } else {
                        p.sendMessage("�c���� ����� ��� �� ���������.");
                    }   
                } else {
                    p.sendMessage("�c�� �� ������ �������� �� ����.");
                }
            }
            if(args[0].equals("deny")){
                Player p2 = Bukkit.getPlayer(args[1]);
                if(p2.getName() != p.getName()){
                    if(invites.containsKey(p2.getName()) && invites.containsValue(p.getName())){
                        invites.remove(p2.getName(), p.getName());
                        p.sendMessage("�c�� �� ����� �������� �� " + p2.getName());
                        p2.sendMessage("�c����� " + p.getName() + " �������� ����������� � �������.");
                    } else {
                        p.sendMessage("�c���� ����� ��� �� ���������.");
                    } 
                } else {
                    p.sendMessage("�c�� �� ������ �������� �� ����.");
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