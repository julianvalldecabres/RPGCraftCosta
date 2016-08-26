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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.weapons;

import com.craftcosta.jailrules.rpgcraftcosta.gui.GUI;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumItems;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumQuality;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumWeapon;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class GUIWeaponManager {

    private EnumItems ObjMej;
    private String NomObjMej;
    private List<String> LorObjMej;
    private int lastindex = -1;
    private float probRot;
    private float probDet;
    private float probSnE;
    private float probExito;
    private File filew;
    private File filec;
    private FileConfiguration filewConfig;
    private FileConfiguration filecConfig;
    private Map<String, GUIWeapon> listWeapons;
    private Map<String, Integer> listnum;
    private GUI gui;

    public GUIWeaponManager(GUI gui) {
        this.gui = gui;
        filew = new File(RPGFinals.weaponFile);
        if (!filew.exists()) {
            try {
                filew.createNewFile();
            } catch (IOException ex) {
            }
        }
        filec = new File(RPGFinals.weaponsConfig);
        if (!filec.exists()) {
            try {
                filec.createNewFile();
            } catch (IOException ex) {

            }
        }
        listWeapons = new HashMap<>();
        listnum = new HashMap<>();
        loadWeaponsConfig();
        loadWeapons();
        gui.getComboListaArmas().addItem("<Selecciona un arma para editar>");
        gui.recursivelyEnableDisablePanel(gui.getPanelEditorArma(), false);
        for (Map.Entry<String, GUIWeapon> entrySet : listWeapons.entrySet()) {
            gui.getComboListaArmas().addItem(entrySet.getKey());
        }
        System.out.println("Cargado config armas");
    }

    private void loadWeaponsConfig() {
        filecConfig = YamlConfiguration.loadConfiguration(filec);
        this.probRot = (float) filecConfig.getDouble("breakprobability");
        gui.getSpinnerProbRotArma().setValue(this.probRot);
        this.probDet = (float) filecConfig.getDouble("detteroriateprobability");
        gui.getSpinnerProbDetArma().setValue(this.probDet);
        this.probSnE = (float) filecConfig.getDouble("nothingprobability");
        gui.getSpinnerProbSEArma().setValue(this.probSnE);
        this.probExito = (float) filecConfig.getDouble("improveprobability");
        gui.getSpinnerProbExitoArma().setValue(this.probExito);
        ConfigurationSection section = filecConfig.getConfigurationSection("weapon");
        this.NomObjMej = section.getString("name");
        gui.getTxtNombreObjetoMejArma().setText(NomObjMej);
        this.LorObjMej = section.getStringList("lores");
        gui.getjListDescArma().setListData(this.LorObjMej.toArray());
        this.ObjMej = EnumItems.valueOf(section.getString("material"));
        gui.getComboMejoradorArma().setSelectedIndex(this.ObjMej.ordinal());
    }

    private void loadWeapons() {
        filewConfig = YamlConfiguration.loadConfiguration(filew);
        String name;
        EnumQuality quality;
        EnumWeapon item;
        int level;
        int weaponlevel;
        long sellprice;
        long buyprice;
        boolean upgradable;
        boolean comerciable;
        double physicalattack;
        double incphysicalattack;
        float physicalhitrate;
        float incphysicalhitrate;
        double magicalattack;
        double incmagicalattack;
        float magicalhitrate;
        float incmagicalhitrate;
        float critical;
        float inccritical;
        float healthsteal;
        float inchealthsteal;
        float manasteal;
        float incmanasteal;
        long xpbonus;
        long moneybonus;
        Set<String> armas = filewConfig.getKeys(false);
        for (String armaid : armas) {
            if (this.lastindex <= Integer.parseInt(armaid)) {
                this.lastindex = Integer.parseInt(armaid);
            }
            ConfigurationSection section = filewConfig.getConfigurationSection(armaid);
            name = section.getString("name");
            item = EnumWeapon.valueOf(section.getString("item"));
            comerciable = section.getBoolean("comerciable");
            sellprice = section.getInt("sellprice");
            buyprice = section.getInt("buyprice");
            quality = EnumQuality.valueOf(section.getString("quality"));
            level = section.getInt("level");
            upgradable = section.getBoolean("upgradable");
            weaponlevel = section.getInt("weaponlevel");
            physicalattack = section.getDouble("physicalattack");
            incphysicalattack = section.getDouble("incphysicalattack");
            physicalhitrate = (float) section.getDouble("physicalhitrate");
            incphysicalhitrate = (float) section.getDouble("incphysicalhitrate");
            magicalattack = section.getDouble("magicalattack");
            incmagicalattack = section.getDouble("incmagicalattack");
            magicalhitrate = (float) section.getDouble("magicalhitrate");
            incmagicalhitrate = (float) section.getDouble("incmagicalhitrate");
            critical = (float) section.getDouble("critical");
            inccritical = (float) section.getDouble("inccritical");
            healthsteal = (float) section.getDouble("healthsteal");
            inchealthsteal = (float) section.getDouble("inchealthsteal");
            manasteal = (float) section.getDouble("manasteal");
            incmanasteal = (float) section.getDouble("incmanasteal");
            xpbonus = section.getLong("xpbonus");
            moneybonus = section.getLong("moneybonus");
            this.listWeapons.put(name, new GUIWeapon(name, item, level, weaponlevel, sellprice, buyprice, quality, upgradable, comerciable, physicalattack, incphysicalattack, physicalhitrate, incphysicalhitrate, magicalattack, incmagicalattack, magicalhitrate, incmagicalhitrate, critical, inccritical, healthsteal, inchealthsteal, manasteal, incmanasteal, xpbonus, moneybonus));
            this.listnum.put(name, Integer.parseInt(armaid));
        }
    }

    public void saveWeapon() {
        GUIWeapon gw = getGUIWeaponByName(gui.getTxtNombreArma().getText());
        if (gw == null) {

            gw = new GUIWeapon(gui.getTxtNombreArma().getText(),
                    EnumWeapon.valueOf(EnumWeapon.values()[(int) gui.getComboTipoArma().getSelectedItem()].name()),
                    (int) gui.getSpinnerNivelMinArma().getValue(),
                    (int) gui.getSpinnerNivelIniArma().getValue(),
                    (long) gui.getSpinnerPrecioVArma().getValue(),
                    (long) gui.getSpinnerPrecioCArma().getValue(),
                    EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidadArma().getSelectedIndex()].name()),
                    gui.getCheckMejorableArma().isSelected(),
                    gui.getCheckComerciableArma().isSelected(),
                    (double) gui.getSpinnerAtaFisArma().getValue(),
                    (double) gui.getSpinnerMejAtaFisArma().getValue(),
                    (float) gui.getSpinnerHRFisArma().getValue(),
                    (float) gui.getSpinnerMejHRFisArma().getValue(),
                    (double) gui.getSpinnerAtaMagArma().getValue(),
                    (double) gui.getSpinnerMejAtaMagArma().getValue(),
                    (float) gui.getSpinnerHRMagArma().getValue(),
                    (float) gui.getSpinnerMejHRMagArma().getValue(),
                    (float) gui.getSpinnerCritArma().getValue(),
                    (float) gui.getSpinnerMejCritArma().getValue(),
                    (float) gui.getSpinnerRoboVArma().getValue(),
                    (float) gui.getSpinnerMejRoboVArma().getValue(),
                    (float) gui.getSpinnerRoboMArma().getValue(),
                    (float) gui.getSpinnerMejRoboMArma().getValue(),
                    (float) gui.getSpinnerExpExtraArma().getValue(),
                    (float) gui.getSpinnerDineroExtraArma().getValue());
            this.listWeapons.put(gw.getName(), gw);
            this.listnum.put(gw.getName(), lastindex + 1);
            gui.getComboListaArmas().addItem(gw.getName());

            gui.recursivelyEnableDisablePanel(gui.getPanelEditorArma(), false);
            saveWeaponToFile(gw);
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "Ya existe un arma con ese nombre\n¿Quieres sobreescribirlo?", "Sobreescribir arma: " + gw.getName(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                gw.setItem(EnumWeapon.valueOf(EnumWeapon.values()[(int) gui.getComboTipoArma().getSelectedItem()].name()));
                gw.setQuality(EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidadArma().getSelectedIndex()].name()));
                gw.setLevel((int) gui.getSpinnerNivelMinArma().getValue());
                gw.setWeaponlevel((int) gui.getSpinnerNivelIniArma().getValue());
                gw.setSellprice((long) gui.getSpinnerPrecioVArma().getValue());
                gw.setBuyprice((long) gui.getSpinnerPrecioCArma().getValue());
                gw.setUpgradable(gui.getCheckMejorableArma().isSelected());
                gw.setComerciable(gui.getCheckComerciableArma().isSelected());
                gw.setPhysicalattack((double) gui.getSpinnerAtaFisArma().getValue());
                gw.setIncphysicalattack((double) gui.getSpinnerMejAtaFisArma().getValue());
                gw.setPhysicalhitrate((float) gui.getSpinnerHRFisArma().getValue());
                gw.setIncphysicalhitrate((float) gui.getSpinnerMejHRFisArma().getValue());
                gw.setMagicalattack((double) gui.getSpinnerAtaMagArma().getValue());
                gw.setIncmagicalattack((double) gui.getSpinnerMejAtaMagArma().getValue());
                gw.setMagicalhitrate((float) gui.getSpinnerHRMagArma().getValue());
                gw.setIncmagicalhitrate((float) gui.getSpinnerMejHRMagArma().getValue());
                gw.setCritical((float) gui.getSpinnerCritArma().getValue());
                gw.setInccritical((float) gui.getSpinnerMejCritArma().getValue());
                gw.setHealthsteal((float) gui.getSpinnerRoboVArma().getValue());
                gw.setInchealthsteal((float) gui.getSpinnerMejRoboVArma().getValue());
                gw.setManasteal((float) gui.getSpinnerRoboMArma().getValue());
                gw.setIncmanasteal((float) gui.getSpinnerMejRoboMArma().getValue());
                gw.setXpbonus((float) gui.getSpinnerExpExtraArma().getValue());
                gw.setMoneybonus((float) gui.getSpinnerDineroExtraArma().getValue());

                this.listWeapons.put(gw.getName(), gw);
                gui.recursivelyEnableDisablePanel(gui.getPanelEditorObjetos(), false);
                saveWeaponToFile(gw);
            }
        }
    }

    public EnumItems getObjMej() {
        return ObjMej;
    }

    public void setObjMej(EnumItems ObjMej) {
        this.ObjMej = ObjMej;
    }

    public String getNomObjMej() {
        return NomObjMej;
    }

    public void setNomObjMej(String NomObjMej) {
        this.NomObjMej = NomObjMej;
    }

    public List<String> getLorObjMej() {
        return LorObjMej;
    }

    public void setLorObjMej(List<String> LorObjMej) {
        this.LorObjMej = LorObjMej;
    }

    public float getProbRot() {
        return probRot;
    }

    public void setProbRot(float probRot) {
        this.probRot = probRot;
    }

    public float getProbDet() {
        return probDet;
    }

    public void setProbDet(float probDet) {
        this.probDet = probDet;
    }

    public float getProbSnE() {
        return probSnE;
    }

    public void setProbSnE(float probSnE) {
        this.probSnE = probSnE;
    }

    public float getProbExito() {
        return probExito;
    }

    public void setProbExito(float probExito) {
        this.probExito = probExito;
    }

    private GUIWeapon getGUIWeaponByName(String text) {
        return this.listWeapons.get(text);
    }

    public void saveConfig() {
        filecConfig = YamlConfiguration.loadConfiguration(filec);
        this.probRot = (float) gui.getSpinnerProbRotArma().getValue();
        filecConfig.set("breakprobability", this.probRot);
        this.probRot = (float) gui.getSpinnerProbDetArma().getValue();
        filecConfig.set("detteroriateprobability", this.probDet);
        this.probRot = (float) gui.getSpinnerProbSEArma().getValue();
        filecConfig.set("nothingprobability", this.probSnE);
        this.probRot = (float) gui.getSpinnerProbExitoArma().getValue();
        filecConfig.set("improveprobability", this.probExito);
        ConfigurationSection section = filecConfig.createSection("weapon");
        this.ObjMej = EnumItems.valueOf(EnumItems.values()[(int) gui.getComboMejoradorArma().getSelectedIndex()].name());
        section.set("material", this.LorObjMej);
        this.NomObjMej = gui.getTxtNombreObjetoMejArma().getText();
        section.set("name", gui.getTxtNombreObjetoMejArma());
        this.LorObjMej = new ArrayList<>();
        for (int i = 0; i < gui.getjListDescArma().getModel().getSize(); i++) {
            this.LorObjMej.add(gui.getjListDescArma().getModel().getElementAt(i).toString());
        }
        section.set("lores", this.LorObjMej);
        try {
            filecConfig.save(filec);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la configuración de armas", "Error", 2);
        }
    }

    private void saveWeaponToFile(GUIWeapon gw) {
        filewConfig = YamlConfiguration.loadConfiguration(filew);
        int index;
        GUIWeapon check = getGUIWeaponByName(gw.getName());
        if (check == null) {
            this.lastindex++;
            index = lastindex;
        } else {
            index = this.listnum.get(gw.getName());
        }
        ConfigurationSection section = filewConfig.createSection("" + index);
        section.set("name", gw.getName());
        section.set("item", gw.getItem().getMaterial());
        section.set("quality", gw.getQuality().getName());
        section.set("level", gw.getLevel());
        section.set("weaponlevel", gw.getWeaponlevel());
        section.set("upgradable", gw.isUpgradable());
        section.set("comerciable", gw.isComerciable());
        section.set("buyprice", gw.getBuyprice());
        section.set("sellprice", gw.getSellprice());
        section.set("physicalattack", gw.getPhysicalattack());
        section.set("incphysicalattack", gw.getIncphysicalattack());
        section.set("physicalhitrate", gw.getPhysicalhitrate());
        section.set("incphysicalhitrate", gw.getIncphysicalhitrate());
        section.set("magicalattack", gw.getMagicalattack());
        section.set("incmagicalattack", gw.getIncmagicalattack());
        section.set("magicalhitrate", gw.getMagicalhitrate());
        section.set("incmagicalhitrate", gw.getIncmagicalhitrate());
        section.set("critical", gw.getCritical());
        section.set("inccritical", gw.getInccritical());
        section.set("healthsteal", gw.getHealthsteal());
        section.set("inchealthsteal", gw.getInchealthsteal());
        section.set("manasteal", gw.getManasteal());
        section.set("incmanasteal", gw.getIncmanasteal());
        section.set("xpbonus", gw.getXpbonus());
        section.set("moneybonus", gw.getMoneybonus());
        try {
            filewConfig.save(filew);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el arma", "Error", 2);
        }
    }

    public void deleteWeaponSelected() {
        GUIWeapon gw = getGUIWeaponByName(gui.getComboListaArmas().getSelectedItem().toString());
        if (gw == null) {
            gui.sendMessageWarning("Error", "El arma seleccionada no existe");
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "¿Estas seguro de querer eliminar el arma?", "Borrar arma: " + gw.getName(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                //borrar clase de el combo
                gui.getComboListaArmas().removeItem(gw.getName());
                gui.getComboListaArmas().setSelectedIndex(0);
                //Borrar clase de el fichero y de la memoria
                listWeapons.remove(gw.getName());
                //listnum.remove(gw.getName());
                deleteWeaponFromFile(gw);
            }
        }
    }

    private void deleteWeaponFromFile(GUIWeapon gw) {
        try {
            filewConfig = YamlConfiguration.loadConfiguration(filew);
            int index = listnum.get(gw.getName());
            listnum.remove(gw.getName());
            filewConfig.set("" + index, null);
            filewConfig.save(filew);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar eliminar el arma del fichero de configuración", "Error", 2);
        }
    }

    public void loadValuesForWeaponSelected() {
        GUIWeapon gw = getGUIWeaponByName(gui.getComboListaArmas().getSelectedItem().toString());
        if (gw == null) {
            gui.sendMessageWarning("Error", "El arma seleccionada no existe");
        } else {
            gui.getTxtNombreArma().setText(gw.getName());
            gui.getComboTipoArma().setSelectedIndex(gw.getItem().ordinal());
            gui.getSpinnerNivelMinArma().setValue(gw.getLevel());
            gui.getSpinnerNivelIniArma().setValue(gw.getWeaponlevel());
            gui.getSpinnerPrecioVArma().setValue(gw.getSellprice());
            gui.getSpinnerPrecioCArma().setValue(gw.getBuyprice());
            gui.getComboCalidadArma().setSelectedIndex(gw.getQuality().ordinal());
            gui.getCheckMejorableArma().setSelected(gw.isUpgradable());
            gui.getCheckComerciableArma().setSelected(gw.isComerciable());
            gui.getSpinnerAtaFisArma().setValue(gw.getPhysicalattack());
            gui.getSpinnerMejAtaFisArma().setValue(gw.getIncphysicalattack());
            gui.getSpinnerHRFisArma().setValue(gw.getPhysicalhitrate());
            gui.getSpinnerMejHRFisArma().setValue(gw.getIncphysicalhitrate());
            gui.getSpinnerAtaMagArma().setValue(gw.getMagicalattack());
            gui.getSpinnerMejAtaMagArma().setValue(gw.getIncmagicalattack());
            gui.getSpinnerHRMagArma().setValue(gw.getMagicalhitrate());
            gui.getSpinnerMejHRMagArma().setValue(gw.getIncmagicalhitrate());
            gui.getSpinnerCritArma().setValue(gw.getCritical());
            gui.getSpinnerMejCritArma().setValue(gw.getInccritical());
            gui.getSpinnerRoboVArma().setValue(gw.getHealthsteal());
            gui.getSpinnerMejRoboVArma().setValue(gw.getInchealthsteal());
            gui.getSpinnerRoboMArma().setValue(gw.getManasteal());
            gui.getSpinnerMejRoboMArma().setValue(gw.getIncmanasteal());
            gui.getSpinnerExpExtraArma().setValue(gw.getXpbonus());
            gui.getSpinnerDineroExtraArma().setValue(gw.getMoneybonus());
            gui.recursivelyEnableDisablePanel(gui.getPanelEditorArma(), true);
        }
    }

    public String getUpgraderName() {
        return this.NomObjMej;
    }

    public Map<String, GUIWeapon> getListWeapons() {
        return listWeapons;
    }

}
