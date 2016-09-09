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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.spawners;

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
public class GUISPawnerManager {

    private GUI gui;
    private File file;
    private int lastindex = 0;
    private FileConfiguration filec;
    private Map<String, GUISpawner> listspawners;
    private Map<String, Integer> listnum;

    public GUISPawnerManager(GUI gui) {
        this.gui = gui;
        file = new File(RPGFinals.spawnersFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GUISPawnerManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        listspawners = new HashMap<>();
        listnum = new HashMap<>();
        loadSpawners();
        gui.getComboSelectorGenerador().addItem("<Selecciona un spawner para editar>");
        gui.recursivelyEnableDisablePanel(gui.getPanelEditorSpawner(), false);
        for (Map.Entry<String, GUISpawner> entrySet : listspawners.entrySet()) {
            gui.getComboSelectorGenerador().addItem(entrySet.getKey());
        }
        System.out.println("Cargado config de spawners");
    }

    private void loadSpawners() {
        int count = 0;
        filec = YamlConfiguration.loadConfiguration(file);
        if (filec.getKeys(false) != null) {
            Set<String> worlds = filec.getKeys(false);
            for (String world : worlds) {
                ConfigurationSection section = filec.getConfigurationSection(world);
                Set<String> chunks = section.getKeys(false);
                for (String chunk : chunks) {
                    ConfigurationSection s2 = section.getConfigurationSection(chunk);
                    Set<String> spawnerids = s2.getKeys(false);
                    for (String id : spawnerids) {
                        count++;
                        if (this.lastindex <= count) {
                            this.lastindex = count;
                        }
                        ConfigurationSection s3 = s2.getConfigurationSection(id);
                        String rpgmob;
                        boolean enabled;
                        int coordx;
                        int coordy;
                        int coordz;
                        int maxmobs;
                        int radius;//blocks
                        int cooldown;//seconds
                        String identifier;//name for spawner
                        identifier = s3.getString("name");
                        rpgmob = s3.getString("rpgmob");
                        enabled = s3.getBoolean("enabled");
                        coordx = s3.getInt("x");
                        coordy = s3.getInt("y");
                        coordz = s3.getInt("z");
                        maxmobs = s3.getInt("maxmobs");
                        radius = s3.getInt("radius");
                        cooldown = s3.getInt("cooldown");
                        listspawners.put(identifier, new GUISpawner(identifier, world, coordx, coordy, coordz, rpgmob, maxmobs, cooldown, enabled, radius));
                        listnum.put(identifier, count);
                    }
                }
            }
        }
    }

    public void saveSpawner() {
        GUISpawner check = getGUISpawnerByName(gui.getTxtNombreSpawner().getText());
        String name = gui.getTxtNombreSpawner().getText();
        String world = gui.getTxtMundoSpawnLoc().getText();
        int x = (int) gui.getSpinnerXSpawnLoc().getValue();
        int y = (int) gui.getSpinnerYSpawnLoc().getValue();
        int z = (int) gui.getSpinnerZSpawnLoc().getValue();

        String mobname = gui.getComboMobSpawner().getModel().getSelectedItem().toString();
        int maxmobs = (int) gui.getSpinnerNumMaxMobSpawner().getValue();
        int cooldown = (int) gui.getSpinnerRefrescoSpawner().getValue();
        int radius = (int) gui.getSpinnerRadioSpawner().getValue();
        boolean enabled = gui.getCheckEnableSpawner().isSelected();
        if (check == null) {
            //si no existe
            check = new GUISpawner(name, world, x, y, z, mobname, maxmobs, cooldown, enabled, radius);
            listspawners.put(name, check);
            listnum.put(name, lastindex + 1);
            lastindex++;
            gui.getComboSelectorGenerador().addItem(check.getName());

            gui.recursivelyEnableDisablePanel(gui.getPanelEditorSpawner(), false);
            saveSpawnerToFile(check);

        } else {
            //si existe
            int diag = JOptionPane.showConfirmDialog(null, "Ya existe un arma con ese nombre\n¿Quieres sobreescribirlo?", "Sobreescribir arma: " + check.getName(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                findAndDelSpawnerFromFile(check.getName());
                gui.getComboSelectorGenerador().removeItem(check.getName());
                check.setCooldown(cooldown);
                check.setEnabled(enabled);
                check.setX(x);
                check.setY(y);
                check.setZ(z);
                check.setMobname(mobname);
                check.setWorld(world);
                check.setMaxmobs(maxmobs);
                listspawners.put(name, check);
                listnum.put(name, lastindex++);
                gui.getComboSelectorGenerador().addItem(check.getName());

                gui.recursivelyEnableDisablePanel(gui.getPanelEditorSpawner(), false);
                saveSpawnerToFile(check);
            }
        }

    }

    private GUISpawner getGUISpawnerByName(String text) {
        return this.listspawners.get(text);
    }

    private String getChunkString(int x, int z) {
        int chunkx = x >> 4;
        int chunkz = z >> 4;
        return chunkx + "x" + chunkz;
    }

    private void saveSpawnerToFile(GUISpawner gs) {
        filec = YamlConfiguration.loadConfiguration(file);
        int index;
        GUISpawner check = getGUISpawnerByName(gs.getName());
        if (check == null) {
            this.lastindex++;
            index = lastindex;
        } else {
            index = this.listnum.get(gs.getName());

        }
        String chunk = getChunkString(check.getX(), check.getZ());
        ConfigurationSection section = filec.createSection(gs.getWorld() + "." + chunk + "." + index);
        section.set("name", gs.getName());
        section.set("rpgmob", gs.getMobname());
        section.set("enabled", gs.isEnabled());
        section.set("x", gs.getX());
        section.set("y", gs.getY());
        section.set("z", gs.getZ());
        section.set("maxmobs", gs.getMaxmobs());
        section.set("cooldown", gs.getCooldown());
        section.set("radius", gs.getRadius());
        try {
            filec.save(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el spawner", "Error", 2);
        }

    }

    private void findAndDelSpawnerFromFile(String name) {
        filec = YamlConfiguration.loadConfiguration(file);
        Set<String> worlds = filec.getKeys(false);
        for (String world : worlds) {
            ConfigurationSection section = filec.getConfigurationSection(world);
            Set<String> chunks = section.getKeys(false);
            for (String chunk : chunks) {
                ConfigurationSection s2 = section.getConfigurationSection(chunk);
                Set<String> ids = s2.getKeys(false);
                for (String id : ids) {
                    ConfigurationSection s3 = s2.getConfigurationSection(id);
                    if (s3.getString("name").equals(name)) {
                        delSpawnerFromfileByPath(world + "." + chunk + "." + id);
                    }
                }
                if (!nodehasAnotherChild(world + "." + chunk)) {
                    delNodeFromFileByPath(world+"."+chunk);
                }
            }
            if(!nodehasAnotherChild(world)){
                delNodeFromFileByPath(world);
            }

        }
    }

    private void delSpawnerFromfileByPath(String path) {
        filec = YamlConfiguration.loadConfiguration(file);
        filec.set(path, null);
        try {
            filec.save(file);
        } catch (IOException ex) {
            Logger.getLogger(GUISPawnerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteSpawnSelected() {
        GUISpawner gs = getGUISpawnerByName(gui.getComboSelectorGenerador().getSelectedItem().toString());
        if (gs == null) {
            gui.sendMessageWarning("Error", "El spawner seleccionado no existe");
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "¿Estas seguro de querer eliminar el spawner?", "Borrar arma: " + gs.getName(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                //borrar clase de el combo
                gui.getComboSelectorGenerador().removeItem(gs.getName());
                gui.getComboSelectorGenerador().setSelectedIndex(0);
                //Borrar clase de el fichero y de la memoria
                listspawners.remove(gs.getName());
                //listnum.remove(gw.getName());
                findAndDelSpawnerFromFile(gs.getName());
            }
        }
    }

    public void loadValuesForSpawnSelected() {
        GUISpawner gs = getGUISpawnerByName(gui.getComboSelectorGenerador().getSelectedItem().toString());
        if (gs == null) {
            gui.sendMessageWarning("Error", "El spawner seleccionado no existe");
        } else {
            gui.getTxtNombreSpawner().setText(gs.getName());
            //ERROR SELECCION DE MOB

            gui.getComboMobSpawner().setSelectedItem(gs.getMobname());
            gui.getSpinnerRefrescoSpawner().setValue(gs.getCooldown());
            gui.getSpinnerRadioSpawner().setValue(gs.getRadius());
            gui.getSpinnerNumMaxMobSpawner().setValue(gs.getMaxmobs());
            gui.getTxtMundoSpawnLoc().setText(gs.getWorld());
            gui.getSpinnerXSpawnLoc().setValue(gs.getX());
            gui.getSpinnerYSpawnLoc().setValue(gs.getY());
            gui.getSpinnerZSpawnLoc().setValue(gs.getZ());
            gui.getCheckEnableSpawner().setSelected(gs.isEnabled());
            gui.recursivelyEnableDisablePanel(gui.getPanelEditorSpawner(), true);
        }
    }

    private boolean nodehasAnotherChild(String string) {
        filec = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection s= filec.getConfigurationSection(string);
        Set<String> ids=s.getKeys(false);
        if(ids.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    private void delNodeFromFileByPath(String string) {
        filec = YamlConfiguration.loadConfiguration(file);
        filec.set(string, null);
        try {
            filec.save(file);
        } catch (IOException ex) {
            Logger.getLogger(GUISPawnerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean spawnerhasmob(String rpgmob) {
        for (Map.Entry<String, GUISpawner> entrySet : listspawners.entrySet()) {
                if(entrySet.getValue().getMobname().equals(rpgmob)){
                    return true;
                }
        }
        return false;
    }
}
