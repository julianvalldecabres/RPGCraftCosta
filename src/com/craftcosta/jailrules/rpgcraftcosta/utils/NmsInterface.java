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

import org.bukkit.entity.Arrow;

/**
 *
 * @author jail
 */
interface NmsInterface {
    /**
  * Multiplies the damage of an arrow
  */
  public abstract void multiplyArrowDamage(final Arrow arrow, final double multiplier);

  /**
  * Increases damage of an arrow
  */
  public abstract void addArrowDamage(final Arrow arrow, final double add);

  /**
  * Sets the damage of an arrow
  */
  public abstract void setArrowDamage(final Arrow arrow, final double set);

  /**
  * Gets the damage of an arrow
  */
  public abstract double getArrowDamage(final Arrow arrow);
}