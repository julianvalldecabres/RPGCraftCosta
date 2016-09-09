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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.objects;

import com.craftcosta.jailrules.rpgcraftcosta.gui.GUI;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumItems;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumQuality;
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
public class GUIItemManager {

    private File file;
    private int lastindex;
    private FileConfiguration fileConfig;
    private GUI gui;
    private Map<String, GUIItem> listItems;
    private Map<String, Integer> listnum;
    private List<String> upgraders;

    public GUIItemManager(GUI gui) {
        this.gui = gui;
        listItems = new HashMap<>();
        upgraders = new ArrayList<>();
        upgraders.add(gui.getGuiArmorMan().getUpgraderName());
        upgraders.add(gui.getGuiWeaponMan().getUpgraderName());
        listnum = new HashMap<>();
        file = new File(RPGFinals.questItemFile);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(gui, "Fichero de items no encontrado se creara uno por defecto", "Error", 2);
            }
        }
        gui.getComboListaObjetos().addItem("<Selecciona un objeto para editar>");
        loadItems();

        gui.recursivelyEnableDisablePanel(gui.getPanelEditorObjetos(), false);
        for (Map.Entry<String, GUIItem> entrySet : listItems.entrySet()) {
            gui.getComboListaObjetos().addItem(entrySet.getKey());
        }
        System.out.println("Cargado config de objetos");
    }

    public Map<String, GUIItem> getListItems() {
        return listItems;
    }

    public List<String> getUpgraders() {
        return this.upgraders;
    }

    private void loadItems() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
        String name;
        EnumQuality quality;
        EnumItems material;
        List<String> lores = new ArrayList<>();
        Set<String> items = fileConfig.getKeys(false);
        for (String item : items) {
            if (this.lastindex <= Integer.parseInt(item)) {
                this.lastindex = Integer.parseInt(item);
            }
            ConfigurationSection section = fileConfig.getConfigurationSection(item);
            name = section.getString("name");
            quality = EnumQuality.valueOf(section.getString("quality"));
            material = EnumItems.valueOf(section.getString("material"));
            lores = section.getStringList("lores");
            this.listItems.put(name, new GUIItem(name, material, quality, lores));
            this.listnum.put(name, Integer.parseInt(item));
        }
    }

    public void saveItem() {
        GUIItem gi = getGUIItemByName(gui.getTxtNombreObjeto().getText());
        List<String> desc = new ArrayList<>();
        if (gi == null) {
            desc = new ArrayList<>();
            for (int i = 0; i < gui.getListDescripcionObjeto().getModel().getSize(); i++) {
                desc.add(gui.getListDescripcionObjeto().getModel().getElementAt(i).toString());
            }

            gi = new GUIItem(gui.getTxtNombreObjeto().getText(),
                    EnumItems.valueOf(EnumItems.values()[(int) gui.getComboTipoObjeto().getSelectedItem()].name()),
                    EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidadObjeto().getSelectedItem()].name()),
                    desc);
            this.listItems.put(gi.getName(), gi);
            this.listnum.put(gi.getName(), lastindex + 1);
            lastindex++;
            gui.getComboListaObjetos().addItem(gi.getName());
            gui.recursivelyEnableDisablePanel(gui.getPanelEditorObjetos(), false);
            saveItemToFile(gi);
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "Ya existe un objeto con ese nombre\n¿Quieres sobreescribirlo?", "Sobreescribir objeto: " + gi.getName(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                desc = new ArrayList<>();
                for (int i = 0; i < gui.getListDescripcionObjeto().getModel().getSize(); i++) {
                    desc.add(gui.getListDescripcionObjeto().getModel().getElementAt(i).toString());
                }
                gi.setMaterial(EnumItems.valueOf(EnumItems.values()[(int) gui.getComboTipoObjeto().getSelectedItem()].name()));
                gi.setQuality(EnumQuality.valueOf(EnumQuality.values()[(int) gui.getComboCalidadObjeto().getSelectedItem()].name()));
                gi.description.clear();
                gi.description.addAll(desc);

                this.listItems.put(gi.getName(), gi);
                gui.recursivelyEnableDisablePanel(gui.getPanelEditorObjetos(), false);
                saveItemToFile(gi);
            }
        }
    }

    private GUIItem getGUIItemByName(String name) {
        return this.listItems.get(name);
    }

    private void saveItemToFile(GUIItem gi) {
        fileConfig = YamlConfiguration.loadConfiguration(file);
        int index;
        GUIItem check = getGUIItemByName(gi.getName());
        if (gi == null) {
            this.lastindex++;
            index = lastindex;
        } else {
            index = this.listnum.get(gi.getName());
        }

        ConfigurationSection section = fileConfig.createSection("" + index);
        section.set("name", gi.getName());
        section.set("material", gi.getMaterial().getMaterial());
        section.set("quality", gi.getQuality().getName());
        section.set("lores", gi.getDescription());
        try {
            fileConfig.save(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ha petao", "Erroraco", 2);
        }
    }

    public void deleteItemSelected() {
        GUIItem gi = getGUIItemByName(gui.getComboListaObjetos().getSelectedItem().toString());
        if (gi == null) {
            gui.sendMessageWarning("Error", "El objeto seleccionado no existe");
        } else {
            if (monsterHasThisObject(gi)) {
                gui.sendMessageWarning("Error", "El objeto seleccionado esta asociado a un monstruo, reemplazalo antes de eliminarlo");
            } else {
                int diag = JOptionPane.showConfirmDialog(null, "¿Estas seguro de querer eliminar el objeto?", "Borrar objeto: " + gi.getName(), JOptionPane.YES_NO_OPTION);
                if (diag == JOptionPane.YES_OPTION) {
                    //borrar clase de el combo
                    gui.getComboListaObjetos().removeItem(gi.getName());
                    gui.getComboListaObjetos().setSelectedIndex(0);
                    //Borrar clase de el fichero y de la memoria
                    listItems.remove(gi.getName());
                    //listnum.remove(gi.getName());
                    deleteItemFromFile(gi);
                }
            }
        }
    }

    private void deleteItemFromFile(GUIItem gi) {
        try {
            fileConfig = YamlConfiguration.loadConfiguration(file);
            int index = listnum.get(gi.getName());
            listnum.remove(gi.getName());
            fileConfig.set("" + index, null);
            fileConfig.save(file);
        } catch (IOException ex) {
        }
    }

    public void loadValuesForItemSelected() {
        GUIItem gi = getGUIItemByName(gui.getComboListaObjetos().getSelectedItem().toString());
        if (gi == null) {
            gui.sendMessageWarning("Error", "El objeto seleccionado no existe");
        } else {
            gui.getTxtNombreObjeto().setText(gi.getName());
            gui.getComboTipoObjeto().setSelectedIndex(gi.getMaterial().ordinal());
            gui.getComboCalidadObjeto().setSelectedIndex(gi.getQuality().ordinal());
            List<String> desc = gi.getDescription();
            gui.getListDescripcionObjeto().setListData(desc.toArray());
            gui.recursivelyEnableDisablePanel(gui.getPanelEditorObjetos(), true);
        }
    }

    private boolean monsterHasThisObject(GUIItem gi) {
        return gui.getGuiMobMan().monsterHasThisDrop(gi.getName());
    }

}
