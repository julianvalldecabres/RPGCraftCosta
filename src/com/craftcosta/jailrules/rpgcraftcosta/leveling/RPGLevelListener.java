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
package com.craftcosta.jailrules.rpgcraftcosta.leveling;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassManager;
import com.craftcosta.jailrules.rpgcraftcosta.economy.NegativeMoneyException;
import com.craftcosta.jailrules.rpgcraftcosta.entities.RPGMob;
import com.craftcosta.jailrules.rpgcraftcosta.entities.RPGMobManager;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildManager;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGPartyManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 * @author jail
 */
public class RPGLevelListener implements Listener {

    private RPGCraftCosta plugin;
    private RPGLevelManager rpgLMan;
    private RPGPlayerManager rpgPMan;
    private RPGMobManager rpgMMan;
    private RPGClassManager rpgCMan;
    private RPGGuildManager rpgGMan;
    private RPGPartyManager rpgPaMan;

    public RPGLevelListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgLMan = plugin.getRPGLevelManager();
        this.rpgMMan = plugin.getRPGMobManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.rpgCMan = plugin.getRPGClassManager();
        this.rpgPaMan = plugin.getRPGPartyManager();
        this.rpgGMan = plugin.getRPGGuildManager();

    }

    @EventHandler
    public void onPlayerKillsMonster(EntityDeathEvent e) {
        Entity ent = e.getEntity();
        rpgMMan.removeUUIDfromSpawner(ent);
        if (ent.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) ent.getLastDamageCause();
            if (nEvent.getDamager() instanceof Player) {
                RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(((Player) nEvent.getDamager()).getName());
                if (!(nEvent.getEntity() instanceof Player)) {
                    String mobName = nEvent.getEntity().getCustomName();
                    RPGMob mob = rpgMMan.getRPGMobByName(mobName);
                    plugin.getLogger().info(mobName);
                    long mobexp = mob.getExp();
                    long mobmoney = mob.getMoney();
                    if (!rpgp.getGuild().isEmpty()) {
                        if (!rpgp.getParty().isEmpty()) {

                            try {
                                if (!rpgGMan.isFixedPlayers()) {
                                    mobmoney -= (long) (mobmoney * rpgGMan.getContributionkills());
                                    rpgGMan.addMoneyToGuild(rpgGMan.getGuildByName(rpgp.getGuild()), (long) (mobmoney * rpgGMan.getContributionkills()));
                                }
                                switch (rpgPaMan.getShareOptions()) {
                                    case "none":
                                        rpgp.getEcon().addMoney(mobmoney);
                                        if (rpgLMan.checkLevelUp(rpgp.getActualExp(), mobexp)) {
                                            rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                            rpgCMan.levelUP(rpgp);
                                        } else {
                                            rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                        }
                                        break;
                                    case "money":
                                        rpgPaMan.shareWithParty(rpgPaMan.getParty(rpgp.getParty()), 0, mobmoney);
                                        if (rpgLMan.checkLevelUp(rpgp.getActualExp(), mobexp)) {
                                            rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                            rpgCMan.levelUP(rpgp);
                                        } else {
                                            rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                        }
                                        break;
                                    case "exp":
                                        rpgp.getEcon().addMoney(mobmoney);
                                        rpgPaMan.shareWithParty(rpgPaMan.getParty(rpgp.getParty()), mobexp, 0);
                                        break;
                                    case "both":
                                        rpgPaMan.shareWithParty(rpgPaMan.getParty(rpgp.getParty()), mobexp, mobmoney);
                                        break;
                                }
                            } catch (NegativeMoneyException ex) {
                                Logger.getLogger(RPGLevelListener.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } else {
                            try {
                                if (!rpgGMan.isFixedPlayers()) {
                                    mobmoney -= (long) (mobmoney * rpgGMan.getContributionkills());
                                    rpgGMan.addMoneyToGuild(rpgGMan.getGuildByName(rpgp.getGuild()), (long) (mobmoney * rpgGMan.getContributionkills()));
                                }
                                rpgp.getEcon().addMoney(mobmoney);
                                if (rpgLMan.checkLevelUp(rpgp.getActualExp(), mobexp)) {
                                    rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                    rpgCMan.levelUP(rpgp);
                                } else {
                                    rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                }
                            } catch (NegativeMoneyException ex) {
                                Logger.getLogger(RPGLevelListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } else {
                        if (!rpgp.getParty().isEmpty()) {
                            switch (rpgPaMan.getShareOptions()) {
                                case "none": {
                                    try {
                                        rpgp.getEcon().addMoney(mobmoney);
                                    } catch (NegativeMoneyException ex) {
                                        Logger.getLogger(RPGLevelListener.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                if (rpgLMan.checkLevelUp(rpgp.getActualExp(), mobexp)) {
                                    rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                    rpgCMan.levelUP(rpgp);
                                } else {
                                    rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                }
                                break;
                                case "money":
                                    rpgPaMan.shareWithParty(rpgPaMan.getParty(rpgp.getParty()), 0, mobmoney);
                                    if (rpgLMan.checkLevelUp(rpgp.getActualExp(), mobexp)) {
                                        rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                        rpgCMan.levelUP(rpgp);
                                    } else {
                                        rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                    }
                                    break;
                                case "exp": {
                                    try {
                                        rpgp.getEcon().addMoney(mobmoney);
                                    } catch (NegativeMoneyException ex) {
                                        Logger.getLogger(RPGLevelListener.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                rpgPaMan.shareWithParty(rpgPaMan.getParty(rpgp.getParty()), mobexp, 0);
                                break;
                                case "both":
                                    rpgPaMan.shareWithParty(rpgPaMan.getParty(rpgp.getParty()), mobexp, mobmoney);
                                    break;
                            }
                        } else {
                            try {
                                rpgp.getEcon().addMoney(mobmoney);
                                if (rpgLMan.checkLevelUp(rpgp.getActualExp(), mobexp)) {
                                    rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                    rpgCMan.levelUP(rpgp);
                                } else {
                                    rpgp.setActualExp(rpgp.getActualExp() + mobexp);
                                }
                            } catch (NegativeMoneyException ex) {
                                Logger.getLogger(RPGLevelListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
    }
}
