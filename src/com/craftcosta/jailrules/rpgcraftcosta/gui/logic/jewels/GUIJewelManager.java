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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.jewels;

import com.craftcosta.jailrules.rpgcraftcosta.gui.GUI;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumItems;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumQuality;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class GUIJewelManager {

    private int lastindex;
    private float breakp;
    private float losep;
    private float losepperlore;
    private float nothing;
    private File filej;
    private File filec;
    private FileConfiguration filejConfig;
    private FileConfiguration filecConfig;
    private Map<String, GUIJewel> listjewels;
    private Map<String, Integer> listnum;
    private GUI gui;
    private float success;

    public GUIJewelManager(GUI gui) {
        this.gui = gui;
        filej = new File(RPGFinals.jewelFile);
        if (!filej.exists()) {
            try {
                filej.createNewFile();
            } catch (IOException ex) {
            }
        }
        filec = new File(RPGFinals.jewelsConfig);
        if (!filec.exists()) {
            try {
                filec.createNewFile();
            } catch (IOException ex) {
            }
        }
        listjewels = new HashMap<>();
        listnum = new HashMap<>();
        loadJewelsConfig();
        loadJewels();
        gui.recursivelyEnableDisablePanel(gui.getPanelEditorJoya(), false);
        gui.getComboSelectorJoyas().addItem("<Selecciona una joya para editar>");
        for (Map.Entry<String, GUIJewel> entrySet : listjewels.entrySet()) {
            gui.getComboSelectorJoyas().addItem(entrySet.getKey());
        }
        System.out.println("Cargado config joyas");
    }

    private void loadJewelsConfig() {
        filecConfig = YamlConfiguration.loadConfiguration(filec);
        this.breakp = (float) filecConfig.getDouble("breakp");
        gui.getSpinnerProbRotJoya().setValue(this.breakp);
        this.losep = (float) filecConfig.getDouble("losep");
        gui.getSpinnerProbDetJoya().setValue(this.losep);
        this.losepperlore = (float) filecConfig.getDouble("loseperlore");
        gui.getSpinnerProbSEJoya().setValue(this.losepperlore);
        this.nothing = (float) filecConfig.getDouble("nothing");
        gui.getSpinnerProbNothingJoya().setValue(this.nothing);
        this.success = (float) filecConfig.getDouble("success");
        gui.getSpinnerProbExitoJoya().setValue(this.success);
    }

    private void loadJewels() {
        filejConfig = YamlConfiguration.loadConfiguration(filej);
        this.lastindex = 0;
        String name;
        EnumItems item;
        EnumQuality quality;
        boolean combinable;
        boolean comerciable;
        long sellprice;
        long buyprice;
        double physicalattack;
        double physicaldefense;
        double magicalattack;
        double magicaldefense;
        float magicalhitrate;
        float magicalevasion;
        float physicalevasion;
        float physicalhitrate;
        float health;
        float mana;
        float critical;
        float healthsteal;
        float manasteal;
        float xpbonus;
        float moneybonus;
        Set<String> joyas = filejConfig.getKeys(false);
        for (String joyaid : joyas) {
            if (this.lastindex <= Integer.parseInt(joyaid)) {
                this.lastindex = Integer.parseInt(joyaid);
            }
            ConfigurationSection section = filejConfig.getConfigurationSection(joyaid);
            name = section.getString("name");
            item = EnumItems.valueOf(section.getString("item"));
            quality = EnumQuality.valueOf(section.getString("quality"));
            combinable = section.getBoolean("combinable");
            comerciable = section.getBoolean("comerciable");
            sellprice = section.getLong("sellprice");
            buyprice = section.getLong("buyprice");
            physicalattack = section.getDouble("physicalattack");
            physicaldefense = section.getDouble("physicaldefense");
            physicalevasion = (float) section.getDouble("physicalevasion");
            physicalhitrate = (float) section.getDouble("physicalhitrate");
            magicalattack = section.getDouble("magicalattack");
            magicaldefense = section.getDouble("magicaldefense");
            magicalevasion = (float) section.getDouble("magicalevasion");
            magicalhitrate = (float) section.getDouble("magicalhitrate");
            health = (float) section.getDouble("health");
            mana = (float) section.getDouble("mana");
            critical = (float) section.getDouble("critical");
            healthsteal = (float) section.getDouble("healthsteal");
            manasteal = (float) section.getDouble("manasteal");
            xpbonus = (float) section.getDouble("xpbonus");
            moneybonus = (float) section.getDouble("moneybonus");
            this.listjewels.put(name, new GUIJewel(name, item, quality, combinable, comerciable, sellprice, buyprice, physicalattack, physicalhitrate, physicalevasion, physicaldefense, magicalevasion, magicaldefense, magicalattack, magicalhitrate, health, mana, critical, healthsteal, manasteal, xpbonus, moneybonus));
            this.listnum.put(name, Integer.parseInt(joyaid));
        }

    }

    public Map<String, GUIJewel> getListjewels() {
        return listjewels;
    }

    public void saveJewel() {
        GUIJewel gj = getGUIJewelByName(gui.getTxtNombreJoya().getText());
        if (gj == null) {
            gj = new GUIJewel(gui.getTxtNombreJoya().getText(),
                    EnumItems.valueOf(EnumItems.values()[(int) gui.getComboObjetoJoya().getSelectedItem()].name()),
                    EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidadArma().getSelectedIndex()].name()),
                    gui.getCheckCombinable().isSelected(),
                    gui.getCheckComerciable().isSelected(),
                    (int) gui.getSpinnerPrecioVJoya().getValue(),
                    (int) gui.getSpinnerPrecioCJoya().getValue(),
                    (double) gui.getSpinnerAtaFisJoya().getValue(),
                    (float) gui.getSpinnerHRFisJoya().getValue(),
                    (float) gui.getSpinnerEvaFisJoya().getValue(),
                    (double) gui.getSpinnerDefFisJoya().getValue(),
                    (float) gui.getSpinnerEvaMagJoya().getValue(),
                    (double) gui.getSpinnerDefMagJoya().getValue(),
                    (double) gui.getSpinnerAtaMagJoya().getValue(),
                    (float) gui.getSpinnerHRMagJoya().getValue(),
                    (float) gui.getSpinnerVidaJoya().getValue(),
                    (float) gui.getSpinnerManaJoya().getValue(),
                    (float) gui.getSpinnerCritJoya().getValue(),
                    (float) gui.getSpinnerRoboVJoya().getValue(),
                    (float) gui.getSpinnerRoboMJoya().getValue(),
                    (float) gui.getSpinnerExpExtraJoya().getValue(),
                    (float) gui.getSpinnerDineroExtraJoya().getValue());
            this.listjewels.put(gj.getName(), gj);
            this.listnum.put(gj.getName(), lastindex + 1);
            gui.getComboSelectorJoyas().addItem(gj.getName());

            gui.recursivelyEnableDisablePanel(gui.getPanelEditorJoya(), false);
            saveJewelToFile(gj);
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "Ya existe una joya con ese nombre\n¿Quieres sobreescribirla?", "Sobreescribir joya: " + gj.getName(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                gj.setItem(EnumItems.valueOf(EnumItems.values()[(int) gui.getComboObjetoJoya().getSelectedItem()].name()));
                gj.setQuality(EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidadJoya().getSelectedIndex()].name()));
                gj.setSellprice((long) gui.getSpinnerPrecioVJoya().getValue());
                gj.setBuyprice((long) gui.getSpinnerPrecioCJoya().getValue());
                gj.setCombinable(gui.getCheckCombinable().isSelected());
                gj.setComerciable(gui.getCheckComerciable().isSelected());
                gj.setPhysicalattack((double) gui.getSpinnerAtaFisJoya().getValue());
                gj.setPhysicalhitrate((float) gui.getSpinnerHRFisJoya().getValue());
                gj.setPhysicaldefense((double) gui.getSpinnerDefFisJoya().getValue());
                gj.setPhysicalevasion((float) gui.getSpinnerEvaFisJoya().getValue());
                gj.setMagicalattack((double) gui.getSpinnerAtaMagJoya().getValue());
                gj.setMagicalhitrate((float) gui.getSpinnerHRMagJoya().getValue());
                gj.setMagicaldefense((double) gui.getSpinnerDefMagJoya().getValue());
                gj.setMagicalevasion((float) gui.getSpinnerEvaMagJoya().getValue());
                gj.setCritical((float) gui.getSpinnerCritJoya().getValue());
                gj.setHealth((float) gui.getSpinnerVidaJoya().getValue());
                gj.setMana((float) gui.getSpinnerManaJoya().getValue());
                gj.setHealthsteal((float) gui.getSpinnerRoboVJoya().getValue());
                gj.setManasteal((float) gui.getSpinnerRoboMJoya().getValue());
                gj.setXpbonus((float) gui.getSpinnerExpExtraJoya().getValue());
                gj.setMoneybonus((float) gui.getSpinnerDineroExtraJoya().getValue());

                this.listjewels.put(gj.getName(), gj);
                gui.recursivelyEnableDisablePanel(gui.getPanelEditorJoya(), false);
                saveJewelToFile(gj);
            }
        }
    }

    private GUIJewel getGUIJewelByName(String text) {
        return this.listjewels.get(text);
    }

    private void saveJewelToFile(GUIJewel gj) {
        filejConfig = YamlConfiguration.loadConfiguration(filej);
        int index;
        GUIJewel check = getGUIJewelByName(gj.getName());
        if (gj == null) {
            this.lastindex++;
            index = lastindex;
        } else {
            index = this.listnum.get(gj.getName());
        }
        ConfigurationSection section = filejConfig.createSection("" + index);
        section.set("name", gj.getName());
        section.set("item", gj.getItem().getMaterial());
        section.set("quality", gj.getQuality().getName());
        section.set("combinable", gj.isCombinable());
        section.set("comerciable", gj.isComerciable());
        section.set("buyprice", gj.getBuyprice());
        section.set("sellprice", gj.getSellprice());
        section.set("physicalattack", gj.getPhysicalattack());
        section.set("physicaldefense", gj.getPhysicaldefense());
        section.set("physicalhitrate", gj.getPhysicalhitrate());
        section.set("physicalevasion", gj.getPhysicalevasion());
        section.set("magicalattack", gj.getMagicalattack());
        section.set("magicaldefense", gj.getMagicaldefense());
        section.set("magicalhitrate", gj.getMagicalhitrate());
        section.set("magicalevasion", gj.getMagicalevasion());
        section.set("critical", gj.getCritical());
        section.set("health", gj.getHealth());
        section.set("healthsteal", gj.getHealthsteal());
        section.set("mana", gj.getMana());
        section.set("manasteal", gj.getManasteal());
        section.set("xpbonus", gj.getXpbonus());
        section.set("moneybonus", gj.getMoneybonus());
        try {
            filejConfig.save(filej);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la joya", "Error", 2);
        }
    }

    public void deleteJewelSelected() {
        GUIJewel gj = getGUIJewelByName(gui.getComboSelectorJoyas().getSelectedItem().toString());
        if (gj == null) {
            gui.sendMessageWarning("Error", "La joya seleccionada no existe");
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "¿Estas seguro de querer eliminar la joya?", "Borrar arma: " + gj.getName(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                //borrar clase de el combo
                gui.getComboSelectorJoyas().removeItem(gj.getName());
                gui.getComboSelectorJoyas().setSelectedIndex(0);
                //Borrar clase de el fichero y de la memoria
                listjewels.remove(gj.getName());
                //listnum.remove(gw.getName());
                deleteJewelFromFile(gj);
            }
        }
    }

    private void deleteJewelFromFile(GUIJewel gj) {
        try {
            filejConfig = YamlConfiguration.loadConfiguration(filej);
            int index = listnum.get(gj.getName());
            listnum.remove(gj.getName());
            filejConfig.set("" + index, null);
            filejConfig.save(filej);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar eliminar la joyaa del fichero de configuración", "Error", 2);
        }
    }

    public void loadValuesForJewelSelected() {
        GUIJewel gj = getGUIJewelByName(gui.getComboSelectorJoyas().getSelectedItem().toString());
        if (gj == null) {
            gui.sendMessageWarning("Error", "La joya seleccionada no existe");
        } else {
            gui.getTxtNombreJoya().setText(gj.getName());
            gui.getComboObjetoJoya().setSelectedIndex(gj.getItem().ordinal());
            gui.getSpinnerPrecioVJoya().setValue(gj.getSellprice());
            gui.getSpinnerPrecioCJoya().setValue(gj.getBuyprice());
            gui.getComboCalidadJoya().setSelectedIndex(gj.getQuality().ordinal());
            gui.getCheckCombinable().setSelected(gj.isCombinable());
            gui.getCheckComerciable().setSelected(gj.isComerciable());
            gui.getSpinnerAtaFisJoya().setValue(gj.getPhysicalattack());
            gui.getSpinnerHRFisJoya().setValue(gj.getPhysicalhitrate());
            gui.getSpinnerDefFisJoya().setValue(gj.getPhysicaldefense());
            gui.getSpinnerEvaFisJoya().setValue(gj.getPhysicalevasion());
            gui.getSpinnerAtaMagJoya().setValue(gj.getMagicalattack());
            gui.getSpinnerDefMagJoya().setValue(gj.getMagicaldefense());
            gui.getSpinnerHRMagJoya().setValue(gj.getMagicalhitrate());
            gui.getSpinnerEvaMagJoya().setValue(gj.getMagicalevasion());
            gui.getSpinnerCritJoya().setValue(gj.getCritical());
            gui.getSpinnerVidaJoya().setValue(gj.getHealth());
            gui.getSpinnerRoboVJoya().setValue(gj.getHealthsteal());
            gui.getSpinnerRoboMJoya().setValue(gj.getManasteal());
            gui.getSpinnerManaJoya().setValue(gj.getMana());
            gui.getSpinnerExpExtraJoya().setValue(gj.getXpbonus());
            gui.getSpinnerDineroExtraJoya().setValue(gj.getMoneybonus());
            gui.recursivelyEnableDisablePanel(gui.getPanelEditorJoya(), true);
        }
    }

    public void saveConfig() {

        filecConfig = YamlConfiguration.loadConfiguration(filec);
        filecConfig.set("breakp", this.breakp);
        filecConfig.set("losep", this.losep);
        filecConfig.set("losepperlore", this.losepperlore);
        filecConfig.set("nothing", this.nothing);
        filecConfig.set("success", this.success);
        try {
            filecConfig.save(filec);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la configuración de joys", "Error", 2);
        }
    }
}
