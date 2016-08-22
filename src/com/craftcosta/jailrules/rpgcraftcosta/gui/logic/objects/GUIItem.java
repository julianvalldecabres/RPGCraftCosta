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

import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumItems;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumQuality;
import java.util.List;

/**
 *
 * @author jail
 */
class GUIItem {
    String name;
    EnumItems material;
    EnumQuality quality;
    List<String> description;

    public GUIItem(String name, EnumItems material, EnumQuality quality, List<String> description) {
        this.name = name;
        this.material = material;
        this.quality = quality;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumItems getMaterial() {
        return material;
    }

    public void setMaterial(EnumItems material) {
        this.material = material;
    }

    public EnumQuality getQuality() {
        return quality;
    }

    public void setQuality(EnumQuality quality) {
        this.quality = quality;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }
    

}
