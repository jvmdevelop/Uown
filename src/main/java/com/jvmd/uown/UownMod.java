package com.jvmd.uown;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.item.Item;


@Mod("uown")
public class UownMod {

    public final static Minecraft mc;
    static {
        mc = Minecraft.getInstance();
    }

    public UownMod() {
        ModItems.register();

    }

    public static class ModItems {
        private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "uown");

        public static final RegistryObject<Item> SERVKEEPER_GREEN = ITEMS.register("servkeeper_green",
                () -> new Item(new Item.Properties()));

        public static final RegistryObject<Item> SERVKEEPER_ORANGE = ITEMS.register("servkeeper_orange",
                () -> new Item(new Item.Properties()));

        public static final RegistryObject<Item> SERVKEEPER_RED = ITEMS.register("servkeeper_red",
                () -> new Item(new Item.Properties()));



        public static void register() {
            ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }
}