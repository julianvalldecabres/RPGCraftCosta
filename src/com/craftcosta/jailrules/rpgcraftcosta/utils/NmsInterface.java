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
