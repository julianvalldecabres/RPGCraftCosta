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
    private String owner;                   //Owner of the Guild
    private int level;                      //Level of the guild    
    private double money;                      //Guild money
    private List<String> members;           //List of members
    private List<Player> onlineMembers;     //List of players online

    /**
     *
     * @param name
     * @param owner
     */
    public RPGGuild(String name, Player owner) {
        this.name = name;
        this.owner = owner.getName();
        this.level = 0;
        this.money = 0;
        this.members = new ArrayList<>();
        this.onlineMembers = new ArrayList<>();
        this.onlineMembers.add(owner);
        this.members.add(owner.getName());
    }

    /**
     *
     * @param name
     * @param owner
     * @param level
     * @param money
     * @param mods
     * @param members
     */
    public RPGGuild(String name, String owner, int level, double money, List<String> members) {
        this.name = name;
        this.owner = owner;
        this.level = level;
        this.money = money;
        this.members = members;
        this.onlineMembers = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     *
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     *
     * @return
     */
    public double getMoney() {
        return money;
    }

    /**
     *
     * @param money
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     *
     * @return
     */
    public List<String> getMembers() {
        return members;
    }

    /**
     *
     * @param members
     */
    public void setMembers(List<String> members) {
        this.members = members;
    }

    /**
     *
     * @return
     */
    public List<Player> getOnlineMembers() {
        return onlineMembers;
    }

    /**
     *
     * @param onlineMembers
     */
    public void setOnlineMembers(List<Player> onlineMembers) {
        this.onlineMembers = onlineMembers;
    }

    /**
     *
     * @param member
     * @return
     */
    public boolean addMember(Player member) {
        if (!this.members.contains(member.getName())) {
            this.members.add(member.getName());
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
    public boolean delFromGuild(String member) {
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
        namelist.add(owner);
        return namelist;
    }

    public void subOnlinePlayer(Player p) {
        this.onlineMembers.remove(p);
    }

    public void leavePlayerFromGuild(Player p) {
        this.members.remove(p.getName());
        this.onlineMembers.remove(p);
    }

    public void addOnlinePlayer(Player p) {
        this.onlineMembers.add(p);
    }

    public void delFromGuild(Player kickplayer) {
        if (this.members.contains(kickplayer.getName())) {
            this.members.remove(kickplayer.getName());
        }
        if (this.onlineMembers.contains(kickplayer)) {
            this.onlineMembers.remove(kickplayer);
        }
    }

    public void sendMessageToGuildPlayer(Player p, String message) {
        p.sendMessage(message);
    }
}
