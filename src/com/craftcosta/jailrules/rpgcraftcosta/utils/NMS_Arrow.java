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
package com.craftcosta.jailrules.rpgcraftcosta.utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import net.minecraft.server.v1_8_R3.EntityArrow;

import org.bukkit.entity.Arrow;

final class NMS_v1_8_R3 implements NmsInterface {

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
