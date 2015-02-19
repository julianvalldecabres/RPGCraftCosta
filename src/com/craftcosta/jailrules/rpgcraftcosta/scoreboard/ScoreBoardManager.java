/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.scoreboard;

import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 *
 * @author jail
 */
public class ScoreBoardManager {

    public static void createScoreBoard(RPGPlayer p) {
        ScoreboardManager sbm = Bukkit.getServer().getScoreboardManager();
        Scoreboard sb = sbm.getNewScoreboard();

        Objective obj = sb.registerNewObjective("aaa", "bbb");
        obj.setDisplayName(ChatColor.GREEN + "Stats");

        Score health = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "health"));
        Score mana = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "mana"));
        Score money = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "money"));
        Score level = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "level"));

        health.setScore(ScoreBoardManager.getHealth(p));
        mana.setScore(ScoreBoardManager.getMana(p));
        money.setScore(ScoreBoardManager.getMoney(p));
        level.setScore(ScoreBoardManager.getLevel(p));

        p.getPlayer().setScoreboard(sb);
    }

    public static int getHealth(RPGPlayer p) {
        File health = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(health);
        int back = stats.getInt(p.getPlayer().getName() + ".Health");
        return back;
    }

    public static int getMana(RPGPlayer p) {
        File mana = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(mana);
        int back = stats.getInt(p.getPlayer().getName() + ".Mana");
        return back;
    }

    public static int getMoney(RPGPlayer p) {
        File money = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(money);
        int back = stats.getInt(p.getPlayer().getName() + ".Money");
        return back;
    }

    public static int getLevel(RPGPlayer p) {
        File level = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(level);
        int back = stats.getInt(p.getPlayer().getName() + ".Level");
        return back;
    }

    public static void setHealth(RPGPlayer p) throws IOException {
        File health = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(health);
        int back = stats.getInt(p.getPlayer().getName() + ".Health");
        stats.set(p.getPlayer().getName()+".Health",back);
        stats.save(health);
    }

    public static void setMana(RPGPlayer p) throws IOException {
        File mana = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(mana);
        int back = stats.getInt(p.getPlayer().getName() + ".Mana");
        stats.set(p.getPlayer().getName()+".Mana",back);
        stats.save(mana);
    }

    public static void setMoney(RPGPlayer p) throws IOException {
        File money = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(money);
        int back = stats.getInt(p.getPlayer().getName() + ".Money");
        stats.set(p.getPlayer().getName()+".Money",back);
        stats.save(money);
    }

    public static void setLevel(RPGPlayer p) throws IOException {
        File level = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(level);
        int back = stats.getInt(p.getPlayer().getName() + ".Level");
        stats.set(p.getPlayer().getName()+".Level",back);
        stats.save(level);
    }
}
