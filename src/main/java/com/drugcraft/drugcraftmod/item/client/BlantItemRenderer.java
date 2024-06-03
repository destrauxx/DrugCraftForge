package com.drugcraft.drugcraftmod.item.client;

import com.drugcraft.drugcraftmod.item.custom.BlantItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BlantItemRenderer extends GeoItemRenderer<BlantItem> {
    public BlantItemRenderer() {
        super(new BlantItemModel());
    }
}
