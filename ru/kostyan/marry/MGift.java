/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.kostyan.marry;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author krogon500
 */
public class MGift implements CommandExecutor {
    Main plugin = Main.instance;

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        Player p = (Player) cs;
        ItemStack gift = new ItemStack(p.getItemInHand().getTypeId(), p.getItemInHand().getAmount());
        Player partner = Bukkit.getPlayerExact(plugin.getConfig().getString(p.getName() + ".partner"));
        if(!plugin.getConfig().getString(p.getName() + ".partner").equals("NoPartner")){
            if(!p.getItemInHand().getType().equals(Material.AIR)){
                if(partner.isOnline()){
                    partner.getInventory().addItem(gift);
                    p.getInventory().removeItem(p.getItemInHand());
                    partner.sendMessage("�a��� ������ �������� ��� �������!");
                    p.sendMessage("�a�� ��������� ������� �������");
                } else {
                    p.sendMessage("�c������ �� � ����!");
                }
            } else {
                p.sendMessage("�c� ��� ������ ��� � �����!");
            }
        } else {
                p.sendMessage("�c�� �� ������!");
        }
        return false;
    }
}