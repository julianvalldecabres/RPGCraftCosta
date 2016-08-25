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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.chat;

import com.craftcosta.jailrules.rpgcraftcosta.chat.MessageType;
import com.craftcosta.jailrules.rpgcraftcosta.gui.GUI;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class GUIChatManager {

    private GUI gui;
    private boolean globalenabled;
    private boolean guildenabled;
    private boolean partyenabled;
    private boolean marketenabled;
    private boolean privateenabled;
    private int maxdistance;
    private ChatColor playerclasscolor;
    private ChatColor playernamecolor;
    private ChatColor locationcolor;
    private File file;
    private FileConfiguration fileConfig;

    public GUIChatManager(GUI gui) {
        this.gui = gui;
        file = new File(RPGFinals.chatFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GUIChatManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadChatConfig();
    }

    private void loadChatConfig() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
        this.globalenabled = fileConfig.getBoolean("globalenabled");
        gui.recursivelyEnableDisablePanel(gui.getPanelConfigGlobal(), globalenabled);
        gui.getCheckEnableGlobalChat().setEnabled(globalenabled);
        this.marketenabled = fileConfig.getBoolean("marketenabled");
        gui.recursivelyEnableDisablePanel(gui.getPanelConfigMarketChat(), marketenabled);
        gui.getCheckEnableMarketChat().setEnabled(marketenabled);
        this.guildenabled = fileConfig.getBoolean("guildenabled");
        gui.recursivelyEnableDisablePanel(gui.getPanelConfigGuild(), guildenabled);
        gui.getCheckGuildChat().setEnabled(guildenabled);
        this.partyenabled = fileConfig.getBoolean("partyenabled");
        gui.recursivelyEnableDisablePanel(gui.getPanelConfigParty(), partyenabled);
        gui.getCheckPartyChat().setEnabled(partyenabled);
        this.privateenabled = fileConfig.getBoolean("privateenabled");
        gui.recursivelyEnableDisablePanel(gui.getPanelConfiglPrivate(), privateenabled);
        gui.getCheckPrivateChat().setEnabled(privateenabled);
        this.maxdistance = fileConfig.getInt("maxdistance");
        gui.getSpinnerDistanceLocalChat().setValue(this.maxdistance);
        this.playerclasscolor = ChatColor.valueOf(fileConfig.getString("playerclasscolor"));
        this.playernamecolor = ChatColor.valueOf(fileConfig.getString("playernamecolor"));
        this.locationcolor = ChatColor.valueOf(fileConfig.getString("locationcolor"));
        //WARNING
        ConfigurationSection section = fileConfig.getConfigurationSection("WARNING");
        gui.getTextPrefixWarningChat().setText(section.getString("prefix"));
        gui.getComboShortcutWarningChat().setSelectedIndex(gui.indiceCombo(gui.getComboShortcutWarningChat(), section.getString("shortcut")));
        gui.getComboPrefixColorWarningChat().setSelectedIndex(ChatColor.valueOf(section.getString("prefixcolor")).ordinal());
        gui.getComboMessageColorWarningChat().setSelectedIndex(ChatColor.valueOf(section.getString("messagecolor")).ordinal());
        //NEWS
        section = fileConfig.getConfigurationSection("NEWS");
        gui.getTextPrefixNewsChat().setText(section.getString("prefix"));
        gui.getComboShortcutNewsChat().setSelectedIndex(gui.indiceCombo(gui.getComboShortcutNewsChat(), section.getString("shortcut")));
        gui.getComboPrefixColorNewsChat().setSelectedIndex(ChatColor.valueOf(section.getString("prefixcolor")).ordinal());
        gui.getComboMessageColorNewsChat().setSelectedIndex(ChatColor.valueOf(section.getString("messagecolor")).ordinal());
        //PRIVATE
        section = fileConfig.getConfigurationSection("PRIVATE");
        gui.getTextPrefixPrivateChat().setText(section.getString("prefix"));
        gui.getComboShortcutPrivateChat().setSelectedIndex(gui.indiceCombo(gui.getComboShortcutPrivateChat(), section.getString("shortcut")));
        gui.getComboPrefixColorPrivateChat().setSelectedIndex(ChatColor.valueOf(section.getString("prefixcolor")).ordinal());
        gui.getComboMessageColorPrivateChat().setSelectedIndex(ChatColor.valueOf(section.getString("messagecolor")).ordinal());
        //GUILD
        section = fileConfig.getConfigurationSection("GUILD");
        gui.getTextPrefixGuildChat().setText(section.getString("prefix"));
        gui.getComboShortcutGuildChat().setSelectedIndex(gui.indiceCombo(gui.getComboShortcutGuildChat(), section.getString("shortcut")));
        gui.getComboPrefixColorGuildChat().setSelectedIndex(ChatColor.valueOf(section.getString("prefixcolor")).ordinal());
        gui.getComboMessageColorGuildChat().setSelectedIndex(ChatColor.valueOf(section.getString("messagecolor")).ordinal());
        //PARTY
        section = fileConfig.getConfigurationSection("NEWS");
        gui.getTextPrefixPartyChat().setText(section.getString("prefix"));
        gui.getComboShortcutPartyChat().setSelectedIndex(gui.indiceCombo(gui.getComboShortcutPartyChat(), section.getString("shortcut")));
        gui.getComboPrefixColorPartyChat().setSelectedIndex(ChatColor.valueOf(section.getString("prefixcolor")).ordinal());
        gui.getComboMessageColorPartyChat().setSelectedIndex(ChatColor.valueOf(section.getString("messagecolor")).ordinal());
        //MARKET
        section = fileConfig.getConfigurationSection("MARKET");
        gui.getTextPrefixMarketChat().setText(section.getString("prefix"));
        gui.getComboShortcutMarketChat().setSelectedIndex(gui.indiceCombo(gui.getComboShortcutMarketChat(), section.getString("shortcut")));
        gui.getComboPrefixColorMarketChat().setSelectedIndex(ChatColor.valueOf(section.getString("prefixcolor")).ordinal());
        gui.getComboMessageColorMarketChat().setSelectedIndex(ChatColor.valueOf(section.getString("messagecolor")).ordinal());
        //GLOBAL
        section = fileConfig.getConfigurationSection("GLOBAL");
        gui.getTextPrefixGlobalChat().setText(section.getString("prefix"));
        gui.getComboShortcutGlobalChat().setSelectedIndex(gui.indiceCombo(gui.getComboShortcutGlobalChat(), section.getString("shortcut")));
        gui.getComboPrefixColorGlobalChat().setSelectedIndex(ChatColor.valueOf(section.getString("prefixcolor")).ordinal());
        gui.getComboMessageColorGlobalChat().setSelectedIndex(ChatColor.valueOf(section.getString("messagecolor")).ordinal());
        //LOCAL
        section = fileConfig.getConfigurationSection("LOCAL");
        gui.getTextPrefixLocalChat().setText(section.getString("prefix"));
        gui.getComboPrefixColorLocalChat().setSelectedIndex(ChatColor.valueOf(section.getString("prefixcolor")).ordinal());
        gui.getComboMessageColorLocalChat().setSelectedIndex(ChatColor.valueOf(section.getString("messagecolor")).ordinal());
    }

    public void saveConfig() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
        fileConfig.set("globalenabled", gui.getCheckEnableGlobalChat().isSelected());
        fileConfig.set("marketenabled", gui.getCheckEnableMarketChat().isSelected());
        fileConfig.set("guildenabled", gui.getCheckGuildChat().isSelected());
        fileConfig.set("partyenabled", gui.getCheckPartyChat().isSelected());
        fileConfig.set("privateenabled", gui.getCheckPrivateChat().isSelected());
        fileConfig.set("maxdistance", gui.getSpinnerDistanceLocalChat().getValue());
        fileConfig.set("playerclasscolor", this.playerclasscolor.name());
        fileConfig.set("playernamecolor", this.playernamecolor.name());
        fileConfig.set("locationcolor", this.locationcolor.name());

        //WARNING
        ConfigurationSection section = fileConfig.getConfigurationSection("WARNING");
        section.set("prefix", gui.getTextPrefixWarningChat().getText());
        section.set("shortcut", gui.getComboShortcutWarningChat().getSelectedItem());
        section.set("prefixcolor", gui.getComboPrefixColorWarningChat().getSelectedItem());
        section.set("messagecolor", gui.getComboMessageColorWarningChat().getSelectedItem());
        //NEWS
        section = fileConfig.getConfigurationSection("NEWS");
        section.set("prefix", gui.getTextPrefixNewsChat().getText());
        section.set("shortcut", gui.getComboShortcutNewsChat().getSelectedItem());
        section.set("prefixcolor", gui.getComboPrefixColorNewsChat().getSelectedItem());
        section.set("messagecolor", gui.getComboMessageColorNewsChat().getSelectedItem());
        //PRIVATE
        section = fileConfig.getConfigurationSection("PRIVATE");
        section.set("prefix", gui.getTextPrefixPrivateChat().getText());
        section.set("shortcut", gui.getComboShortcutPrivateChat().getSelectedItem());
        section.set("prefixcolor", gui.getComboPrefixColorPrivateChat().getSelectedItem());
        section.set("messagecolor", gui.getComboMessageColorPrivateChat().getSelectedItem());
        //GUILD
        section = fileConfig.getConfigurationSection("GUILD");
        section.set("prefix", gui.getTextPrefixGuildChat().getText());
        section.set("shortcut", gui.getComboShortcutGuildChat().getSelectedItem());
        section.set("prefixcolor", gui.getComboPrefixColorGuildChat().getSelectedItem());
        section.set("messagecolor", gui.getComboMessageColorGuildChat().getSelectedItem());
        //PARTY
        section = fileConfig.getConfigurationSection("NEWS");
        section.set("prefix", gui.getTextPrefixPartyChat().getText());
        section.set("shortcut", gui.getComboShortcutPartyChat().getSelectedItem());
        section.set("prefixcolor", gui.getComboPrefixColorPartyChat().getSelectedItem());
        section.set("messagecolor", gui.getComboMessageColorPartyChat().getSelectedItem());
        //MARKET
        section = fileConfig.getConfigurationSection("MARKET");
        section.set("prefix", gui.getTextPrefixMarketChat().getText());
        section.set("shortcut", gui.getComboShortcutMarketChat().getSelectedItem());
        section.set("prefixcolor", gui.getComboPrefixColorMarketChat().getSelectedItem());
        section.set("messagecolor", gui.getComboMessageColorMarketChat().getSelectedItem());
        //GLOBAL
        section = fileConfig.getConfigurationSection("GLOBAL");
        section.set("prefix", gui.getTextPrefixGlobalChat().getText());
        section.set("shortcut", gui.getComboShortcutGlobalChat().getSelectedItem());
        section.set("prefixcolor", gui.getComboPrefixColorGlobalChat().getSelectedItem());
        section.set("messagecolor", gui.getComboMessageColorGlobalChat().getSelectedItem());
        //LOCAL
        section = fileConfig.getConfigurationSection("LOCAL");
        section.set("prefix", gui.getTextPrefixLocalChat().getText());
        section.set("prefixcolor", gui.getComboPrefixColorLocalChat().getSelectedItem());
        section.set("messagecolor", gui.getComboMessageColorLocalChat().getSelectedItem());
        try {
            fileConfig.save(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al intentar guardar la configuración de chats", "Error", 2);
        }
    }
}
