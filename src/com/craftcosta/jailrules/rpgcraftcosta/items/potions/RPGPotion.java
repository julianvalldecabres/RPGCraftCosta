/*
 * Copyright (C) 2016 jail
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
package com.craftcosta.jailrules.rpgcraftcosta.items.potions;

import com.craftcosta.jailrules.rpgcraftcosta.items.RPGItem;
import org.bukkit.Effect;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 *
 * @author jail
 */
public class RPGPotion extends RPGItem{
    private PotionType potionType;
    private Effect effect;// effect to display during potion. if potion don't have duration play once.
    private int duration; //seconds 0 instant >0 
    private int value; // value of heal type of speed damage poison per sec...

    
}
