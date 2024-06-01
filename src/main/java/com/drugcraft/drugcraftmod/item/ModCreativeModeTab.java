package com.drugcraft.drugcraftmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {

    public static final CreativeModeTab DRUGCRAFT_TAB = new CreativeModeTab("drugcraft") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.MARIJUANA_SEEDS.get());
        }
    };

}
