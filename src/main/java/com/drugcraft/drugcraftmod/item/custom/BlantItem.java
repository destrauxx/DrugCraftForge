package com.drugcraft.drugcraftmod.item.custom;

import com.drugcraft.drugcraftmod.item.client.BlantItemRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class BlantItem extends Item implements IAnimatable {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public BlantItem(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);

        consumer.accept(new IClientItemExtensions() {
            private BlantItemRenderer renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new BlantItemRenderer();

                return renderer;
            }
        });

    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, event -> PlayState.CONTINUE));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
