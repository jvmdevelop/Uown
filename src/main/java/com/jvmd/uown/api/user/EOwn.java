package com.jvmd.uown.api.user;

import com.jvmd.uown.UownMod;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public enum EOwn {
    GREEN(UownMod.ModItems.SERVKEEPER_GREEN),
    ORANGE(UownMod.ModItems.SERVKEEPER_ORANGE),
    RED(UownMod.ModItems.SERVKEEPER_RED),;

    public RegistryObject<Item> item;

    EOwn(RegistryObject<Item> servkeeperGreen) {
        item = servkeeperGreen;
    }
}
