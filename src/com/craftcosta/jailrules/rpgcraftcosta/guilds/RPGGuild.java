/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.guilds;

import java.util.ArrayList;
import java.util.List;
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
    private double money;                      //Guild money
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

    public RPGGuild(String name, String acronym,String owner, int level,double money, List<String> mods, List<String> members) {
        this.name = name;
        this.acronym= acronym;
        this.owner = owner;
        this.level = level;
        this.money= money;
        this.moderators = mods;
        this.members = members;
        this.onlineMembers=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<String> getModerators() {
        return moderators;
    }

    public void setModerators(List<String> moderators) {
        this.moderators = moderators;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<Player> getOnlineMembers() {
        return onlineMembers;
    }

    public void setOnlineMembers(List<Player> onlineMembers) {
        this.onlineMembers = onlineMembers;
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
