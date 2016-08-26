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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.guilds;

import com.craftcosta.jailrules.rpgcraftcosta.gui.GUI;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class GUIGuildManager {

    private GUI gui;
    private int lastlevel;
    private int minlevelcreate;
    private int numplayers;
    private boolean fixedplayers;
    private boolean formulabased;
    private double a;
    private double b;
    private int maxlevel;
    private int playersxlevel;
    private int initialplayers;
    private boolean donation;
    private boolean contribution;
    private float contributionkills;
    private Map<Integer, GuildLevel> guildlevels;
    private File fileg;
    private FileConfiguration filegConfig;

    public GUIGuildManager(GUI gui) {
        this.gui = gui;
        this.guildlevels = new HashMap<>();
        fileg = new File(RPGFinals.guildsConfig);
        if (!fileg.exists()) {
            try {
                fileg.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GUIGuildManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadGuildConfig();
        System.out.println("Cargado config de clanes");
    }

    private void loadGuildConfig() {
        filegConfig = YamlConfiguration.loadConfiguration(fileg);
        this.minlevelcreate = filegConfig.getInt("minlevelcreate");
        gui.getSpinnerNivelMinJugClan().setValue(this.minlevelcreate);
        this.numplayers = filegConfig.getInt("numplayers");
        gui.getSpinnerNumFijosClan().setValue(this.numplayers);
        this.formulabased = filegConfig.getBoolean("formulabased");
        gui.getRdBtnOpcDinForm().setSelected(this.formulabased);
        this.fixedplayers = filegConfig.getBoolean("fixedplayers");
        gui.getRdBtnNumFijosClan().setSelected(this.fixedplayers);
        this.a = filegConfig.getDouble("a");
        gui.getSpinnerformulax2clan().setValue(this.a);
        this.b = filegConfig.getDouble("b");
        gui.getSpinnerformulaxclan().setValue(this.b);
        this.contribution = filegConfig.getBoolean("contribution");
        gui.getCheckContributionClan().setSelected(contribution);
        this.donation = filegConfig.getBoolean("donation");
        gui.getCheckDonationClan().setSelected(donation);
        this.initialplayers = filegConfig.getInt("initialplayers");
        gui.getSpinnerFormJugIniClan().setValue(initialplayers);
        this.maxlevel = filegConfig.getInt("maxlevel");
        gui.getSpinnerNivelMaxClan().setValue(this.maxlevel);
        this.playersxlevel = filegConfig.getInt("playersxlevel");
        gui.getSpinnerFormJugXNivelClan().setValue(playersxlevel);
        this.contributionkills = (float) filegConfig.getDouble("contributionkills");
        gui.getSpinnerContriPorcentajeMuertes().setValue(this.contributionkills);
        if (fixedplayers) {
            gui.recursivelyEnableDisablePanel(gui.getPanelOpcLimNivelClan(), false);
            gui.recursivelyEnableDisablePanel(gui.getPanelSisContNivelClan(), false);
            gui.getSpinnerNumFijosClan().setEnabled(true);
        } else {
            gui.recursivelyEnableDisablePanel(gui.getPanelOpcLimNivelClan(), true);
            gui.recursivelyEnableDisablePanel(gui.getPanelSisContNivelClan(), true);
            gui.getSpinnerNumFijosClan().setEnabled(false);
        }
        long dinero;
        int players;
        int nivel;
        if (!formulabased) {
            gui.recursivelyEnableDisablePanel(gui.getPanelFormulaClan(), false);
            ConfigurationSection section = filegConfig.getConfigurationSection("levels");
            Set<String> levels = section.getKeys(false);
            for (String level : levels) {
                ConfigurationSection s2 = section.getConfigurationSection(level);
                nivel = Integer.parseInt(level);
                lastlevel = nivel;
                dinero = s2.getLong("dinero");
                players = s2.getInt("numplayers");
                guildlevels.put(nivel, new GuildLevel(nivel, dinero, players));
            }

        } else {
            gui.recursivelyEnableDisablePanel(gui.getPanelFormulaClan(), true);
            guildlevels = new HashMap<>();
            guildlevels.put(0, new GuildLevel(0, 0, this.initialplayers));
            for (int i = 1; i <= this.maxlevel; i++) {
                dinero = (long) (Math.pow(a, 2) * i + b * i);
                players = initialplayers + i * playersxlevel;
                guildlevels.put(i, new GuildLevel(i, dinero, players));
            }

        }
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Nivel");
        dtm.addColumn("Dinero");
        dtm.addColumn("Jugadores");
        Object[] data = new Object[3];
        for (Map.Entry<Integer, GuildLevel> entrySet : guildlevels.entrySet()) {
            data[0] = entrySet.getValue().getLevel();
            data[1] = entrySet.getValue().getDinero();
            data[2] = entrySet.getValue().getNumplayers();
            dtm.addRow(data);
        }
        gui.getTablaClanes().setModel(dtm);
        gui.getTablaClanes().setVisible(true);
        gui.getTablaClanes().validate();
        gui.getTablaClanes().repaint();

    }

    public void saveConfig() {
        try {
            filegConfig = YamlConfiguration.loadConfiguration(fileg);
            filegConfig.set("numplayers", (int) gui.getSpinnerNumFijosClan().getValue());
            filegConfig.set("fixedplayers", gui.getRdBtnNumFijosClan().isSelected());
            filegConfig.set("formulabased", gui.getRdBtnOpcDinForm().isSelected());
            filegConfig.set("a", (double) gui.getSpinnerformulax2clan().getValue());
            filegConfig.set("b", (double) gui.getSpinnerformulaxclan().getValue());
            filegConfig.set("maxlevel", (int) gui.getSpinnerNivelMaxClan().getValue());
            filegConfig.set("playersxlevel", (int) gui.getSpinnerFormJugXNivelClan().getValue());
            filegConfig.set("initialplayers", (int) gui.getSpinnerFormJugIniClan().getValue());
            filegConfig.set("donation", gui.getCheckDonationClan().isSelected());
            filegConfig.set("contribution", gui.getCheckContributionClan().isSelected());
            filegConfig.set("contributionkills", (float) gui.getSpinnerContriPorcentajeMuertes().getValue());
            ConfigurationSection section = filegConfig.createSection("levels");
            for (int i = 0; i < gui.getTablaClanes().getModel().getRowCount(); i++) {
                ConfigurationSection s2 = section.createSection("" + i);
                s2.set("dinero", gui.getTablaClanes().getModel().getValueAt(i, 1));
                s2.set("numplayers", gui.getTablaClanes().getModel().getValueAt(i, 2));
            }
            filegConfig.save(fileg);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui, "Se ha producido un error al intentar guardar la configuración", "Error", 2);
        }
    }

}
