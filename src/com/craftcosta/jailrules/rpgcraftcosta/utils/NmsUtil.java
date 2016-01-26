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
import org.bukkit.Bukkit;

public final class NmsUtil
{ 

    private static NmsInterface nms;

  private NmsUtil() {}

  static
  { String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    try
    { nms = (NmsInterface)Class.forName("com.mandarinacorp.snowarrows.nms.NMS_Arrow").newInstance();
    }
    catch(InstantiationException | IllegalAccessException | ClassNotFoundException e)
    { e.printStackTrace();
    }
  }


  public static void multiplyArrowDamage(final Arrow arrow, final double multiplier)
  { nms.multiplyArrowDamage(arrow, multiplier);
  }

  public static void addArrowDamage(final Arrow arrow, final double add)
  { nms.addArrowDamage(arrow, add);
  }

  public static void setArrowDamage(final Arrow arrow, final double set)
  { nms.setArrowDamage(arrow, set);
  }

  public static double getArrowDamage(final Arrow arrow)
  { return nms.getArrowDamage(arrow);
  }
}
