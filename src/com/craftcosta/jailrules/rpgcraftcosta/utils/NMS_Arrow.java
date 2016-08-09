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
package com.craftcosta.jailrules.rpgcraftcosta.utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import net.minecraft.server.v1_8_R3.EntityArrow;

import org.bukkit.entity.Arrow;

final class NMS_Arrow implements NmsInterface {

    public void multiplyArrowDamage(final Arrow projectile, double multiplier) {
        EntityArrow arrow = ((CraftArrow) projectile).getHandle();
        arrow.b(arrow.j() * multiplier);
    }

    public void addArrowDamage(final Arrow projectile, final double add) {
        EntityArrow arrow = ((CraftArrow) projectile).getHandle();
        arrow.b(arrow.j() + add);
    }

    public void setArrowDamage(final Arrow projectile, final double set) {
        EntityArrow arrow = ((CraftArrow) projectile).getHandle();
        arrow.b(set);
    }

    public double getArrowDamage(final Arrow projectile) {
        EntityArrow arrow = ((CraftArrow) projectile).getHandle();
        return arrow.j();
    }
}
