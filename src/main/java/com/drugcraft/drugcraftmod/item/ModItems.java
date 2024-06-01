package com.drugcraft.drugcraftmod.item;

import com.drugcraft.drugcraftmod.DrugCraftMod;
import com.drugcraft.drugcraftmod.block.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, DrugCraftMod.MOD_ID);

    public static final RegistryObject<Item> MARIJUANA_SEEDS = ITEMS.register("marijuana_seeds",
            () -> new ItemNameBlockItem(ModBlocks.MARIJUANA_CROP.get(), new Item.Properties().tab(ModCreativeModeTab.DRUGCRAFT_TAB)));
    public static final RegistryObject<Item> MARIJUANA_LEAVES = ITEMS.register("marijuana_leaves",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.DRUGCRAFT_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
