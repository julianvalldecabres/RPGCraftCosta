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
