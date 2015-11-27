/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.guilds;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGGuild {

    /**
     *
     * @param string
     * @return
     */
    private String name;                    //Name of the guild
    private String acronym;                 //reduced name of the guild (3 letters)
    private String owner;                   //Owner of the Guild
    private int level;                      //Level of the guild    
    private List<String> moderators;        //List of moderators of the Guild
    private List<String> members;           //List of members
    private List<Player> onlineMembers;     //List of players online

    public RPGGuild(String name, String acronym, Player owner) {
        this.name = name;
        this.acronym = acronym;
        this.owner = owner.getName();
        this.level = 0;
        this.moderators = new ArrayList<String>();
        this.members = new ArrayList<String>();
        this.onlineMembers= new ArrayList<>();
        this.onlineMembers.add(owner);
    }

    public RPGGuild(String name, String owner, String level, ArrayList<String> mods, ArrayList<String> members) {
        this.name = name;
        this.owner = owner;
        this.level = Integer.parseInt(level);
        this.moderators = mods;
        this.members = members;
        this.onlineMembers=new ArrayList<>();
    }

    /**
     *
     * @param member
     * @return
     */
    public boolean addMember(String member) {
        if (!this.members.contains(member) && !this.moderators.contains(member)) {
            this.members.add(member);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param member
     * @return
     */
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

    /**
     *
     * @param mod
     * @return
     */
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

    /**
     *
     * @param member
     * @return
     */
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

    /**
     *
     * @param newOwner
     * @return
     */
    public boolean changeOwner(String newOwner) {
        if (newOwner.equals(this.owner)) {
            return false;
        } else {
            this.owner = newOwner;
            return true;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getNameMembersList() {
        ArrayList<String> namelist = new ArrayList<String>();
        namelist.addAll(members);
        namelist.addAll(moderators);
        namelist.add(owner);
        return namelist;
    }

    /**
     *
     * @param message
     */
    public void sendMessageToGuild(String message) {
        for (Player p1 : onlineMembers) {            
                p1.sendMessage(message);
        }
    }
}
