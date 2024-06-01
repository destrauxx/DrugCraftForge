package com.drugcraft.drugcraftmod.block;

import com.drugcraft.drugcraftmod.DrugCraftMod;
import com.drugcraft.drugcraftmod.block.custom.MarijuanaCrop;
import com.drugcraft.drugcraftmod.item.ModCreativeModeTab;
import com.drugcraft.drugcraftmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, DrugCraftMod.MOD_ID);

    public static final RegistryObject<Block> MARIJUANA_CROP = BLOCKS.register("marijuana_crop",
            () -> new MarijuanaCrop(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission()));
    public static final RegistryObject<Block> WILD_MARIJUANA = registerBlock("wild_marijuana",
            () -> new TallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH).noCollission()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ModCreativeModeTab.DRUGCRAFT_TAB)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
