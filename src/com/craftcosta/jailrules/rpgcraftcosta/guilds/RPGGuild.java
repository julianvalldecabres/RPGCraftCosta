/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.guilds;

import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import java.util.ArrayList;
import org.bukkit.entity.Player;
/**
 *
 * @author jail
 */
public class RPGGuild {

    String name;                        //Name of the guild
    String acronym;                     //reduced name of the guild (3 letters)
    RPGPlayer owner;                       //Owner of the Guild
    int level;                          //Level of the guild    
    ArrayList<RPGPlayer> moderators;       //List of moderators of the Guild
    ArrayList<RPGPlayer> members;          //List of members of the Guild

    public RPGGuild RPGGuild(String name, String acronym, RPGPlayer owner) {
        this.name = name;
        this.acronym=acronym;                       //3 letters to reduce name to be shown next to the players name
        this.owner = owner;                         //Guild owner
        this.level = 0;                             //Level of the guild
        this.moderators = new ArrayList<RPGPlayer>();  //List of moderators from the guild
        this.members = new ArrayList<RPGPlayer>();     //List of members from the guild
        return this;
    }

    public RPGGuild Guild(String name, RPGPlayer owner, String level, ArrayList<RPGPlayer> mods, ArrayList<RPGPlayer> members) {
        this.name = name;
        this.owner = owner;
        this.level = Integer.parseInt(level);
        this.moderators = mods;
        this.members = members;
        return this;
    }

    boolean addMember(RPGPlayer member) {
        if (!this.members.contains(member) && !this.moderators.contains(member)) {
            this.members.add(member);
            return true;
        } else {
            return false;
        }
    }

    boolean addMod(RPGPlayer member) {
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

    boolean depromote(RPGPlayer mod) {
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

    boolean delFromGuild(RPGPlayer member) {
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

    boolean changeOwner(RPGPlayer newOwner) {
        if (newOwner.equals(this.owner)) {
            return false;
        } else {
            this.owner = newOwner;
            return true;
        }
    }

    public ArrayList<RPGPlayer> getAllMembers() {
        ArrayList<RPGPlayer> listplayers = new ArrayList<RPGPlayer>();
        for (RPGPlayer player : members) {
            if(player.getPlayer().isOnline())
            listplayers.add(player);
        }
        for (RPGPlayer player : moderators) {
            if(player.getPlayer().isOnline())
            listplayers.add(player);
        }
        if(owner.getPlayer().isOnline())
        listplayers.add(owner);
        return listplayers;
    }

    public void sendMessageToGuild(String message) {
        ArrayList<RPGPlayer> memberslist=getAllMembers();
        for (RPGPlayer member : members) {
            member.getPlayer().sendMessage(message);
        }                
    }
}
