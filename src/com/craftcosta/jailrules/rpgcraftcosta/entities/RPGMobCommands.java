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
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGMobCommands implements CommandExecutor {

    private RPGCraftCosta plugin;
    RPGMobManager rpgMMan;

    /**
     *
     * @param plugin
     */
    public RPGMobCommands(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgMMan= plugin.getRPGMobManager();
    }

    /**
     * arg[0] mob type arg[1] world arg[2-4]coords arg[5] mt arg[6] name arg[7]
     * limit arg[8] follow arg[9] attack arg[10] movspeed arg[11] knockback
     * arg[12] maxhealth arg[13] attackspeed
     */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
        Player p = null;
        if(sender instanceof Player){
            p=(Player) sender;
        }
        Location loc= p.getLocation();
        if(label.equalsIgnoreCase("sumc")){
            if(arg.length==1){
                if(arg[0].equalsIgnoreCase("list")){
                    for(String s:rpgMMan.mobList.keySet()){
                        p.sendMessage(s);
                    }
                }
            }else{
                String mob="";
                for(int i=0;i<=arg.length-2;i++){
                    mob+=arg[i]+" ";
                }
                mob+=arg[arg.length-1];
                RPGMob rpgm=rpgMMan.mobList.get(mob);
                if(rpgm!=null){
                    rpgMMan.spawnRPGMobAtLocation(rpgm, loc);
                }else{
                    p.sendMessage(ChatColor.RED+""+arg+" no existe");
                }
            }
        }
    
        return true;
    }

}
