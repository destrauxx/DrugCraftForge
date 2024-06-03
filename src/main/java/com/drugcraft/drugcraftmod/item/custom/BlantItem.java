package com.drugcraft.drugcraftmod.item.custom;

import com.drugcraft.drugcraftmod.item.client.BlantItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.network.PacketDistributor;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class BlantItem extends Item implements IAnimatable, ISyncable {
    private static final String CONTROLLER_NAME = "popupController";
    private static final int ANIM_OPEN = 0;
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final AnimationBuilder ACTIVATE_ANIM = new AnimationBuilder().addAnimation("blant.use", ILoopType.EDefaultLoopTypes.PLAY_ONCE);

    public BlantItem(Properties properties) {
        super(properties);
        GeckoLibNetwork.registerSyncable(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);

        consumer.accept(new IClientItemExtensions() {
            private BlantItemRenderer renderer = new BlantItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController controller = new AnimationController(this, CONTROLLER_NAME,
                20, this::predicate);

//        controller.registerSoundListener(this::soundListener);
        animationData.addAnimationController(controller);
    }

//    private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
//        LocalPlayer player = Minecraft.getInstance().player;
//        if (player != null) {
////            player.playSound(SoundRegistry); // TODO
//        }
//    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerLevel)world);

            final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player);
            GeckoLibNetwork.syncAnimation(target, this, id, ANIM_OPEN);
        }
//        return super.use(world, player, hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onAnimationSync(int id, int state) {
        if (state == ANIM_OPEN) {
            final AnimationController controller = GeckoLibUtil.getControllerForID(this.factory, id, CONTROLLER_NAME);

            if (controller.getAnimationState() == AnimationState.Stopped) {
                final LocalPlayer player = Minecraft.getInstance().player;
                if (player != null) {
                    player.displayClientMessage(Component.literal("Smoking the blant!"), true);
                }

                controller.markNeedsReload();

                controller.setAnimation(ACTIVATE_ANIM);
            }
        }
    }
}
