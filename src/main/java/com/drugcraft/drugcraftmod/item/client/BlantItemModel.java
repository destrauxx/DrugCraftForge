package com.drugcraft.drugcraftmod.item.client;

import com.drugcraft.drugcraftmod.DrugCraftMod;
import com.drugcraft.drugcraftmod.item.custom.BlantItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlantItemModel extends AnimatedGeoModel<BlantItem> {
    public static final ResourceLocation modelResource = new ResourceLocation(DrugCraftMod.MOD_ID, "geo/blant.geo.json");
    public static final ResourceLocation textureResource = new ResourceLocation(DrugCraftMod.MOD_ID, "textures/blant.png");
    public static final ResourceLocation animationResource = new ResourceLocation(DrugCraftMod.MOD_ID, "animations/blant.animation.json");


    @Override
    public ResourceLocation getModelResource(BlantItem blantItem) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureResource(BlantItem blantItem) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationResource(BlantItem blantItem) {
        return animationResource;
    }
}
