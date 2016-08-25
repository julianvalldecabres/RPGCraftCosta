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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.parties;

import com.craftcosta.jailrules.rpgcraftcosta.gui.GUI;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class GUIPartyManager {

    private GUI gui;
    private int playerlimit;
    private String share;
    private boolean pvpenabled;
    private String shareoption;
    private String proportionalreason;
    File filec;
    FileConfiguration filecConfig;

    public GUIPartyManager(GUI gui) {
        this.gui = gui;
        filec = new File(RPGFinals.partyFile);
        if (!filec.exists()) {
            try {
                filec.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GUIPartyManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadConfig();
    }

    private void loadConfig() {
        filecConfig = YamlConfiguration.loadConfiguration(filec);
        playerlimit = filecConfig.getInt("playerlimit");
        if (playerlimit == 0) {
            gui.getRdBtnIlimitadosGrupo().setSelected(true);
            gui.getRdBtnFijosGrupo().setSelected(false);
            gui.getSpinnerNumeroFijoJugGrupo().setEnabled(false);
        } else {
            gui.getRdBtnIlimitadosGrupo().setSelected(false);
            gui.getRdBtnFijosGrupo().setSelected(true);
            gui.getSpinnerNumeroFijoJugGrupo().setEnabled(true);
            gui.getSpinnerNumeroFijoJugGrupo().setValue(this.playerlimit);
        }
        pvpenabled = filecConfig.getBoolean("pvpenabled");
        if (gui.getGuiConfigMan().isEnablePvP()) {
            gui.getRdBtnPermitidoPvpGrupo().setEnabled(true);
            gui.getRdBtnNoPermitidoPvpGrupo().setEnabled(true);
        } else {
            gui.getRdBtnPermitidoPvpGrupo().setEnabled(false);
            gui.getRdBtnNoPermitidoPvpGrupo().setEnabled(false);
        }
        if (pvpenabled) {
            gui.getRdBtnPermitidoPvpGrupo().setSelected(true);
            gui.getRdBtnNoPermitidoPvpGrupo().setSelected(false);
        } else {
            gui.getRdBtnPermitidoPvpGrupo().setSelected(false);
            gui.getRdBtnNoPermitidoPvpGrupo().setSelected(true);
        }
        share = filecConfig.getString("share");
        switch (share) {
            case "none":
                gui.recursivelyEnableDisablePanel(gui.getPanelOpcRepGRupos(), false);
                break;
            case "money":
                gui.recursivelyEnableDisablePanel(gui.getPanelOpcRepGRupos(), true);
                gui.getCheckDineroGrupo().setSelected(true);
                break;
            case "exp":
                gui.recursivelyEnableDisablePanel(gui.getPanelOpcRepGRupos(), true);
                gui.getCheckExpGrupo().setSelected(true);
                break;
            case "both":
                gui.recursivelyEnableDisablePanel(gui.getPanelOpcRepGRupos(), true);
                gui.getCheckDineroGrupo().setSelected(true);
                gui.getCheckExpGrupo().setSelected(true);
                break;
        }
        shareoption = filecConfig.getString("shareoption");
        switch (shareoption) {
            case "equal":
                gui.getRdBtnOpcionigualitarioGrupo().setSelected(true);
                gui.recursivelyEnableDisablePanel(gui.getPanelOpcRepProp(), false);
                break;
            case "proportional":
                gui.getRdBtnOpcionProporcionalGrupo().setSelected(true);
                if (!share.equals("none")) {
                    gui.recursivelyEnableDisablePanel(gui.getPanelOpcRepProp(), true);
                }
                break;

        }
        proportionalreason = filecConfig.getString("proportionalreason");
        switch (proportionalreason) {
            case "kills":
                gui.getRdBtnProKillsGrupo().setSelected(true);
                break;
            case "level":
                gui.getRdBtnProNivelGrupo().setSelected(true);
                break;
        }
    }

    private void saveConfig() {
        filecConfig = YamlConfiguration.loadConfiguration(filec);
        if (gui.getRdBtnFijosGrupo().isSelected()) {
            filecConfig.set("playerlimit", gui.getSpinnerNumeroFijoJugGrupo().getValue());
        } else {
            filecConfig.set("playerlimit", 0);
        }
        if (gui.getRdBtnPermitidoPvpGrupo().isSelected()) {
            filecConfig.set("pvpenabled", true);
        } else {
            filecConfig.set("pvpenabled", false);
        }
        if (gui.getCheckDineroGrupo().isSelected() && gui.getCheckExpGrupo().isSelected()) {
            filecConfig.set("share", "both");
        } else if (gui.getCheckDineroGrupo().isSelected() && !gui.getCheckExpGrupo().isSelected()) {
            filecConfig.set("share", "money");
        } else if (gui.getCheckDineroGrupo().isSelected() && gui.getCheckExpGrupo().isSelected()) {
            filecConfig.set("share", "exp");
        } else {
            filecConfig.set("share", "none");
        }
        if (gui.getRdBtnOpcionigualitarioGrupo().isSelected()) {
            filecConfig.set("shareoption", "equal");
        } else {
            filecConfig.set("shareoption", "proportion");
        }
        if (gui.getRdBtnProKillsGrupo().isSelected()) {
            filecConfig.set("proportionalreason", "kills");
        } else {
            filecConfig.set("proportionalreason", "level");
        }

        try {
            filecConfig.save(filec);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al guardar el fichero", "Error", 2);
        }

    }

}
