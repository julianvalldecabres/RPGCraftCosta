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
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class MobCommands implements CommandExecutor {

    private RPGCraftCosta plugin;

    /**
     *
     * @param plugin
     */
    public MobCommands(RPGCraftCosta plugin) {
        this.plugin = plugin;
    }

    /**
     * arg[0] mob type arg[1] world arg[2-4]coords arg[5] mt arg[6] name arg[7]
     * limit arg[8] follow arg[9] attack arg[10] movspeed arg[11] knockback
     * arg[12] maxhealth arg[13] attackspeed
     */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
        Player p = (Player) sender;
        Location loc;
        int limit;
        double follow;
        double attack;
        double movspeed;
        double attackspeed;
        double knockback;
        double maxhealth;
        String name;
        if (arg.length == 14) {
            if (cmd.getName().equalsIgnoreCase("sumC")) {
                if (plugin.getServer().getWorld(arg[1]) == null) {
                    p.sendMessage("mundo errorneo");
                    return true;
                }
                try {
                    int x = Integer.parseInt(arg[2]);
                    int y = Integer.parseInt(arg[3]);
                    int z = Integer.parseInt(arg[4]);
                    loc = new Location(plugin.getServer().getWorld(arg[1]), x, y, z);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    p.sendMessage("formato de coordenadas incorrecto");
                    return true;
                }
                MobBehaviour mt;
                switch (arg[5].toLowerCase()) {
                    case "aggressive":
                        mt = MobBehaviour.AGGRESSIVE;
                        break;
                    case "aggreaggro":
                        mt = MobBehaviour.AGGREAGGRO;
                        break;
                    case "normal":
                        mt = MobBehaviour.NORMAL;
                        break;
                    case "normaggro":
                        mt = MobBehaviour.NORMAGGRO;
                        break;
                    case "peaceful":
                        mt = MobBehaviour.PEACEFUL;
                        break;
                    default:
                        p.sendMessage("mobtype incorrecto");
                        return true;
                }
                if (arg[6].length() >= 17 && arg[6].matches("([a-z]|[A-Z]|\\s)+")) {
                    p.sendMessage("nombre demasiado largo o con caracteres especiales");
                    return true;
                }
                name = arg[6];
                try {
                    limit = Integer.parseInt(arg[7]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    p.sendMessage("limit debe ser un numero");
                    return true;
                }
                try {
                    follow = Double.parseDouble(arg[8]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    p.sendMessage("follow debe ser un numero");
                    return true;
                }
                try {
                    attack = Double.parseDouble(arg[9]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    p.sendMessage("attack debe ser un numero");
                    return true;
                }
                try {
                    movspeed = Double.parseDouble(arg[10]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    p.sendMessage("movspeed debe ser un numero");
                    return true;
                }
                try {
                    knockback = Double.parseDouble(arg[11]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    p.sendMessage("knockback debe ser un numero");
                    return true;
                }
                try {
                    maxhealth = Double.parseDouble(arg[12]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    p.sendMessage("vida maxima debe ser un numero");
                    return true;
                }
                try {
                    attackspeed = Double.parseDouble(arg[13]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    p.sendMessage("velocidad de ataque debe ser un numero");
                    return true;
                }

                switch (arg[0].toLowerCase()) {
                    case "cavespider":
                        //CustomEntityCaveSpider ent = new CustomEntityCaveSpider(loc, mt, name, limit, follow, attack, movspeed, knockback, maxhealth, attackspeed);
                        break;
                }
            }
        }

        return false;
    }

}
