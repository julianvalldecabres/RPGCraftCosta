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
package com.craftcosta.jailrules.rpgcraftcosta.items.lores;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGLoreManager {

    private RPGCraftCosta plugin;
    private File lores;
    private FileConfiguration lConfig;

    /**
     *
     * @param plugin
     */
    public RPGLoreManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Loading lores module....");
        this.lores = new File(RPGFinals.loresFilePath);
        if (!lores.exists()) {
            plugin.getLogger().info("Loading default lores...");
            lores.getParentFile().mkdirs();
            copy(plugin.getResource("lores.yml"), lores);
        }
        loadLores();
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLores() {
        if (!lores.exists()) {
            lConfig = YamlConfiguration.loadConfiguration(new File(RPGFinals.loresFilePath));
        } else {
            lConfig = YamlConfiguration.loadConfiguration(lores);
        }
        plugin.getLogger().info("Loading lores");
        Set<String> listlores = lConfig.getKeys(false);
        for (String lore : listlores) {
            ConfigurationSection section = lConfig.getConfigurationSection(lore);
            RPGLores.valueOf(lore).setLoreName(section.getString("name"));
            RPGLores.valueOf(lore).setValue(RPGLoreValueType.valueOf(section.getString("type")));
        }
    }

    /**
     *
     * @param item
     * @return
     */
    public List<RPGLore> getListOfLoresFromItem(ItemStack item) {
        List<RPGLore> listlores = new ArrayList<>();
        if (item.getItemMeta().hasLore()) {
            List<String> lores = item.getItemMeta().getLore();
            for (String lore : lores) {
                Object value = getLoreValue(getRPGLoresFromString(lore), lore);
                listlores.add(new RPGLore(getRPGLoresFromString(lore), value));
            }
        }
        return listlores;
    }

    /**
     *
     * @param lore
     * @return
     */
    public RPGLores getRPGLoresFromString(String lore) {
        String[] loreparts = lore.split(" ");
        String lorename = "";
        for (RPGLores rpglores : RPGLores.values()) {
            if (rpglores.getLoreName().equals(lore)) {
                return rpglores;
            }
        }
        for (int i = 0; i < loreparts.length - 2; i++) {
            lorename += loreparts[i] + " ";
        }
        lorename += loreparts[loreparts.length - 2];
        for (RPGLores rpglores : RPGLores.values()) {
            if (rpglores.getLoreName().equals(lorename)) {
                return rpglores;
            }
        }
        return RPGLores.APBONUS;
    }

    /**
     *
     * @param type
     * @param lore
     * @return
     */
    public Object getLoreValue(RPGLores type, String lore) {
        String[] loreparts = lore.split(" ");
        switch (type) {
            case MAGICALATTACK:
            case MAGICALDEFENSE:
            case PHYSICALATTACK:
            case PHYSICALDEFENSE:
                return Integer.parseInt(loreparts[loreparts.length - 1].substring(1));
            case MAGICALEVASION:
            case MAGICALHITRATE:
            case PHYSICALEVASION:
            case PHYSICALHITRATE:
            case HEALTH:
            case MANA:
            case HEALTHSTEAL:
            case MANASTEAL:
            case APBONUS:
            case XPBONUS:
            case MONEYBONUS:
            case CRITICAL:
                return Double.parseDouble((loreparts[loreparts.length - 1].substring(1, loreparts[loreparts.length - 1].length() - 1)).replaceAll(",", "."));
            case BUYPRICE:
            case SELLPRICE:
                return Integer.parseInt(loreparts[loreparts.length - 1].substring(0, loreparts[loreparts.length - 1].length() - 1));
            case NOCOMERCIABLE:
            case NOUPGRADABLE:
            case NOCOMBINABLE:
        }
        return null;
    }

}
