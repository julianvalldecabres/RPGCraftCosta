/*
 * Copyright (C) 2015 jail
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

    public List<RPGLore> getListOfLoresFromItem(ItemStack item) {
        List<RPGLore> listlores = new ArrayList<>();
        List<String> lores = item.getItemMeta().getLore();
        for (String lore : lores) {
            Object value = getLoreValue(getRPGLoresFromString(lore), lore);
            listlores.add(new RPGLore(getRPGLoresFromString(lore), value));
        }
        return listlores;
    }

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
        return null;
    }

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
                return Double.parseDouble(loreparts[loreparts.length - 1].substring(1, loreparts[loreparts.length - 1].length() - 1));
            case BUYPRICE:
            case SELLPRICE:
                return null;
            case NOCOMERCIABLE:
            case NOUPGRADABLE:
            case NOCOMBINABLE:
        }
        return null;
    }

}
