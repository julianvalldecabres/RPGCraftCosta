/* 
 * Copyright 2016 jail.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craftcosta.jailrules.rpgcraftcosta.scoreboard;

import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 *
 * @author jail
 */
public class ScoreBoardManager {

    /**
     *
     * @param p
     */
    public static void createScoreBoard(RPGPlayer p) {
        ScoreboardManager sbm = Bukkit.getServer().getScoreboardManager();
        Scoreboard sb = sbm.getNewScoreboard();

        Objective obj = sb.registerNewObjective("test", "dummy");
        obj.setDisplayName(ChatColor.GREEN + "Stats");

        Score health = obj.getScore(ChatColor.RED + "health");
        Score mana = obj.getScore(ChatColor.RED + "mana");
        Score money = obj.getScore(ChatColor.RED + "money");
        Score level = obj.getScore(ChatColor.RED + "level");
        Score exp = obj.getScore(ChatColor.RED + "exp");

        health.setScore(ScoreBoardManager.getHealth(p));
        mana.setScore(ScoreBoardManager.getMana(p));
        money.setScore(ScoreBoardManager.getMoney(p));
        level.setScore(ScoreBoardManager.getLevel(p));

        p.getPlayer().setScoreboard(sb);
    }

    /**
     *
     * @param p
     * @return
     */
    public static int getHealth(RPGPlayer p) {
        File health = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(health);
        int back = stats.getInt(p.getPlayer().getName() + ".Health");
        return back;
    }

    /**
     *
     * @param p
     * @return
     */
    public static int getMana(RPGPlayer p) {
        File mana = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(mana);
        int back = stats.getInt(p.getPlayer().getName() + ".Mana");
        return back;
    }

    /**
     *
     * @param p
     * @return
     */
    public static int getMoney(RPGPlayer p) {
        File money = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(money);
        int back = stats.getInt(p.getPlayer().getName() + ".Money");
        return back;
    }

    /**
     *
     * @param p
     * @return
     */
    public static int getLevel(RPGPlayer p) {
        File level = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(level);
        int back = stats.getInt(p.getPlayer().getName() + ".Level");
        return back;
    }

    /**
     *
     * @param p
     * @throws IOException
     */
    public static void setHealth(RPGPlayer p) throws IOException {
        File health = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(health);
        int back = stats.getInt(p.getPlayer().getName() + ".Health");
        stats.set(p.getPlayer().getName() + ".Health", back);
        stats.save(health);
    }

    /**
     *
     * @param p
     * @throws IOException
     */
    public static void setMana(RPGPlayer p) throws IOException {
        File mana = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(mana);
        int back = stats.getInt(p.getPlayer().getName() + ".Mana");
        stats.set(p.getPlayer().getName() + ".Mana", back);
        stats.save(mana);
    }

    /**
     *
     * @param p
     * @throws IOException
     */
    public static void setMoney(RPGPlayer p) throws IOException {
        File money = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(money);
        int back = stats.getInt(p.getPlayer().getName() + ".Money");
        stats.set(p.getPlayer().getName() + ".Money", back);
        stats.save(money);
    }

    /**
     *
     * @param p
     * @throws IOException
     */
    public static void setLevel(RPGPlayer p) throws IOException {
        File level = new File("plugins/RPGCraftCosta", "ScoreboardStats.yml");
        FileConfiguration stats = YamlConfiguration.loadConfiguration(level);
        int back = stats.getInt(p.getPlayer().getName() + ".Level");
        stats.set(p.getPlayer().getName() + ".Level", back);
        stats.save(level);
    }
}
