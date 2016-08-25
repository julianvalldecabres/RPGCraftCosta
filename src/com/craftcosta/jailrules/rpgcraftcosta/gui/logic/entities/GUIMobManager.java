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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.entities;

import com.craftcosta.jailrules.rpgcraftcosta.entities.CustomEntityType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.RPGMob;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGBat;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGBlaze;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGCaveSpider;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGChicken;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGCow;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGCreeper;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGEnderDragon;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGEnderman;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGEndermite;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGGhast;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGGiant;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGGuardian;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGHorse;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGIronGolem;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGMagmaCube;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGMushroomCow;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGOcelot;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGPig;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGPigZombie;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGRabbit;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSheep;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSilverfish;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSkeleton;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSlime;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSnowGolem;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSpider;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSquid;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGVillager;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGWitch;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGWither;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGWolf;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGZombie;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.AttackType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.GuardianType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.HorseType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.HorseVariant;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.OcelotType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.RabbitType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.SheepColor;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.SkeletonType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.VillagerType;
import com.craftcosta.jailrules.rpgcraftcosta.gui.GUI;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumWeapon;
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
public class GUIMobManager {

    private GUI gui;
    private int lastindex = -1;
    private Map<String, RPGMob> listmobs;
    private Map<String, Integer> listnum;
    private File filem;
    private FileConfiguration filemConfig;

    public GUIMobManager(GUI gui) {
        this.gui = gui;
        filem = new File(RPGFinals.mobsFile);
        if (!filem.exists()) {
            try {
                filem.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GUIMobManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        listmobs = new HashMap<>();
        listnum = new HashMap<>();
        loadMobs();
        gui.getComboSelectorMobs().addItem("<Selecciona un monstruo para editar>");
        gui.recursivelyEnableDisablePanel(gui.getPanelEditorMobs(), false);
        for (Map.Entry<String, RPGMob> entrySet : listmobs.entrySet()) {
            gui.getComboSelectorMobs().addItem(entrySet.getKey());
        }

    }

    private void loadMobs() {
        filemConfig = YamlConfiguration.loadConfiguration(filem);
        String name;
        CustomEntityType type;
        int level;
        AttackType aType;
        MobBehaviour bType;
        double dammageattack;
        double movementspeed;
        double knockback;
        double followrange;
        double maxhealth;
        double attackspeed;
        double rangeddamage;
        double rangedstrength;
        long money;
        long exp;
        Set<String> mobs = filemConfig.getKeys(false);
        for (String id : mobs) {
            if (this.lastindex <= Integer.parseInt(id)) {
                this.lastindex = Integer.parseInt(id);
            }
            ConfigurationSection section = filemConfig.getConfigurationSection(id);
            name = section.getString("name");
            level = section.getInt("level");
            type = CustomEntityType.valueOf(section.getString("entitytype"));
            aType = AttackType.valueOf(section.getString("attacktype"));
            bType = MobBehaviour.valueOf(section.getString("behaviour"));
            dammageattack = section.getDouble("damageattack");
            movementspeed = section.getDouble("movementspeed");
            knockback = section.getDouble("knockback");
            followrange = section.getDouble("followrange");
            maxhealth = section.getDouble("maxhealth");
            attackspeed = section.getDouble("attackspeed");
            rangeddamage = section.getDouble("rangeddamage");
            rangedstrength = section.getDouble("rangedstrength");
            money = section.getLong("money");
            exp = section.getLong("exp");
            RPGMob rpgmob = null;
            boolean baby = false;
            boolean villager = false;
            boolean asleep = false;
            GuardianType gtype = null;
            HorseType htype = null;
            HorseVariant hvariant = null;
            OcelotType otype = null;
            RabbitType rtype = null;
            SheepColor sColor = null;
            SkeletonType stype = null;
            VillagerType vType = null;
            switch (type) {
                case BATX:
                    asleep = section.getBoolean("asleep");
                    rpgmob = new RPGBat(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, asleep);
                    break;
                case BLAZEX:
                    rpgmob = new RPGBlaze(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case CAVESPIDERX:
                    rpgmob = new RPGCaveSpider(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case CHICKENX:
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGChicken(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby);
                    break;
                case COWX:
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGCow(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby);
                    break;
                case CREEPERX:
                    rpgmob = new RPGCreeper(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case ENDERDRAGONX:
                    rpgmob = new RPGEnderDragon(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case ENDERMANX:
                    rpgmob = new RPGEnderman(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case ENDERMITEX:
                    rpgmob = new RPGEndermite(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case GHASTX:
                    rpgmob = new RPGGhast(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case GIANTX:
                    rpgmob = new RPGGiant(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case GUARDIANX:
                    gtype = GuardianType.valueOf(section.getString("guardiantype"));
                    rpgmob = new RPGGuardian(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, gtype);
                    break;
                case HORSEX:
                    hvariant = HorseVariant.valueOf(section.getString("horsevariant"));
                    htype = HorseType.valueOf(section.getString("horsetype"));
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGHorse(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, htype, hvariant);
                    break;
                case IRONGOLEMX:
                    rpgmob = new RPGIronGolem(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case MAGMACUBEX:
                    rpgmob = new RPGMagmaCube(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case MUSHROOMCOWX:
                    rpgmob = new RPGMushroomCow(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby);
                    break;
                case OCELOTX:
                    baby = section.getBoolean("baby");
                    otype = OcelotType.valueOf(section.getString("otype"));
                    rpgmob = new RPGOcelot(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, otype);
                    break;
                case PIGX:
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGPig(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby);
                    break;
                case PIGZOMBIEX:
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGPigZombie(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby);
                    break;
                case RABBITX:
                    baby = section.getBoolean("baby");
                    rtype = RabbitType.valueOf(section.getString("rtype"));
                    rpgmob = new RPGRabbit(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, rtype, baby);
                    break;
                case SHEEPX:
                    baby = section.getBoolean("baby");
                    sColor = SheepColor.valueOf(section.getString("scolor"));
                    rpgmob = new RPGSheep(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, sColor, baby);
                    break;
                case SILVERFISHX:
                    rpgmob = new RPGSilverfish(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case SKELETONX:
                    stype = SkeletonType.valueOf(section.getString("stype"));
                    rpgmob = new RPGSkeleton(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, stype);
                    break;
                case SLIMEX:
                    rpgmob = new RPGSlime(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case SNOWGOLEMX:
                    rpgmob = new RPGSnowGolem(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case SPIDERX:
                    rpgmob = new RPGSpider(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case SQUIDX:
                    rpgmob = new RPGSquid(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case VILLAGERX:
                    baby = section.getBoolean("baby");
                    vType = VillagerType.valueOf(section.getString("vtype"));
                    rpgmob = new RPGVillager(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, vType, baby);
                    break;
                case WITCHX:
                    rpgmob = new RPGWitch(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case WITHERX:
                    rpgmob = new RPGWither(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                    break;
                case ZOMBIEX:
                    baby = section.getBoolean("baby");
                    villager = section.getBoolean("villager");
                    rpgmob = new RPGZombie(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, villager);
                    break;
            }
            listmobs.put(name, rpgmob);
            listnum.put(name, Integer.parseInt(id));
        }
    }

    public void saveMob() {
        RPGMob check = getGUIMobByName(gui.getTxtNombreMob().getText());
        CustomEntityType type;
        String name;
        int level;
        AttackType atype;
        MobBehaviour btype;
        double dammageattack;
        double movementspeed;
        double knockback;
        double followrange;
        double maxhealth;
        double attackspeed;
        double rangeddamage;
        double rangedstrength;
        int money;
        int exp;
        boolean baby = false;
        boolean villager = false;
        boolean asleep = false;
        GuardianType gtype = null;
        HorseType htype = null;
        HorseVariant hvariant = null;
        OcelotType otype = null;
        RabbitType rtype = null;
        SheepColor sColor = null;
        SkeletonType stype = null;
        VillagerType vType = null;
        name = gui.getTxtNombreMob().getText();
        type = CustomEntityType.valueOf(CustomEntityType.values()[(int) gui.getComboSelectorTipoMob().getSelectedIndex()].name());
        atype = AttackType.valueOf(AttackType.values()[(int) gui.getComboTipoAtaqueMob().getSelectedItem()].name());
        btype = MobBehaviour.valueOf(MobBehaviour.values()[(int) gui.getComboComportamiento().getSelectedItem()].name());
        level = (int) gui.getSpinnerNivelMob().getValue();
        dammageattack = (double) gui.getSpinnerAtaFisMob().getValue();
        movementspeed = (double) gui.getSpinnerVelocidadMob().getValue();
        knockback = (double) gui.getSpinnerRetrocesoMob().getValue();
        followrange = (double) gui.getSpinnerRangoSeguiMob().getValue();
        maxhealth = (double) gui.getSpinnerMaxVidaMob().getValue();
        attackspeed = (double) gui.getSpinnerVelocidadAtaqueMob().getValue();
        rangeddamage = (double) gui.getSpinnerAtaDistMob().getValue();
        rangedstrength = (double) gui.getSpinnerFuerzaDistMob().getValue();

        money = (int) gui.getSpinnerDineroDropMob().getValue();
        exp = (int) gui.getSpinnerExpDropMob().getValue();
        RPGMob mob = null;
        switch (type) {
            case BATX:
                asleep = gui.getCheckAtribMob1().isSelected();
                mob = new RPGBat(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, asleep);
                break;
            case BLAZEX:
                mob = new RPGBlaze(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case CAVESPIDERX:
                mob = new RPGCaveSpider(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case CHICKENX:
                baby = gui.getCheckAtribMob1().isSelected();
                mob = new RPGChicken(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby);
                break;
            case COWX:
                baby = gui.getCheckAtribMob1().isSelected();
                mob = new RPGCow(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby);
                break;
            case CREEPERX:
                mob = new RPGCreeper(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case ENDERDRAGONX:
                mob = new RPGEnderDragon(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case ENDERMANX:
                mob = new RPGEnderman(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case ENDERMITEX:
                mob = new RPGEndermite(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case GHASTX:
                mob = new RPGGhast(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case GIANTX:
                mob = new RPGGiant(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case GUARDIANX:
                gtype = GuardianType.valueOf(GuardianType.values()[(int) gui.getComboAtribMob1().getSelectedIndex()].name());
                mob = new RPGGuardian(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, gtype);
                break;
            case HORSEX:
                baby = gui.getCheckAtribMob1().isSelected();
                htype = HorseType.valueOf(HorseType.values()[(int) gui.getComboAtribMob1().getSelectedIndex()].name());
                hvariant = HorseVariant.valueOf(HorseVariant.values()[(int) gui.getComboAtribMob2().getSelectedIndex()].name());
                mob = new RPGHorse(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, htype, hvariant);
                break;
            case IRONGOLEMX:
                mob = new RPGIronGolem(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case MAGMACUBEX:
                mob = new RPGMagmaCube(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case MUSHROOMCOWX:
                baby = gui.getCheckAtribMob1().isSelected();
                mob = new RPGMushroomCow(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby);
                break;
            case OCELOTX:
                baby = gui.getCheckAtribMob1().isSelected();
                otype = OcelotType.valueOf(OcelotType.values()[(int) gui.getComboAtribMob1().getSelectedIndex()].name());
                mob = new RPGOcelot(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, otype);
                break;
            case PIGX:
                baby = gui.getCheckAtribMob1().isSelected();
                mob = new RPGPig(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby);
                break;
            case PIGZOMBIEX:
                baby = gui.getCheckAtribMob1().isSelected();
                mob = new RPGPigZombie(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby);
                break;
            case RABBITX:
                baby = gui.getCheckAtribMob1().isSelected();
                rtype = RabbitType.valueOf(RabbitType.values()[(int) gui.getComboAtribMob1().getSelectedIndex()].name());
                mob = new RPGRabbit(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, rtype, baby);
                break;
            case SHEEPX:
                baby = gui.getCheckAtribMob1().isSelected();
                sColor = SheepColor.valueOf(SheepColor.values()[(int) gui.getComboAtribMob1().getSelectedIndex()].name());
                mob = new RPGSheep(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, sColor, baby);
                break;
            case SILVERFISHX:
                mob = new RPGSilverfish(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case SKELETONX:
                stype = SkeletonType.valueOf(SkeletonType.values()[(int) gui.getComboAtribMob1().getSelectedIndex()].name());
                mob = new RPGSkeleton(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, stype);
                break;
            case SLIMEX:
                mob = new RPGSlime(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case SNOWGOLEMX:
                mob = new RPGSnowGolem(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case SPIDERX:
                mob = new RPGSpider(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case SQUIDX:
                mob = new RPGSquid(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case VILLAGERX:
                baby = gui.getCheckAtribMob1().isSelected();
                vType = VillagerType.valueOf(VillagerType.values()[(int) gui.getComboAtribMob1().getSelectedIndex()].name());
                mob = new RPGVillager(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, vType, baby);
                break;
            case WITCHX:
                mob = new RPGWitch(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case WITHERX:
                mob = new RPGWither(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp);
                break;
            case WOLFX:
                baby = gui.getCheckAtribMob1().isSelected();
                mob = new RPGWolf(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby);
                break;
            case ZOMBIEX:
                baby = gui.getCheckAtribMob1().isSelected();
                villager = gui.getCheckAtribMob2().isSelected();
                mob = new RPGZombie(level, name, type, atype, btype, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, villager);
                break;
        }
        if (check == null) {
            this.listmobs.put(mob.getName(), mob);
            this.listnum.put(mob.getName(), lastindex + 1);
            gui.getComboSelectorMobs().addItem(mob.getName());
            gui.recursivelyEnableDisablePanel(gui.getPanelEditorMobs(), false);
            saveMobToFile(mob);
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "Ya existe un monstruo con ese nombre\n¿Quieres sobreescribirlo?", "Sobreescribir monstruo: " + mob.getName(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                this.listmobs.put(mob.getName(), mob);
                gui.recursivelyEnableDisablePanel(gui.getPanelEditorMobs(), false);
                saveMobToFile(mob);
            }
        }
    }

    private RPGMob getGUIMobByName(String text) {
        return this.listmobs.get(text);
    }

    private void saveMobToFile(RPGMob mob) {
        filemConfig = YamlConfiguration.loadConfiguration(filem);
        int index;
        RPGMob check = getGUIMobByName(mob.getName());
        if (check == null) {
            this.lastindex++;
            index = lastindex;
        } else {
            index = this.listnum.get(mob.getName());
        }
        ConfigurationSection section = filemConfig.createSection("" + index);
        section.set("entitytype", mob.getType().name());
        section.set("name", mob.getName());
        section.set("level", mob.getLevel());
        section.set("attacktype", mob.getaType().name());
        section.set("behaviour", mob.getmType().name());
        section.set("damageattack", mob.getDamageattack());
        section.set("movementspeed", mob.getMovementspeed());
        section.set("knockback", mob.getKnockback());
        section.set("followrange", mob.getFollowrange());
        section.set("maxhealth", mob.getMaxhealth());
        section.set("attackspeed", mob.getAttackspeed());
        section.set("rangeddamage", mob.getRangeddamage());
        section.set("rangedstregth", mob.getRangedstrength());
        section.set("money", mob.getMoney());
        section.set("exp", mob.getExp());
        switch (mob.getType()) {
            case BATX:
                section.set("asleep", ((RPGBat) mob).isAsleep());
                break;
            case CHICKENX:
            case COWX:
            case PIGX:
            case PIGZOMBIEX:
            case WOLFX:
                section.set("baby", ((RPGChicken) mob).isBaby());
                break;
            case GUARDIANX:
                section.set("guardiantype", ((RPGGuardian) mob).getgType().name());
                break;
            case HORSEX:
                section.set("baby", ((RPGHorse) mob).isBaby());
                section.set("horsetype", ((RPGHorse) mob).getHtype().name());
                section.set("horsevariant", ((RPGHorse) mob).gethVariant().name());
                break;
            case OCELOTX:
                section.set("baby", ((RPGOcelot) mob).isBaby());
                section.set("otype", ((RPGOcelot) mob).getoType().name());
                break;
            case RABBITX:
                section.set("baby", ((RPGRabbit) mob).isBaby());
                section.set("rtype", ((RPGRabbit) mob).getRtype().name());
                break;
            case SHEEPX:
                section.set("baby", ((RPGSheep) mob).isBaby());
                section.set("scolor", ((RPGSheep) mob).getsColor().name());
                break;
            case SKELETONX:
                section.set("stype", ((RPGSkeleton) mob).getsType().name());
                break;
            case VILLAGERX:
                section.set("baby", ((RPGVillager) mob).isBaby());
                section.set("vtype", ((RPGVillager) mob).getvType().name());
                break;
            case ZOMBIEX:
                section.set("baby", ((RPGZombie) mob).isBaby());
                section.set("villager", ((RPGZombie) mob).isVillager());
                break;
        }
        try {
            filemConfig.save(filem);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el monstruo", "Error", 2);
        }
    }

    public void deleteMobSelected() {
        RPGMob mob = getGUIMobByName(gui.getComboSelectorMobs().getSelectedItem().toString());
        if (mob == null) {
            gui.sendMessageWarning("Error", "El monstruo seleccionado no existe");
        } else {
            int diag = JOptionPane.showConfirmDialog(null, "¿Estas seguro de querer eliminar el monstruo?", "Borrar monstruo: " + mob.getName(), JOptionPane.YES_NO_OPTION);
            if (diag == JOptionPane.YES_OPTION) {
                //borrar clase de el combo
                gui.getComboSelectorMobs().removeItem(mob.getName());
                gui.getComboSelectorMobs().setSelectedIndex(0);
                //Borrar clase de el fichero y de la memoria
                listmobs.remove(mob.getName());
                //listnum.remove(gw.getName());
                deleteMobFromFile(mob);
            }
        }
    }

    private void deleteMobFromFile(RPGMob mob) {
        try {
            filemConfig = YamlConfiguration.loadConfiguration(filem);
            int index = listnum.get(mob.getName());
            listnum.remove(mob.getName());
            filemConfig.set("" + index, null);
            filemConfig.save(filem);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar eliminar el monstruo del fichero de configuración", "Error", 2);
        }
    }

    public void loadValuesForMobSelected() {
        RPGMob mob = getGUIMobByName(gui.getComboSelectorMobs().getSelectedItem().toString());
        if (mob == null) {
            gui.sendMessageWarning("Error", "El arma seleccionada no existe");
        } else {
            gui.getTxtNombreMob().setText(mob.getName());
            gui.getComboSelectorTipoMob().setSelectedIndex(mob.getType().ordinal());
            gui.getSpinnerNivelMob().setValue((int) mob.getLevel());
            gui.getSpinnerAtaFisMob().setValue((double) mob.getDamageattack());
            gui.getSpinnerAtaDistMob().setValue((double) mob.getRangeddamage());
            gui.getSpinnerVelocidadMob().setValue((double) mob.getMovementspeed());
            gui.getSpinnerVelocidadAtaqueMob().setValue((double) mob.getAttackspeed());
            gui.getSpinnerRetrocesoMob().setValue((double) mob.getKnockback());
            gui.getSpinnerRangoSeguiMob().setValue((double) mob.getFollowrange());
            gui.getSpinnerMaxVidaMob().setValue((double) mob.getMaxhealth());
            gui.getSpinnerFuerzaDistMob().setValue((float) mob.getRangedstrength());
            gui.getSpinnerDineroDropMob().setValue((long) mob.getMoney());
            gui.getSpinnerExpDropMob().setValue((long) mob.getExp());
            gui.getComboTipoAtaqueMob().setSelectedIndex(mob.getaType().ordinal());
            gui.getComboComportamiento().setSelectedIndex(mob.getmType().ordinal());
            gui.dibujarComponentesMobs();
            gui.recursivelyEnableDisablePanel(gui.getPanelEditorMobs(), true);
        }
    }
}
