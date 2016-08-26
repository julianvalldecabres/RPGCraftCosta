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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.classes;

import com.craftcosta.jailrules.rpgcraftcosta.gui.GUI;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class GUIClassesManager {

    private GUI gui;
    private Map<String, GUIClass> listClasses;
    File file;
    FileConfiguration fileConfig;

    public GUIClassesManager(GUI gui) {
        this.gui = gui;
        file = new File(RPGFinals.classFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GUIClassesManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        listClasses = new HashMap<>();
        loadClasses();
        gui.getComboSelectorClases().addItem("<Selecciona una clase para editar>");
        gui.recursivelyEnableDisablePanel(gui.getPanelEditorClase(), false);
        for (Map.Entry<String, GUIClass> entrySet : listClasses.entrySet()) {
            gui.getComboSelectorClases().addItem(entrySet.getKey());

        }
        System.out.println("Cargado config clases");
    }

    public void loadValuesForClassSelected() {

        GUIClass gc = getGUIClassByName(gui.getComboSelectorClases().getSelectedItem().toString());
        if (gc == null) {
            gui.sendMessageWarning("Error", "La clase seleccionada no existe");
        } else {
            gui.getTxtNombreClase().setText(gc.getName());
            gui.getCheckEnableClase().setSelected(gc.isEnabled());
            gui.getSpinnerMaxVidaBase().setValue(gc.getBaseHealth());
            gui.getSpinnerMaxManaBase().setValue(gc.getBaseMana());
            gui.getSpinnerAtaFisBase().setValue(gc.getBasePhysicalAttack());
            gui.getSpinnerDefFisBase().setValue(gc.getBasePhysicalDefense());
            gui.getSpinnerEvaFisBase().setValue(gc.getBasePhysicalEvasion());
            gui.getSpinnerHRFisBase().setValue(gc.getBasePhysicalHitRate());
            gui.getSpinnerAtaMagBase().setValue(gc.getBaseMagicAttack());
            gui.getSpinnerDefMagBase().setValue(gc.getBaseMagicDefense());
            gui.getSpinnerHRMagBase().setValue(gc.getBaseMagicHitRate());
            gui.getSpinnerEvaMagBase().setValue(gc.getBaseMagicEvasion());
            gui.getSpinnerCritBase().setValue(gc.getBaseCritical());
            gui.getSpinnerMortalBase().setValue(gc.getBaseDeadly());

            gui.getSpinnerVidaNivel().setValue(gc.getLvlUpHealth());
            gui.getSpinnerManaNivel().setValue(gc.getLvlUpMana());
            gui.getSpinnerAtaFisNivel().setValue(gc.getLvlUpPhysicalAttack());
            gui.getSpinnerDefFisNivel().setValue(gc.getLvlUpPhysicalDefense());
            gui.getSpinnerEvaFisNivel().setValue(gc.getLvlUpPhysicalEvasion());
            gui.getSpinnerHRFisNivel().setValue(gc.getLvlUpPhysicalHitRate());
            gui.getSpinnerAtaMagNivel().setValue(gc.getLvlUpMagicAttack());
            gui.getSpinnerDefMagNivel().setValue(gc.getLvlUpMagicDefense());
            gui.getSpinnerEvaMagNivel().setValue(gc.getLvlUpMagicEvasion());
            gui.getSpinnerHRMagNivel().setValue(gc.getLvlUpMagicHitRate());
            gui.getSpinnerCritNivel().setValue(gc.getLvlUpCritical());
            gui.getSpinnerMortalNivel().setValue(gc.getLvlUpDeadly());

            gui.getSpinnerAtaFisAP().setValue(gc.getSPUpStrength_PhysicalAttack());
            gui.getSpinnerVidaAP().setValue(gc.getSPUpStrength_Health());

            gui.getSpinnerVidaAP2().setValue(gc.getSPUpConstitution_Health());
            gui.getSpinnerManaAP().setValue(gc.getSPUpConstitution_Mana());

            gui.getSpinnerAtaMagAP().setValue(gc.getSPUpIntelligence_MagicAttack());
            gui.getSpinnerManaAP2().setValue(gc.getSPUpIntelligence_Mana());

            gui.getSpinnerMortalAP().setValue(gc.getSPUpDextery_Deadly());
            gui.getSpinnerCritAP().setValue(gc.getSPUpDextery_Critical());
            gui.recursivelyEnableDisablePanel(gui.getPanelEditorClase(), true);
        }
    }

    private void loadClasses() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
        String name;
        boolean enabled;
        double baseHealth;
        double baseMana;
        double basePhysicalAttack;
        double basePhysicalDefense;
        double basePhysicalHitRate;
        double basePhysicalEvasion;
        double baseMagicAttack;
        double baseMagicDefense;
        double baseMagicHitRate;
        double baseMagicEvasion;
        double baseCritical;
        double baseDeadly;
        double lvlUpHealth;
        double lvlUpMana;
        double lvlUpPhysicalAttack;
        double lvlUpPhysicalDefense;
        double lvlUpPhysicalHitRate;
        double lvlUpPhysicalEvasion;
        double lvlUpMagicAttack;
        double lvlUpMagicDefense;
        double lvlUpMagicHitRate;
        double lvlUpMagicEvasion;
        double lvlUpCritical;
        double lvlUpDeadly;
        double SPUpStrength_Health;
        double SPUpStrength_PhysicalAttack;
        double SPUpDextery_Critical;
        double SPUpDextery_Deadly;
        double SPUpConstitution_Health;
        double SPUpConstitution_Mana;
        double SPUpIntelligence_Mana;
        double SPUpIntelligence_MagicAttack;

        Set<String> classes = fileConfig.getKeys(false);
        for (String clas : classes) {

            ConfigurationSection section = fileConfig.getConfigurationSection(clas);
            enabled = section.getBoolean("enabled");
            name = section.getString("name");
            baseHealth = section.getDouble("baseHealth");
            baseMana = section.getDouble("baseMana");
            basePhysicalAttack = section.getDouble("basePhysicalAttack");
            basePhysicalDefense = section.getDouble("basePhysicalDefense");
            basePhysicalHitRate = section.getDouble("basePhysicalHitRate");
            basePhysicalEvasion = section.getDouble("basePhysicalEvasion");
            baseMagicAttack = section.getDouble("baseMagicAttack");
            baseMagicDefense = section.getDouble("baseMagicDefense");
            baseMagicHitRate = section.getDouble("baseMagicHitRate");
            baseMagicEvasion = section.getDouble("baseMagicEvasion");
            baseCritical = section.getDouble("baseCritical");
            baseDeadly = section.getDouble("baseDeadly");
            lvlUpHealth = section.getDouble("lvlUpHealth");
            lvlUpMana = section.getDouble("lvlUpMana");
            lvlUpPhysicalAttack = section.getDouble("lvlUpPhysicalAttack");
            lvlUpPhysicalDefense = section.getDouble("lvlUpPhysicalDefense");
            lvlUpPhysicalHitRate = section.getDouble("lvlUpPhysicalHitRate");
            lvlUpPhysicalEvasion = section.getDouble("lvlUpPhysicalEvasion");
            lvlUpMagicAttack = section.getDouble("lvlUpMagicAttack");
            lvlUpMagicDefense = section.getDouble("lvlUpMagicDefense");
            lvlUpMagicHitRate = section.getDouble("lvlUpMagicHitRate");
            lvlUpMagicEvasion = section.getDouble("lvlUpMagicEvasion");
            lvlUpCritical = section.getDouble("lvlUpCritical");
            lvlUpDeadly = section.getDouble("lvlUpDeadly");
            SPUpStrength_Health = section.getDouble("SPUpStrength_Health");
            SPUpStrength_PhysicalAttack = section.getDouble("SPUpStrength_PhysicalAttack");
            SPUpDextery_Critical = section.getDouble("SPUpDextery_Critical");
            SPUpDextery_Deadly = section.getDouble("SPUpDextery_Dedly");
            SPUpConstitution_Health = section.getDouble("SPUpConstitution_Health");
            SPUpConstitution_Mana = section.getDouble("SPUpConstitution_Mana");
            SPUpIntelligence_MagicAttack = section.getDouble("SPUpIntelligence_MagicAttack");
            SPUpIntelligence_Mana = section.getDouble("SPUpIntelligence_Mana");
            this.listClasses.put(name, new GUIClass(name, enabled, baseHealth, baseMana, basePhysicalAttack, basePhysicalDefense, basePhysicalHitRate, basePhysicalEvasion, baseMagicAttack, baseMagicDefense, baseMagicHitRate, baseMagicEvasion, baseCritical, baseDeadly, lvlUpHealth, lvlUpMana, lvlUpPhysicalAttack, lvlUpPhysicalDefense, lvlUpPhysicalHitRate, lvlUpPhysicalEvasion, lvlUpMagicAttack, lvlUpMagicDefense, lvlUpMagicHitRate, lvlUpMagicEvasion, lvlUpCritical, lvlUpDeadly, SPUpStrength_Health, SPUpStrength_PhysicalAttack, SPUpDextery_Critical, SPUpDextery_Deadly, SPUpConstitution_Health, SPUpConstitution_Mana, SPUpIntelligence_Mana, SPUpIntelligence_MagicAttack));
        }
    }

    public void saveClass() {
        GUIClass gc = getGUIClassByName(gui.getTxtNombreClase().getText());
        if (gc == null) {
            //La clase no existe y crea una nueva
            gc = new GUIClass(gui.getTxtNombreClase().getText(),
                    gui.getCheckEnableClase().isSelected(),
                    (double) gui.getSpinnerMaxVidaBase().getValue(),
                    (double) gui.getSpinnerMaxManaBase().getValue(),
                    (double) gui.getSpinnerAtaFisBase().getValue(),
                    (double) gui.getSpinnerDefFisBase().getValue(),
                    (double) gui.getSpinnerHRFisBase().getValue(),
                    (double) gui.getSpinnerEvaFisBase().getValue(),
                    (double) gui.getSpinnerAtaMagBase().getValue(),
                    (double) gui.getSpinnerDefMagBase().getValue(),
                    (double) gui.getSpinnerHRMagBase().getValue(),
                    (double) gui.getSpinnerEvaMagBase().getValue(),
                    (double) gui.getSpinnerCritBase().getValue(),
                    (double) gui.getSpinnerMortalBase().getValue(),
                    (double) gui.getSpinnerVidaNivel().getValue(),
                    (double) gui.getSpinnerManaNivel().getValue(),
                    (double) gui.getSpinnerAtaFisNivel().getValue(),
                    (double) gui.getSpinnerDefFisNivel().getValue(),
                    (double) gui.getSpinnerHRFisNivel().getValue(),
                    (double) gui.getSpinnerEvaFisNivel().getValue(),
                    (double) gui.getSpinnerAtaMagNivel().getValue(),
                    (double) gui.getSpinnerDefMagNivel().getValue(),
                    (double) gui.getSpinnerHRMagNivel().getValue(),
                    (double) gui.getSpinnerEvaMagNivel().getValue(),
                    (double) gui.getSpinnerCritNivel().getValue(),
                    (double) gui.getSpinnerMortalNivel().getValue(),
                    (double) gui.getSpinnerVidaAP().getValue(),
                    (double) gui.getSpinnerAtaFisAP().getValue(),
                    (double) gui.getSpinnerCritAP().getValue(),
                    (double) gui.getSpinnerMortalAP().getValue(),
                    (double) gui.getSpinnerVidaAP2().getValue(),
                    (double) gui.getSpinnerManaAP().getValue(),
                    (double) gui.getSpinnerManaAP2().getValue(),
                    (double) gui.getSpinnerAtaMagAP().getValue());
            //Añadir a listado
            this.listClasses.put(gc.getName(), gc);
            //Añadir al combobox de selección
            gui.getComboSelectorClases().addItem(gc.getName());
            //bloquear editor
            gui.recursivelyEnableDisablePanel(gui.getPanelEditorClase(), false);
            //Guardar clase en el fichero
            saveClassToFile(gc);
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "Ya existe una clase con ese nombre\n¿Quieres sobreescribirla?", "Sobreescribir clase: " + gc.getName(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                gc.setEnabled(gui.getCheckEnableClase().isEnabled());
                gc.setBaseHealth((double) gui.getSpinnerMaxVidaBase().getValue());
                gc.setBaseMana((double) gui.getSpinnerMaxManaBase().getValue());
                gc.setBasePhysicalAttack((double) gui.getSpinnerAtaFisBase().getValue());
                gc.setBasePhysicalDefense((double) gui.getSpinnerDefFisBase().getValue());
                gc.setBasePhysicalEvasion((double) gui.getSpinnerEvaFisBase().getValue());
                gc.setBasePhysicalHitRate((double) gui.getSpinnerHRFisBase().getValue());
                gc.setBaseMagicAttack((double) gui.getSpinnerAtaMagBase().getValue());
                gc.setBaseMagicDefense((double) gui.getSpinnerDefMagBase().getValue());
                gc.setBaseMagicEvasion((double) gui.getSpinnerEvaMagBase().getValue());
                gc.setBaseMagicHitRate((double) gui.getSpinnerHRMagBase().getValue());
                gc.setBaseCritical((double) gui.getSpinnerCritBase().getValue());
                gc.setBaseDeadly((double) gui.getSpinnerMortalBase().getValue());

                gc.setLvlUpHealth((double) gui.getSpinnerVidaNivel().getValue());
                gc.setLvlUpMana((double) gui.getSpinnerManaNivel().getValue());
                gc.setLvlUpPhysicalAttack((double) gui.getSpinnerAtaFisNivel().getValue());
                gc.setLvlUpPhysicalDefense((double) gui.getSpinnerDefFisNivel().getValue());
                gc.setLvlUpPhysicalEvasion((double) gui.getSpinnerEvaFisNivel().getValue());
                gc.setLvlUpPhysicalHitRate((double) gui.getSpinnerHRFisNivel().getValue());
                gc.setLvlUpMagicAttack((double) gui.getSpinnerAtaMagNivel().getValue());
                gc.setLvlUpMagicDefense((double) gui.getSpinnerDefMagNivel().getValue());
                gc.setLvlUpMagicEvasion((double) gui.getSpinnerEvaMagNivel().getValue());
                gc.setLvlUpMagicHitRate((double) gui.getSpinnerHRMagNivel().getValue());
                gc.setLvlUpCritical((double) gui.getSpinnerCritNivel().getValue());
                gc.setLvlUpDeadly((double) gui.getSpinnerMortalNivel().getValue());

                gc.setSPUpStrength_Health((double) gui.getSpinnerVidaAP().getValue());
                gc.setSPUpStrength_PhysicalAttack((double) gui.getSpinnerAtaFisAP().getValue());
                gc.setSPUpDextery_Critical((double) gui.getSpinnerCritAP().getValue());
                gc.setSPUpDextery_Deadly((double) gui.getSpinnerMortalAP().getValue());
                gc.setSPUpConstitution_Health((double) gui.getSpinnerVidaAP2().getValue());
                gc.setSPUpConstitution_Mana((double) gui.getSpinnerManaAP().getValue());
                gc.setSPUpIntelligence_MagicAttack((double) gui.getSpinnerAtaMagAP().getValue());
                gc.setSPUpIntelligence_Mana((double) gui.getSpinnerManaAP2().getValue());
                //modificar listaclasses
                this.listClasses.put(gc.getName(), gc);
                //bloquear editor
                gui.recursivelyEnableDisablePanel(gui.getPanelEditorClase(), false);
                //Guardar clase en el fichero
                saveClassToFile(gc);
            }
        }
    }

    public void saveClassToFile(GUIClass gc) {
        fileConfig = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = fileConfig.createSection(gc.getName());
        section.set("name", gc.getName());
        section.set("enabled", gc.isEnabled());
        section.set("baseHealth", gc.getBaseHealth());
        section.set("baseMana", gc.getBaseMana());
        section.set("basePhysicalAttack", gc.getBasePhysicalAttack());
        section.set("basePhysicalDefense", gc.getBasePhysicalDefense());
        section.set("basePhysicalHitRate", gc.getBasePhysicalHitRate());
        section.set("basePhysicalEvasion", gc.getBasePhysicalEvasion());
        section.set("baseMagicAttack", gc.getBaseMagicAttack());
        section.set("baseMagicDefense", gc.getBaseMagicDefense());
        section.set("baseMagicHitRate", gc.getBaseMagicHitRate());
        section.set("baseMagicEvasion", gc.getBaseMagicEvasion());
        section.set("baseCritical", gc.getBaseCritical());
        section.set("baseDeadly", gc.getBaseDeadly());
        section.set("lvlUpHealth", gc.getLvlUpHealth());
        section.set("lvlUpMana", gc.getLvlUpMana());
        section.set("lvlUpPhysicalAttack", gc.getLvlUpPhysicalAttack());
        section.set("lvlUpPhysicalDefense", gc.getLvlUpPhysicalDefense());
        section.set("lvlUpPhysicalHitRate", gc.getLvlUpPhysicalHitRate());
        section.set("lvlUpPhysicalEvasion", gc.getLvlUpPhysicalEvasion());
        section.set("lvlUpMagicAttack", gc.getLvlUpMagicAttack());
        section.set("lvlUpMagicDefense", gc.getLvlUpMagicDefense());
        section.set("lvlUpMagicHitRate", gc.getLvlUpMagicHitRate());
        section.set("lvlUpMagicEvasion", gc.getLvlUpMagicEvasion());
        section.set("lvlUpCritical", gc.getLvlUpCritical());
        section.set("lvlUpDeadly", gc.getLvlUpDeadly());
        section.set("SPUpStrength_Health", gc.getSPUpStrength_Health());
        section.set("SPUpStrength_PhysicalAttack", gc.getSPUpStrength_PhysicalAttack());
        section.set("SPUpDextery_Critical", gc.getSPUpDextery_Critical());
        section.set("SPUpDextery_Deadly", gc.getSPUpDextery_Deadly());
        section.set("SPUpConstitution_Health", gc.getSPUpConstitution_Health());
        section.set("SPUpConstitution_Mana", gc.getSPUpConstitution_Mana());
        section.set("SPUpIntelligence_Mana", gc.getSPUpIntelligence_Mana());
        section.set("SPUpIntelligence_MagicAttack", gc.getSPUpIntelligence_MagicAttack());
        try {
            fileConfig.save(file);
        } catch (IOException ex) {

        }
    }

    public GUIClass getGUIClassByName(String name) {
        return this.listClasses.get(name);
    }

    public void deleteClassSelected() {
        GUIClass gc = getGUIClassByName(gui.getComboSelectorClases().getSelectedItem().toString());
        if (gc == null) {
            gui.sendMessageWarning("Error", "La clase seleccionada no existe");
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "¿Estas seguro de querer eliminar la clase?", "Borrar clase: " + gc.getName(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                //borrar clase de el combo
                gui.getComboSelectorClases().removeItem(gc.getName());
                gui.getComboSelectorClases().setSelectedIndex(0);
                //Borrar clase de el fichero y de la memoria
                listClasses.remove(gc.getName());
                deleteClassFromFile(gc);
            }
        }
    }

    private void deleteClassFromFile(GUIClass gc) {
        try {
            fileConfig = YamlConfiguration.loadConfiguration(file);
            fileConfig.set(gc.getName(), null);
            fileConfig.save(file);
        } catch (IOException ex) {
            Logger.getLogger(GUIClassesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
