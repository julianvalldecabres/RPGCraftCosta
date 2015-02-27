/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.guilds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
/**
 *
 * @author jail
 */
public class RPGGuild {
    String name;                        //Name of the guild
    String acronym;                     //reduced name of the guild (3 letters)
    String owner;                       //Owner of the Guild
    int level;                          //Level of the guild    
    ArrayList<String> moderators;       //List of moderators of the Guild
    ArrayList<String> members;
    //hashmap<String(Player),String(Rango)>
//List of members of the Guild
    private Object plugin;
    private Object user;

    public RPGGuild RPGGuild(String name, String acronym, String owner) {
        this.name = name;
        this.acronym=acronym;                       //3 letters to reduce name to be shown next to the players name
        this.owner = owner;                         //Guild owner
        this.level = 0;                             //Level of the guild
        this.moderators = new ArrayList<String>();  //List of moderators from the guild
        this.members = new ArrayList<String>();     //List of members from the guild
        return this;
    }

    public RPGGuild Guild(String name, String owner, String level, ArrayList<String> mods, ArrayList<String> members) {
        this.name = name;
        this.owner = owner;
        this.level = Integer.parseInt(level);
        this.moderators = mods;
        this.members = members;
        return this;
    }

    public boolean addMember(String member) {
        if (!this.members.contains(member) && !this.moderators.contains(member)) {
            this.members.add(member);
            return true;
        } else {
            return false;
        }
    }

    public boolean addMod(String member) {
        if (this.moderators.contains(member)) {
            return false;
        } else {
            if (this.members.contains(member)) {
                this.members.remove(member);
            }
            this.moderators.add(member);
        }
        return true;
    }

    public boolean depromote(String mod) {
        if (this.members.contains(mod)) {
            return false;
        }
        if (this.moderators.contains(mod)) {
            this.moderators.remove(mod);
            this.members.add(mod);
            return true;
        }
        return false;
    }

    public boolean delFromGuild(String member) {
        if (this.moderators.contains(member)) {
            this.moderators.remove(member);
            return true;
        }
        if (this.members.contains(member)) {
            this.members.remove(member);
            return true;
        }
        return false;
    }

    public boolean changeOwner(String newOwner) {
        if (newOwner.equals(this.owner)) {
            return false;
        } else {
            this.owner = newOwner;
            return true;
        }
    }
    
    public ArrayList<String> getNameMembersList(){
        ArrayList<String> namelist=new ArrayList<String>();
        namelist.addAll(members);
        namelist.addAll(moderators);
        namelist.add(owner);
        return namelist;
    }

    public void sendMessageToGuild(String message) {
        Set<Player>onlineplayers= new HashSet<Player> (Arrays.asList(Bukkit.getServer().getOnlinePlayers()));
        ArrayList<String> names=getNameMembersList();
        for (Player p1 : onlineplayers){
            if(names.contains(p1.getName())){
                p1.sendMessage(message);
            }            
        }
    }
 }
