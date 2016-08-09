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
import org.bukkit.Bukkit;

/**
 *
 * @author jail
 */
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

    /**
     *
     * @param arrow
     * @param multiplier
     */
    public static void multiplyArrowDamage(final Arrow arrow, final double multiplier)
  { nms.multiplyArrowDamage(arrow, multiplier);
  }

    /**
     *
     * @param arrow
     * @param add
     */
    public static void addArrowDamage(final Arrow arrow, final double add)
  { nms.addArrowDamage(arrow, add);
  }

    /**
     *
     * @param arrow
     * @param set
     */
    public static void setArrowDamage(final Arrow arrow, final double set)
  { nms.setArrowDamage(arrow, set);
  }

    /**
     *
     * @param arrow
     * @return
     */
    public static double getArrowDamage(final Arrow arrow)
  { return nms.getArrowDamage(arrow);
  }
}
