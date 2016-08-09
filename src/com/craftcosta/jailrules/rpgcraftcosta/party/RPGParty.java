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
package com.craftcosta.jailrules.rpgcraftcosta.party;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGParty {

    private String name;
    private String leader;
    private List<Player> players;
    private boolean shareExp;
    private boolean shareMoney;
    private boolean pvpEnabled;

    /**
     *
     * @param name
     * @param creator
     * @param shareExp
     * @param shareMoney
     */
    public RPGParty(String name, Player creator) {
        this.players = new ArrayList<>();
        this.name = name;
        this.leader = creator.getName();
        this.players.add(creator);
        this.pvpEnabled = false;
    }

    /**
     *
     * @param name
     * @param creator
     * @param shareExp
     * @param shareMoney
     */
    public RPGParty(String name, Player creator, boolean shareExp, boolean shareMoney) {
        this.players = new ArrayList<>();
        this.name = name;
        this.leader = creator.getName();
        this.players.add(creator);
        this.shareExp = shareExp;
        this.shareMoney = shareMoney;
        this.pvpEnabled = false;
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
    public String getLeader() {
        return leader;
    }

    /**
     *
     * @param creator
     */
    public void setLeader(String leader) {
        this.leader = leader;
    }

    /**
     *
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @param players
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     *
     * @return
     */
    public boolean isShareExp() {
        return shareExp;
    }

    /**
     *
     * @param shareExp
     */
    public void setShareExp(boolean shareExp) {
        this.shareExp = shareExp;
    }

    /**
     *
     * @return
     */
    public boolean isShareMoney() {
        return shareMoney;
    }

    /**
     *
     * @param shareMoney
     */
    public void setShareMoney(boolean shareMoney) {
        this.shareMoney = shareMoney;
    }

    /**
     *
     * @param player
     */
    public void invitePlayerToParty(Player player) {
        this.players.add(player);
    }

    /**
     *
     * @param player
     */
    public void leavePlayerFromParty(Player player) {
        this.players.remove(player);
    }

    /**
     *
     * @param shareXP
     * @param shareMoney
     */
    public void changeAllConfig(boolean shareXP, boolean shareMoney) {
        setShareExp(shareXP);
        setShareMoney(shareMoney);
    }

    /**
     *
     * @param kickplayer
     */
    public void kickplayer(Player kickplayer) {
        this.players.remove(kickplayer);
    }

    /**
     *
     * @param p
     */
    public void addPlayerToParty(Player p) {
        this.players.add(p);
    }

    /**
     *
     * @return
     */
    public boolean isPvpEnabled() {
        return this.pvpEnabled;
    }

    /**
     *
     * @param value
     */
    public void setPvpEnabled(boolean value) {
        this.pvpEnabled = value;
    }
}
