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
package com.craftcosta.jailrules.rpgcraftcosta.classes;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.leveling.RPGLevelManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class RPGClassManager {

    private final RPGCraftCosta plugin;
    private RPGPlayerManager rpgPMan;
    private RPGLevelManager rpgLMan;
    private static HashMap<String, RPGClass> listClasses;
    private File classesFile;
    private FileConfiguration cFConfig;

    /**
     *
     * @param plugin
     */
    public RPGClassManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.listClasses = new HashMap<>();
        this.rpgLMan=plugin.getRPGLevelManager();
        this.classesFile = new File(RPGFinals.classFilePath);
        plugin.getLogger().info("Loading classes module....");

        if (!classesFile.exists()) {
            plugin.getLogger().info("Loading default classes....");
            classesFile.getParentFile().mkdirs();
            copy(plugin.getResource("classes.yml"), classesFile);
        }
        loadClasses();
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

    /**
     *
     * @return
     */
    public static String getListAvailableClasses() {
        return listClasses.keySet().toString();
    }

    /**
     *
     * @param name
     * @return
     */
    public static RPGClass getRPGClass(String name) {
        return listClasses.get(name);
    }

    private void loadClasses() {
        if (!classesFile.exists()) {
            cFConfig = YamlConfiguration.loadConfiguration(new File(RPGFinals.classFilePath));
        } else {
            cFConfig = YamlConfiguration.loadConfiguration(classesFile);
        }
        plugin.getLogger().info("Loading classes...");
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
        double SPUpStrength_Health;
        double SPUpStrength_PhysicalAttack;
        double SPUpDextery_Critical;
        double SPUpConstitution_Health;
        double SPUpConstitution_Mana;
        double SPUpIntelligence_Mana;
        double SPUpIntelligence_MagicAttack;

        Set<String> classes = cFConfig.getKeys(false);
        for (String clas : classes) {

            ConfigurationSection section = cFConfig.getConfigurationSection(clas);
            enabled = section.getBoolean("enabled");
            if (enabled) {
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
                SPUpStrength_Health = section.getDouble("SPUpStrength_Health");
                SPUpStrength_PhysicalAttack = section.getDouble("SPUpStrength_PhysicalAttack");
                SPUpDextery_Critical = section.getDouble("SPUpDextery_Critical");
                SPUpConstitution_Health = section.getDouble("SPUpConstitution_Health");
                SPUpConstitution_Mana = section.getDouble("SPUpConstitution_Mana");
                SPUpIntelligence_MagicAttack = section.getDouble("SPUpIntelligence_MagicAttack");
                SPUpIntelligence_Mana = section.getDouble("SPUpIntelligence_Mana");
                this.listClasses.put(name, new RPGClass(name, enabled, baseHealth, baseMana, basePhysicalAttack, basePhysicalDefense, basePhysicalHitRate, basePhysicalEvasion, baseMagicAttack, baseMagicDefense, baseMagicHitRate, baseMagicEvasion, baseCritical, lvlUpHealth, lvlUpMana, lvlUpPhysicalAttack, lvlUpPhysicalDefense, lvlUpPhysicalHitRate, lvlUpPhysicalEvasion, lvlUpMagicAttack, lvlUpMagicDefense, lvlUpMagicHitRate, lvlUpMagicEvasion, lvlUpCritical, SPUpStrength_Health, SPUpStrength_PhysicalAttack, SPUpDextery_Critical, SPUpConstitution_Health, SPUpConstitution_Mana, SPUpIntelligence_MagicAttack, SPUpIntelligence_Mana));
            }
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public RPGClass getRPGClassByName(String name) {
        return this.listClasses.get(name);
    }

    /**
     *
     * @return
     */
    public Set<String> getAllClassesNames() {
        return this.listClasses.keySet();
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean isAValidRPGClassName(String name) {
        return this.listClasses.containsKey(name);
    }

    public void setRPGPlayerRPGClass(RPGPlayer rpgp, RPGClass rpgclass) {
        rpgp.setPlayerClass(rpgclass.getNameClass());

        rpgp.setActualHealth(rpgclass.getBaseHealth());
        rpgp.setMaxHealth(rpgclass.getBaseHealth());
        rpgp.setFinalMaxHealth(rpgclass.getBaseHealth());

        rpgp.setActualMana(rpgclass.getBaseMana());
        rpgp.setMaxMana(rpgclass.getBaseMana());

        rpgp.setPhysicalAttack(rpgclass.getBasePhysicalAttack());
        rpgp.setFinalphysicalAttack(rpgclass.getBasePhysicalAttack());

        rpgp.setPhysicalDefense(rpgclass.getBasePhysicalDefense());
        rpgp.setFinalphysicalDefense(rpgclass.getBasePhysicalDefense());

        rpgp.setPhysicalHitRate(rpgclass.getBasePhysicalHitRate());
        rpgp.setFinalphysicalHitRate(rpgclass.getBasePhysicalHitRate());

        rpgp.setPhysicalEvasion(rpgclass.getBasePhysicalEvasion());
        rpgp.setFinalphysicalEvasion(rpgclass.getBasePhysicalEvasion());

        rpgp.setMagicalAttack(rpgclass.getBaseMagicalAttack());
        rpgp.setFinalmagicalAttack(rpgclass.getBaseMagicalAttack());

        rpgp.setMagicalDefense(rpgclass.getBaseMagicalDefense());
        rpgp.setFinalmagicalDefense(rpgclass.getBaseMagicalDefense());

        rpgp.setMagicalHitRate(rpgclass.getBaseMagicalHitRate());
        rpgp.setFinalmagicalHitRate(rpgclass.getBaseMagicalHitRate());

        rpgp.setMagicalEvasion(rpgclass.getBaseMagicalEvasion());
        rpgp.setFinalmagicalEvasion(rpgclass.getBaseMagicalEvasion());

        rpgp.setCritical(rpgclass.getBaseCritical());
        rpgp.setFinalcritical(rpgclass.getBaseCritical());

        rpgPMan.checkAllEquipment(rpgp);
    }

    /**
     *
     */
    public void levelUP(RPGPlayer rpgp) {
        //aumentar stats
        RPGClass rpgclass = RPGClassManager.getRPGClass(rpgp.getPlayerClass());
        rpgp.setActualLevel(rpgp.getActualLevel() + 1);
        recalculatePlayerStats(rpgclass, rpgp, rpgp.getActualLevel() + 1);
        rpgp.getPlayer().sendMessage(ChatColor.GREEN + "Enhorabuena has alcanzado el nivel " + rpgp.getActualLevel());
        rpgp.setAp(rpgp.getAp()+rpgLMan.getPh());
        rpgPMan.checkAllEquipment(rpgp);
    }

    public void setRPGPlayerManager(RPGPlayerManager rpgPlayerManager) {
        this.rpgPMan = rpgPlayerManager;
    }

    private void recalculatePlayerStats(RPGClass rpgclass, RPGPlayer rpgp, int i) {
        rpgp.setActualHealth(rpgclass.getLvlUpHealth()*i + rpgclass.getBaseHealth());
        rpgp.setFinalMaxHealth(rpgclass.getLvlUpHealth()*i + rpgclass.getBaseHealth());
        rpgp.setMaxHealth(rpgclass.getLvlUpHealth()*i + rpgclass.getBaseHealth());
        rpgp.getPlayer().setHealth(20);
        
        
        rpgp.setPhysicalAttack(rpgclass.getLvlUpPhysicalAttack()*i + rpgclass.getBasePhysicalAttack());
        rpgp.setFinalphysicalAttack(rpgclass.getLvlUpPhysicalAttack()*i + rpgclass.getBasePhysicalAttack());
        
        rpgp.setPhysicalDefense(rpgclass.getLvlUpPhysicalAttack()*i + rpgclass.getBasePhysicalDefense());
        rpgp.setFinalphysicalDefense(rpgclass.getLvlUpPhysicalAttack()*i + rpgclass.getBasePhysicalDefense());
        
        rpgp.setPhysicalHitRate(rpgclass.getLvlUpPhysicalHitRate()*i + rpgclass.getBasePhysicalHitRate());
        rpgp.setFinalphysicalHitRate(rpgclass.getLvlUpPhysicalHitRate()*i + rpgclass.getBasePhysicalHitRate());
        
        rpgp.setPhysicalEvasion(rpgclass.getLvlUpPhysicalEvasion()*i + rpgclass.getBasePhysicalEvasion());
        rpgp.setFinalphysicalEvasion(rpgclass.getLvlUpPhysicalEvasion()*i + rpgclass.getBasePhysicalEvasion());
        
        rpgp.setMagicalAttack(rpgclass.getLvlUpMagicalAttack()*i + rpgclass.getBaseMagicalAttack());
        rpgp.setFinalmagicalAttack(rpgclass.getLvlUpMagicalAttack()*i + rpgclass.getBaseMagicalAttack());
        
        rpgp.setMagicalDefense(rpgclass.getLvlUpMagicalAttack()*i + rpgclass.getBaseMagicalDefense());
        rpgp.setFinalmagicalDefense(rpgclass.getLvlUpMagicalAttack()*i + rpgclass.getBaseMagicalDefense());
        
        rpgp.setMagicalHitRate(rpgclass.getLvlUpMagicalHitRate()*i + rpgclass.getBaseMagicalHitRate());
        rpgp.setFinalmagicalHitRate(rpgclass.getLvlUpMagicalHitRate()*i + rpgclass.getBaseMagicalHitRate());
        
        rpgp.setMagicalEvasion(rpgclass.getLvlUpMagicalEvasion()*i + rpgclass.getBaseMagicalEvasion());
        rpgp.setFinalmagicalEvasion(rpgclass.getLvlUpMagicalEvasion()*i + rpgclass.getBaseMagicalEvasion());
        
        rpgp.setCritical(rpgclass.getLvlUpCritical()*i + rpgclass.getBaseCritical());
        rpgp.setFinalcritical(rpgclass.getLvlUpCritical()*i + rpgclass.getBaseCritical());
    }
}
