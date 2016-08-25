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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.armor;

import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumArmorPart;
import java.util.Map;

/**
 *
 * @author jail
 */
public class GUISet {

    private int index;
    private String setname;
    private Map<EnumArmorPart, GUIArmor> set;

    public GUISet(int index, String setname, Map<EnumArmorPart, GUIArmor> set) {
        this.index = index;
        this.setname = setname;
        this.set = set;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getSetname() {
        return setname;
    }

    public void setSetname(String setname) {
        this.setname = setname;
    }

    public Map<EnumArmorPart, GUIArmor> getSet() {
        return set;
    }

    public void setSet(Map<EnumArmorPart, GUIArmor> set) {
        this.set = set;
    }

}
