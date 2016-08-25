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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.armor;

import com.craftcosta.jailrules.rpgcraftcosta.gui.GUI;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumArmor;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumArmorMaterial;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumArmorPart;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumItems;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumQuality;
import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class GUIArmorManager {

    private GUI gui;
    private Map<String, GUISet> listSets;
    private Map<String, Integer> listnum;
    private File filea;
    private File filec;
    private FileConfiguration fileaConfig;
    private FileConfiguration filecConfig;
    private double breakprobability;
    private double nothingprobability;
    private double improveprobability;
    private double detteroriateprobability;
    private EnumItems material;
    private String nameMej;
    private List<String> lores;
    private int lastindex;

    /**
     *
     */
    public GUIArmorManager(GUI gui) {
        this.gui = gui;
        listSets = new HashMap<>();
        listnum = new HashMap<>();
        filea = new File(RPGFinals.armorFile);
        if (!filea.exists()) {
            try {
                filea.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GUIArmorManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        filec = new File(RPGFinals.armorConfigFile);
        if (!filec.exists()) {
            try {
                filec.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GUIArmorManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadConfig();
        loadArmor();
        gui.getComboListSets().addItem("<Selecciona un set de armadura para editar>");
        gui.recursivelyEnableDisablePanel(gui.getPanelEditarSet(), false);
        for (Map.Entry<String, GUISet> entrySet : listSets.entrySet()) {
            gui.getComboListSets().addItem(entrySet.getKey());
        }

    }

    private void loadConfig() {
        filecConfig = YamlConfiguration.loadConfiguration(filec);
        ConfigurationSection section = filecConfig.getConfigurationSection("armor");
        this.material = EnumItems.valueOf(section.getString("material"));
        gui.getComboMejoradorArmadura().setSelectedIndex(this.material.ordinal());
        this.nameMej = section.getString("name");
        gui.getTxtNombreObjetoMejArmadura().setText(this.nameMej);
        this.lores = section.getStringList("lores");
        gui.getjListDescArmadura().setListData(lores.toArray());
        this.breakprobability = filecConfig.getDouble("breakprobability");
        gui.getSpinnerProbRotArmadura().setValue(this.breakprobability);
        this.detteroriateprobability = filecConfig.getDouble("detteroriateprobability");
        gui.getSpinnerProbDetArmadura().setValue(this.detteroriateprobability);
        this.nothingprobability = filecConfig.getDouble("nothingprobability");
        gui.getSpinnerProbSEArmadura().setValue(this.nothingprobability);
        this.improveprobability = filecConfig.getDouble("improveprobability");
        gui.getSpinnerProbExitoArmadura().setValue(this.improveprobability);
    }

    private void loadArmor() {
        fileaConfig = YamlConfiguration.loadConfiguration(filea);
        String armorname;
        String armorpartname;
        EnumArmorMaterial materialS;
        EnumArmorPart materialP;
        EnumArmor materialarmor;
        long sellprice;
        long buyprice;
        EnumQuality quality;
        int armorlevel;
        int level;
        boolean comerciable;
        boolean upgradable;
        double physicaldefense;
        double incphysicaldefense;
        float physicalevasion;
        float incphysicalevasion;
        double magicaldefense;
        double incmagicaldefense;
        float magicalevasion;
        float incmagicalevasion;
        float xpbonus;
        float moneybonus;

        //RECORRER YAML
        Set<String> armorSets = fileaConfig.getKeys(false);
        for (String setName : armorSets) {
            if (this.lastindex <= Integer.parseInt(setName)) {
                this.lastindex = Integer.parseInt(setName);
            }

            ConfigurationSection section = fileaConfig.getConfigurationSection(setName);
            armorname = section.getString("name");
            materialS = EnumArmorMaterial.valueOf(section.getString("material"));
            level = section.getInt("level");
            armorlevel = section.getInt("armorlevel");
            quality = EnumQuality.valueOf(section.getString("quality"));
            comerciable = section.getBoolean("comerciable");
            upgradable = section.getBoolean("upgradable");
            Map<EnumArmorPart, GUIArmor> elset = new HashMap<>();
            GUISet s = new GUISet(Integer.parseInt(setName), armorname, elset);

            this.listSets.put(armorname, s);
            this.listnum.put(armorname, Integer.parseInt(setName));

            Set<String> partes = fileaConfig.getConfigurationSection(setName + ".parts").getKeys(false);
            for (String parte : partes) {
                section = fileaConfig.getConfigurationSection(setName + ".parts." + parte);
                materialP = EnumArmorPart.valueOf(parte);
                physicaldefense = section.getDouble("physicaldefense");
                incphysicaldefense = section.getDouble("incphysicaldefense");
                magicaldefense = section.getDouble("magicaldefense");
                incmagicaldefense = section.getDouble("incmagicaldefense");
                magicalevasion = (float) section.getDouble("magicalevasion");
                incmagicalevasion = (float) section.getDouble("incmagicalevasion");
                physicalevasion = (float) section.getDouble("physicalevasion");
                incphysicalevasion = (float) section.getDouble("incphysicalevasion");
                sellprice = section.getLong("sellprice");
                buyprice = section.getLong("buyprice");
                xpbonus = (float) section.getDouble("xpbonus");
                moneybonus = (float) section.getDouble("moneybonus");

                armorpartname = armorname + " " + materialP.name().toLowerCase();
                s.getSet().put(materialP, new GUIArmor(armorpartname, level, armorlevel, materialS, quality, materialP, upgradable, comerciable, physicaldefense, incphysicaldefense, physicalevasion, incphysicalevasion, magicaldefense, incmagicaldefense, magicalevasion, incmagicalevasion, buyprice, sellprice, xpbonus, moneybonus));
            }
        }
    }

    public Map<String, GUISet> getListSets() {
        return listSets;
    }

    public void setListSets(Map<String, GUISet> listSets) {
        this.listSets = listSets;
    }

    public Map<String, Integer> getListnum() {
        return listnum;
    }

    public void setListnum(Map<String, Integer> listnum) {
        this.listnum = listnum;
    }

    public double getBreakprobability() {
        return breakprobability;
    }

    public void setBreakprobability(double breakprobability) {
        this.breakprobability = breakprobability;
    }

    public double getNothingprobability() {
        return nothingprobability;
    }

    public void setNothingprobability(double nothingprobability) {
        this.nothingprobability = nothingprobability;
    }

    public double getDetteroriateprobability() {
        return detteroriateprobability;
    }

    public void setDetteroriateprobability(double detteroriateprobability) {
        this.detteroriateprobability = detteroriateprobability;
    }

    public EnumItems getMaterial() {
        return material;
    }

    public void setMaterial(EnumItems material) {
        this.material = material;
    }

    public String getNameMej() {
        return nameMej;
    }

    public void setNameMej(String nameMej) {
        this.nameMej = nameMej;
    }

    public List<String> getLores() {
        return lores;
    }

    public void setLores(List<String> lores) {
        this.lores = lores;
    }

    public void saveSet() {
        GUISet gs = getGUISetByName(gui.getTxtNombreSet().getText());
        if (gs == null) {
            gs = new GUISet(lastindex + 1, gui.getTxtNombreArma().getText(), new HashMap<EnumArmorPart, GUIArmor>());
            Map<EnumArmorPart, GUIArmor> partes = new HashMap<>();
            partes.put(EnumArmorPart.HELMET, new GUIArmor(
                    gui.getTxtNombreSet() + " " + EnumArmorPart.HELMET.name().toLowerCase(),
                    (int) gui.getSpinnerNivel().getValue(),
                    0,
                    EnumArmorMaterial.valueOf(EnumArmorMaterial.values()[(int) gui.getComboMaterial().getSelectedItem()].name()),
                    EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidad().getSelectedIndex()].name()),
                    EnumArmorPart.HELMET,
                    gui.getCheckMejorableArmadura().isSelected(),
                    gui.getCheckComerciableArmadura().isSelected(),
                    (double) gui.getSpinnerDefFisCasco().getValue(),
                    (double) gui.getSpinnerMejDefFisCasco().getValue(),
                    (float) gui.getSpinnerEvaFisCasco().getValue(),
                    (float) gui.getSpinnerMejEvaFisCasco().getValue(),
                    (double) gui.getSpinnerDefFisCasco().getValue(),
                    (double) gui.getSpinnerMejDefFisCasco().getValue(),
                    (float) gui.getSpinnerEvaFisCasco().getValue(),
                    (float) gui.getSpinnerMejEvaFisCasco().getValue(),
                    (long) gui.getSpinnerPrecioCCasco().getValue(),
                    (long) gui.getSpinnerPrecioVCasco().getValue(),
                    (float) gui.getSpinnerExpExtraCasco().getValue(),
                    (float) gui.getSpinnerDineroExtraCasco().getValue()));
            partes.put(EnumArmorPart.CHESTPLATE, new GUIArmor(
                    gui.getTxtNombreSet() + " " + EnumArmorPart.CHESTPLATE.name().toLowerCase(),
                    (int) gui.getSpinnerNivel().getValue(),
                    0,
                    EnumArmorMaterial.valueOf(EnumArmorMaterial.values()[(int) gui.getComboMaterial().getSelectedItem()].name()),
                    EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidad().getSelectedIndex()].name()),
                    EnumArmorPart.CHESTPLATE,
                    gui.getCheckMejorableArmadura().isSelected(),
                    gui.getCheckComerciableArmadura().isSelected(),
                    (double) gui.getSpinnerDefFisPechera().getValue(),
                    (double) gui.getSpinnerMejDefFisPechera().getValue(),
                    (float) gui.getSpinnerEvaFisPechera().getValue(),
                    (float) gui.getSpinnerMejEvaFisPechera().getValue(),
                    (double) gui.getSpinnerDefFisPechera().getValue(),
                    (double) gui.getSpinnerMejDefFisPechera().getValue(),
                    (float) gui.getSpinnerEvaFisPechera().getValue(),
                    (float) gui.getSpinnerMejEvaFisPechera().getValue(),
                    (long) gui.getSpinnerPrecioCPechera().getValue(),
                    (long) gui.getSpinnerPrecioVPechera().getValue(),
                    (float) gui.getSpinnerExpExtraPechera().getValue(),
                    (float) gui.getSpinnerDineroExtraPechera().getValue()));
            partes.put(EnumArmorPart.LEGGINGS, new GUIArmor(
                    gui.getTxtNombreSet() + " " + EnumArmorPart.LEGGINGS.name().toLowerCase(),
                    (int) gui.getSpinnerNivel().getValue(),
                    0,
                    EnumArmorMaterial.valueOf(EnumArmorMaterial.values()[(int) gui.getComboMaterial().getSelectedItem()].name()),
                    EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidad().getSelectedIndex()].name()),
                    EnumArmorPart.LEGGINGS,
                    gui.getCheckMejorableArmadura().isSelected(),
                    gui.getCheckComerciableArmadura().isSelected(),
                    (double) gui.getSpinnerDefFisGrebas().getValue(),
                    (double) gui.getSpinnerMejDefFisGrebas().getValue(),
                    (float) gui.getSpinnerEvaFisGrebas().getValue(),
                    (float) gui.getSpinnerMejEvaFisGrebas().getValue(),
                    (double) gui.getSpinnerDefFisGrebas().getValue(),
                    (double) gui.getSpinnerMejDefFisGrebas().getValue(),
                    (float) gui.getSpinnerEvaFisGrebas().getValue(),
                    (float) gui.getSpinnerMejEvaFisGrebas().getValue(),
                    (long) gui.getSpinnerPrecioCGrebas().getValue(),
                    (long) gui.getSpinnerPrecioVGrebas().getValue(),
                    (float) gui.getSpinnerExpExtraGrebas().getValue(),
                    (float) gui.getSpinnerDineroExtraGrebas().getValue()));
            partes.put(EnumArmorPart.BOOTS, new GUIArmor(
                    gui.getTxtNombreSet() + " " + EnumArmorPart.BOOTS.name().toLowerCase(),
                    (int) gui.getSpinnerNivel().getValue(),
                    0,
                    EnumArmorMaterial.valueOf(EnumArmorMaterial.values()[(int) gui.getComboMaterial().getSelectedItem()].name()),
                    EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidad().getSelectedIndex()].name()),
                    EnumArmorPart.BOOTS,
                    gui.getCheckMejorableArmadura().isSelected(),
                    gui.getCheckComerciableArmadura().isSelected(),
                    (double) gui.getSpinnerDefFisBotas().getValue(),
                    (double) gui.getSpinnerMejDefFisBotas().getValue(),
                    (float) gui.getSpinnerEvaFisBotas().getValue(),
                    (float) gui.getSpinnerMejEvaFisBotas().getValue(),
                    (double) gui.getSpinnerDefFisBotas().getValue(),
                    (double) gui.getSpinnerMejDefFisBotas().getValue(),
                    (float) gui.getSpinnerEvaFisBotas().getValue(),
                    (float) gui.getSpinnerMejEvaFisBotas().getValue(),
                    (long) gui.getSpinnerPrecioCBotas().getValue(),
                    (long) gui.getSpinnerPrecioVBotas().getValue(),
                    (float) gui.getSpinnerExpExtraBotas().getValue(),
                    (float) gui.getSpinnerDineroExtraBotas().getValue()));
            gs.setSet(partes);
            this.listSets.put(gs.getSetname(), gs);
            this.listnum.put(gs.getSetname(), lastindex + 1);
            gui.getComboListSets().addItem(gs.getSetname());

            gui.recursivelyEnableDisablePanel(gui.getPanelEditarSet(), false);
            saveSetToFile(gs);
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "Ya existe un set con ese mismo nombre\n¿Quieres sobreescribirlo?", "Sobreescribir set: " + gs.getSetname(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                gs.setSetname(gui.getTxtNombreSet().getText());
                Map<EnumArmorPart, GUIArmor> partes = gs.getSet();
                GUIArmor casco = partes.get(EnumArmorPart.HELMET);
                casco.setName(gs.getSetname() + " " + EnumArmorPart.HELMET.name().toLowerCase());
                casco.setLevel((int) gui.getSpinnerNivel().getValue());
                casco.setMaterial(EnumArmorMaterial.valueOf(EnumArmorMaterial.values()[(int) gui.getComboMaterial().getSelectedItem()].name()));
                casco.setQuality(EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidad().getSelectedIndex()].name()));
                casco.setComerciable(gui.getCheckComerciableArmadura().isSelected());
                casco.setUpgradable(gui.getCheckMejorableArmadura().isSelected());
                casco.setPhysicaldefense((double) gui.getSpinnerDefFisCasco().getValue());
                casco.setIncphysicaldefense((double) gui.getSpinnerMejDefFisCasco().getValue());
                casco.setPhysicalevasion((float) gui.getSpinnerEvaFisCasco().getValue());
                casco.setIncphysicalevasion((float) gui.getSpinnerMejEvaFisCasco().getValue());
                casco.setMagicaldefense((double) gui.getSpinnerDefFisCasco().getValue());
                casco.setIncmagicaldefense((double) gui.getSpinnerMejDefFisCasco().getValue());
                casco.setMagicalevasion((float) gui.getSpinnerEvaFisCasco().getValue());
                casco.setIncmagicalevasion((float) gui.getSpinnerMejEvaFisCasco().getValue());
                casco.setBuyprice((long) gui.getSpinnerPrecioCCasco().getValue());
                casco.setSellprice((long) gui.getSpinnerPrecioVCasco().getValue());
                casco.setMoneybonus((float) gui.getSpinnerDineroExtraCasco().getValue());
                casco.setXpbonus((float) gui.getSpinnerExpExtraCasco().getValue());
                GUIArmor pechera = partes.get(EnumArmorPart.CHESTPLATE);
                pechera.setName(gs.getSetname() + " " + EnumArmorPart.CHESTPLATE.name().toLowerCase());
                pechera.setLevel((int) gui.getSpinnerNivel().getValue());
                pechera.setMaterial(EnumArmorMaterial.valueOf(EnumArmorMaterial.values()[(int) gui.getComboMaterial().getSelectedItem()].name()));
                pechera.setQuality(EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidad().getSelectedIndex()].name()));
                pechera.setComerciable(gui.getCheckComerciableArmadura().isSelected());
                pechera.setUpgradable(gui.getCheckMejorableArmadura().isSelected());
                pechera.setPhysicaldefense((double) gui.getSpinnerDefFisPechera().getValue());
                pechera.setIncphysicaldefense((double) gui.getSpinnerMejDefFisPechera().getValue());
                pechera.setPhysicalevasion((float) gui.getSpinnerEvaFisPechera().getValue());
                pechera.setIncphysicalevasion((float) gui.getSpinnerMejEvaFisPechera().getValue());
                pechera.setMagicaldefense((double) gui.getSpinnerDefFisPechera().getValue());
                pechera.setIncmagicaldefense((double) gui.getSpinnerMejDefFisPechera().getValue());
                pechera.setMagicalevasion((float) gui.getSpinnerEvaFisPechera().getValue());
                pechera.setIncmagicalevasion((float) gui.getSpinnerMejEvaFisPechera().getValue());
                pechera.setBuyprice((long) gui.getSpinnerPrecioCPechera().getValue());
                pechera.setSellprice((long) gui.getSpinnerPrecioVPechera().getValue());
                pechera.setMoneybonus((float) gui.getSpinnerDineroExtraPechera().getValue());
                pechera.setXpbonus((float) gui.getSpinnerExpExtraPechera().getValue());
                GUIArmor grebas = partes.get(EnumArmorPart.LEGGINGS);
                grebas.setName(gs.getSetname() + " " + EnumArmorPart.LEGGINGS.name().toLowerCase());
                grebas.setLevel((int) gui.getSpinnerNivel().getValue());
                grebas.setMaterial(EnumArmorMaterial.valueOf(EnumArmorMaterial.values()[(int) gui.getComboMaterial().getSelectedItem()].name()));
                grebas.setQuality(EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidad().getSelectedIndex()].name()));
                grebas.setComerciable(gui.getCheckComerciableArmadura().isSelected());
                grebas.setUpgradable(gui.getCheckMejorableArmadura().isSelected());
                grebas.setPhysicaldefense((double) gui.getSpinnerDefFisGrebas().getValue());
                grebas.setIncphysicaldefense((double) gui.getSpinnerMejDefFisGrebas().getValue());
                grebas.setPhysicalevasion((float) gui.getSpinnerEvaFisGrebas().getValue());
                grebas.setIncphysicalevasion((float) gui.getSpinnerMejEvaFisGrebas().getValue());
                grebas.setMagicaldefense((double) gui.getSpinnerDefFisGrebas().getValue());
                grebas.setIncmagicaldefense((double) gui.getSpinnerMejDefFisGrebas().getValue());
                grebas.setMagicalevasion((float) gui.getSpinnerEvaFisGrebas().getValue());
                grebas.setIncmagicalevasion((float) gui.getSpinnerMejEvaFisGrebas().getValue());
                grebas.setBuyprice((long) gui.getSpinnerPrecioCGrebas().getValue());
                grebas.setSellprice((long) gui.getSpinnerPrecioVGrebas().getValue());
                grebas.setMoneybonus((float) gui.getSpinnerDineroExtraGrebas().getValue());
                grebas.setXpbonus((float) gui.getSpinnerExpExtraGrebas().getValue());
                GUIArmor botas = partes.get(EnumArmorPart.BOOTS);
                botas.setName(gs.getSetname() + " " + EnumArmorPart.HELMET.name().toLowerCase());
                botas.setLevel((int) gui.getSpinnerNivel().getValue());
                botas.setMaterial(EnumArmorMaterial.valueOf(EnumArmorMaterial.values()[(int) gui.getComboMaterial().getSelectedItem()].name()));
                botas.setQuality(EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidad().getSelectedIndex()].name()));
                botas.setComerciable(gui.getCheckComerciableArmadura().isSelected());
                botas.setUpgradable(gui.getCheckMejorableArmadura().isSelected());
                botas.setPhysicaldefense((double) gui.getSpinnerDefFisBotas().getValue());
                botas.setIncphysicaldefense((double) gui.getSpinnerMejDefFisBotas().getValue());
                botas.setPhysicalevasion((float) gui.getSpinnerEvaFisBotas().getValue());
                botas.setIncphysicalevasion((float) gui.getSpinnerMejEvaFisBotas().getValue());
                botas.setMagicaldefense((double) gui.getSpinnerDefFisBotas().getValue());
                botas.setIncmagicaldefense((double) gui.getSpinnerMejDefFisBotas().getValue());
                botas.setMagicalevasion((float) gui.getSpinnerEvaFisBotas().getValue());
                botas.setIncmagicalevasion((float) gui.getSpinnerMejEvaFisBotas().getValue());
                botas.setBuyprice((long) gui.getSpinnerPrecioCBotas().getValue());
                botas.setSellprice((long) gui.getSpinnerPrecioVBotas().getValue());
                botas.setMoneybonus((float) gui.getSpinnerDineroExtraBotas().getValue());
                botas.setXpbonus((float) gui.getSpinnerExpExtraBotas().getValue());
                gs.getSet().put(EnumArmorPart.HELMET, casco);
                gs.getSet().put(EnumArmorPart.CHESTPLATE, pechera);
                gs.getSet().put(EnumArmorPart.LEGGINGS, grebas);
                gs.getSet().put(EnumArmorPart.BOOTS, botas);
                this.listSets.put(gs.getSetname(), gs);
                gui.recursivelyEnableDisablePanel(gui.getPanelEditarSet(), false);
                saveSetToFile(gs);
            }
        }
    }

    public void saveConfig() {

        filecConfig = YamlConfiguration.loadConfiguration(filec);
        this.breakprobability = (double) gui.getSpinnerProbRotArmadura().getValue();
        filecConfig.set("breakprobability", this.breakprobability);
        this.breakprobability = (double) gui.getSpinnerProbRotArmadura().getValue();
        filecConfig.set("detteroriateprobability", this.detteroriateprobability);
        this.breakprobability = (double) gui.getSpinnerProbRotArmadura().getValue();
        filecConfig.set("nothingprobability", this.nothingprobability);
        this.breakprobability = (double) gui.getSpinnerProbRotArmadura().getValue();
        filecConfig.set("improveprobability", this.improveprobability);
        ConfigurationSection section = filecConfig.createSection("armor");
        this.material = EnumItems.valueOf(EnumItems.values()[(int) gui.getComboMejoradorArmadura().getSelectedIndex()].name());
        section.set("material", this.material.getMaterial());
        this.nameMej = gui.getTxtNombreObjetoMejArmadura().getText();
        section.set("name", this.getNameMej());
        this.lores = new ArrayList<>();
        for (int i = 0; i < gui.getjListDescArmadura().getModel().getSize(); i++) {
            this.lores.add(gui.getjListDescArma().getModel().getElementAt(i).toString());
        }
        section.set("lores", this.lores);
        try {
            filecConfig.save(filec);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la configuración de sets", "Error", 2);
        }
    }

    private void saveSetToFile(GUISet gs) {
        fileaConfig = YamlConfiguration.loadConfiguration(filea);
        int index;
        GUISet check = getGUISetByName(gs.getSetname());
        if (check == null) {
            this.lastindex++;
            index = lastindex;
        } else {
            index = this.listnum.get(gs.getSetname());
        }
        GUIArmor casco = gs.getSet().get(EnumArmorPart.HELMET);
        GUIArmor pechera = gs.getSet().get(EnumArmorPart.CHESTPLATE);
        GUIArmor grebas = gs.getSet().get(EnumArmorPart.LEGGINGS);
        GUIArmor botas = gs.getSet().get(EnumArmorPart.BOOTS);
        ConfigurationSection section = fileaConfig.createSection("" + index);
        section.set("name", gs.getSetname());
        section.set("level", casco.getLevel());
        section.set("quality", casco.getQuality().getName());
        section.set("armorlevel", casco.getArmorlevel());
        section.set("upgradable", casco.isUpgradable());
        section.set("comerciable", casco.isComerciable());
        section.set("material", casco.getMaterial().getName());
        ConfigurationSection s2 = section.createSection("parts");
        ConfigurationSection s3 = s2.createSection("HELMET");
        s3.set("physicaldefense", casco.getPhysicaldefense());
        s3.set("incphysicaldefense", casco.getIncphysicaldefense());
        s3.set("physicalevasion", casco.getPhysicalevasion());
        s3.set("incphysicalevasion", casco.getIncphysicalevasion());
        s3.set("magicaldefense", casco.getMagicaldefense());
        s3.set("incmagicaldefense", casco.getIncmagicalevasion());
        s3.set("magicalevasion", casco.getMagicaldefense());
        s3.set("incmagicalevasion", casco.getIncmagicalevasion());
        s3.set("buyprice", casco.getBuyprice());
        s3.set("sellprice", casco.getSellprice());
        s3.set("xpbonus", casco.getXpbonus());
        s3.set("moneybonus", casco.getMoneybonus());
        s3 = s2.createSection("CHESTPLATE");
        s3.set("physicaldefense", pechera.getPhysicaldefense());
        s3.set("incphysicaldefense", pechera.getIncphysicaldefense());
        s3.set("physicalevasion", pechera.getPhysicalevasion());
        s3.set("incphysicalevasion", pechera.getIncphysicalevasion());
        s3.set("magicaldefense", pechera.getMagicaldefense());
        s3.set("incmagicaldefense", pechera.getIncmagicalevasion());
        s3.set("magicalevasion", pechera.getMagicaldefense());
        s3.set("incmagicalevasion", pechera.getIncmagicalevasion());
        s3.set("buyprice", pechera.getBuyprice());
        s3.set("sellprice", pechera.getSellprice());
        s3.set("xpbonus", pechera.getXpbonus());
        s3.set("moneybonus", pechera.getMoneybonus());
        s3 = s2.createSection("LEGGINGS");
        s3.set("physicaldefense", grebas.getPhysicaldefense());
        s3.set("incphysicaldefense", grebas.getIncphysicaldefense());
        s3.set("physicalevasion", grebas.getPhysicalevasion());
        s3.set("incphysicalevasion", grebas.getIncphysicalevasion());
        s3.set("magicaldefense", grebas.getMagicaldefense());
        s3.set("incmagicaldefense", grebas.getIncmagicalevasion());
        s3.set("magicalevasion", grebas.getMagicaldefense());
        s3.set("incmagicalevasion", grebas.getIncmagicalevasion());
        s3.set("buyprice", grebas.getBuyprice());
        s3.set("sellprice", grebas.getSellprice());
        s3.set("xpbonus", grebas.getXpbonus());
        s3.set("moneybonus", grebas.getMoneybonus());
        s3 = s2.createSection("BOOTS");
        s3.set("physicaldefense", botas.getPhysicaldefense());
        s3.set("incphysicaldefense", botas.getIncphysicaldefense());
        s3.set("physicalevasion", botas.getPhysicalevasion());
        s3.set("incphysicalevasion", botas.getIncphysicalevasion());
        s3.set("magicaldefense", botas.getMagicaldefense());
        s3.set("incmagicaldefense", botas.getIncmagicalevasion());
        s3.set("magicalevasion", botas.getMagicaldefense());
        s3.set("incmagicalevasion", botas.getIncmagicalevasion());
        s3.set("buyprice", botas.getBuyprice());
        s3.set("sellprice", botas.getSellprice());
        s3.set("xpbonus", botas.getXpbonus());
        s3.set("moneybonus", botas.getMoneybonus());
        try {
            fileaConfig.save(filea);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el arma", "Error", 2);
        }
    }

    public void deleteSetSelected() {
        GUISet gs = getGUISetByName(gui.getComboListSets().getSelectedItem().toString());
        if (gs == null) {
            gui.sendMessageWarning("Error", "El set seleccionado no existe");
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "¿Estas seguro de querer eliminar el set?", "Borrar set: " + gs.getSetname(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                //borrar clase de el combo
                gui.getComboListSets().removeItem(gs.getSetname());
                gui.getComboListSets().setSelectedIndex(0);
                //Borrar clase de el fichero y de la memoria
                listSets.remove(gs.getSetname());
                //listnum.remove(gw.getName());
                deleteSetFromFile(gs);
            }
        }
    }

    private void deleteSetFromFile(GUISet gs) {
        try {
            fileaConfig = YamlConfiguration.loadConfiguration(filea);
            int index = listnum.get(gs.getSetname());
            listnum.remove(gs.getSetname());
            fileaConfig.set("" + index, null);
            fileaConfig.save(filea);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar eliminar el set del fichero de configuración", "Error", 2);
        }
    }

    public void loadValuesForSetSelected() {
        GUISet gs = getGUISetByName(gui.getComboListSets().getSelectedItem().toString());
        if (gs == null) {
            gui.sendMessageWarning("Error", "El set seleccionado no existe");
        } else {
            GUIArmor casco = gs.getSet().get(EnumArmorPart.HELMET);
            GUIArmor pechera = gs.getSet().get(EnumArmorPart.CHESTPLATE);
            GUIArmor grebas = gs.getSet().get(EnumArmorPart.LEGGINGS);
            GUIArmor botas = gs.getSet().get(EnumArmorPart.BOOTS);
            gui.getTxtNombreSet().setText(gs.getSetname());
            gui.getComboMaterial().setSelectedIndex(casco.getMaterial().ordinal());
            gui.getSpinnerNivel().setValue(casco.getLevel());
            gui.getComboCalidad().setSelectedIndex(casco.getQuality().ordinal());
            gui.getCheckMejorableArmadura().setSelected(casco.isUpgradable());
            gui.getCheckComerciableArmadura().setSelected(casco.isComerciable());
            //Partes
            gui.getSpinnerPrecioVCasco().setValue((long) casco.getSellprice());
            gui.getSpinnerPrecioCCasco().setValue((long) casco.getBuyprice());
            gui.getSpinnerDefFisCasco().setValue((double) casco.getPhysicaldefense());
            gui.getSpinnerMejDefFisCasco().setValue((double) casco.getIncphysicaldefense());
            gui.getSpinnerEvaFisCasco().setValue((float) casco.getPhysicalevasion());
            gui.getSpinnerMejEvaFisCasco().setValue((float) casco.getIncphysicalevasion());
            gui.getSpinnerExpExtraCasco().setValue((float) casco.getXpbonus());
            gui.getSpinnerDineroExtraCasco().setValue((float) casco.getMoneybonus());

            gui.getSpinnerPrecioVPechera().setValue((long) pechera.getSellprice());
            gui.getSpinnerPrecioCPechera().setValue((long) pechera.getBuyprice());
            gui.getSpinnerDefFisPechera().setValue((double) pechera.getPhysicaldefense());
            gui.getSpinnerMejDefFisPechera().setValue((double) pechera.getIncphysicaldefense());
            gui.getSpinnerEvaFisPechera().setValue((float) pechera.getPhysicalevasion());
            gui.getSpinnerMejEvaFisPechera().setValue((float) pechera.getIncphysicalevasion());
            gui.getSpinnerExpExtraPechera().setValue((float) pechera.getXpbonus());
            gui.getSpinnerDineroExtraPechera().setValue((float) pechera.getMoneybonus());

            gui.getSpinnerPrecioVGrebas().setValue((long) grebas.getSellprice());
            gui.getSpinnerPrecioCGrebas().setValue((long) grebas.getBuyprice());
            gui.getSpinnerDefFisGrebas().setValue((double) grebas.getPhysicaldefense());
            gui.getSpinnerMejDefFisGrebas().setValue((double) grebas.getIncphysicaldefense());
            gui.getSpinnerEvaFisGrebas().setValue((float) grebas.getPhysicalevasion());
            gui.getSpinnerMejEvaFisGrebas().setValue((float) grebas.getIncphysicalevasion());
            gui.getSpinnerExpExtraGrebas().setValue((float) grebas.getXpbonus());
            gui.getSpinnerDineroExtraGrebas().setValue((float) grebas.getMoneybonus());

            gui.getSpinnerPrecioVBotas().setValue((long) botas.getSellprice());
            gui.getSpinnerPrecioCBotas().setValue((long) botas.getBuyprice());
            gui.getSpinnerDefFisBotas().setValue((double) botas.getPhysicaldefense());
            gui.getSpinnerMejDefFisBotas().setValue((double) botas.getIncphysicaldefense());
            gui.getSpinnerEvaFisBotas().setValue((float) botas.getPhysicalevasion());
            gui.getSpinnerMejEvaFisBotas().setValue((float) botas.getIncphysicalevasion());
            gui.getSpinnerExpExtraBotas().setValue((float) botas.getXpbonus());
            gui.getSpinnerDineroExtraBotas().setValue((float) botas.getMoneybonus());

            gui.recursivelyEnableDisablePanel(gui.getPanelEditarSet(), true);
        }
    }

    public String getUpgraderName() {
        return this.nameMej;
    }

    private GUISet getGUISetByName(String text) {
        return this.listSets.get(text);
    }
}
