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
package com.craftcosta.jailrules.rpgcraftcosta.quests;

import com.craftcosta.jailrules.rpgcraftcosta.area.AreaType;
import com.craftcosta.jailrules.rpgcraftcosta.items.ItemType;
import org.bukkit.entity.EntityType;

/**
 *
 * @author jail
 */
public class RPGQuest {

    private QuestType type;     //Tipo de quest
    private int objectivecount; //Contador limite del objetivo solo aplicable a KILLXOFY o COLLECTXOFY
    private ItemType iType;     //Tipo de objeto en caso de COLLECTXOFY
    private EntityType eType;   //Tipo de objetivo en caso de KILLXOFY, LOCATEPERSON
    private String yName;       //Nombre que reciben los objetivos
    private AreaType aType;     //Tipo de area objetivo en caso de LOCATEPLACE

    private boolean completed;  //Indicador de si la quest esta terminada o en curso.
    private int xcount;

}
