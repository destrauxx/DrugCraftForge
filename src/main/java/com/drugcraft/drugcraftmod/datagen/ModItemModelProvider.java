package com.drugcraft.drugcraftmod.datagen;

import com.drugcraft.drugcraftmod.DrugCraftMod;
import com.drugcraft.drugcraftmod.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DrugCraftMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.MARIJUANA_SEEDS);
        simpleItem(ModItems.MARIJUANA_LEAVES);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DrugCraftMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handleldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(DrugCraftMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
