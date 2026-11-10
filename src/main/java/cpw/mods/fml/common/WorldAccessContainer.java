/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     cpw - implementation
 */

package cpw.mods.fml.common;

import java.util.Map;

import com.mojang.nbt.Tag;
import com.mojang.nbt.NBTTagCompound;
import net.fakecraft.world.storage.SaveHandler;
import net.fakecraft.world.storage.WorldInfo;


public interface WorldAccessContainer
{
    public NBTTagCompound getDataForWriting(SaveHandler handler, WorldInfo info);
    public void readData(SaveHandler handler, WorldInfo info, Map<String, Tag> propertyMap, NBTTagCompound tag);
}
