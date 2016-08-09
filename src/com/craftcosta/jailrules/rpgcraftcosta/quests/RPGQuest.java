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
package com.craftcosta.jailrules.rpgcraftcosta.quests;

import com.craftcosta.jailrules.rpgcraftcosta.items.ItemType;
import org.bukkit.entity.EntityType;

/**
 *
 * @author jail
 */
public class RPGQuest {

    private String questName;
    private String askMessage;
    private String finishMessage;
    private String failMessage;
    private int money;
    private long experience;
    private int ap;
    private int minLevel;       //nivel minimo de jugador para realizar la mision
    private QuestType type;     //Tipo de mision
    private int objectivecount; //Contador limite del objetivo solo aplicable a KILLXOFY o COLLECTXOFY
    private ItemType iType;     //Tipo de objeto en caso de COLLECTXOFY
    private EntityType eType;   //Tipo de objetivo en caso de KILLXOFY, LOCATEPERSON
    private String yName;       //Nombre que reciben los objetivos
    private String areaName;    //Nombre del area en caso de mision tipo LOCATEPLACE
    private boolean completed;  //Indicador de si la quest esta terminada o en curso.
    private int xcount;         //Contador de objetivos completados
}
