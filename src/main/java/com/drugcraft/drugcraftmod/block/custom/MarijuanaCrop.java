package com.drugcraft.drugcraftmod.block.custom;

import com.drugcraft.drugcraftmod.item.ModItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.function.Function;

public class MarijuanaCrop extends CropBlock {
    public static final int FIRST_STAGE_MAX_AGE = 4;
    public static final int SECOND_STAGE_MAX_AGE = 1;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, FIRST_STAGE_MAX_AGE + SECOND_STAGE_MAX_AGE);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.2D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.4D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.6D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.8D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16D, 16.0D)
    };



    public MarijuanaCrop(Properties props) {
        super(props);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[this.getAge(state)];
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;

        if (level.getRawBrightness(pos, 0) >= 9) {
            int currentAge = this.getAge(state);

            if (currentAge < this.getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, level, pos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt((int)(25.0F / growthSpeed) + 1) == 0)) {
                    if (currentAge == FIRST_STAGE_MAX_AGE) {
                        if (level.getBlockState(pos.above(1)).is(Blocks.AIR)) {
                            level.setBlock(pos.above(1), this.getStateForAge(currentAge + 1), 2);
                        }
                    } else {
                        level.setBlock(pos, this.getStateForAge(currentAge + 1), 2);
                    }

                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
                }
            }
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return super.mayPlaceOn(state, world, pos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return super.canSurvive(state, level, pos) || (level.getBlockState(pos.below(1)).is(this) &&
                level.getBlockState(pos.below(1)).getValue(AGE) == FIRST_STAGE_MAX_AGE);
    }

    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state) {
        int nextAge = this.getAge(state) + this.getBonemealAgeIncrease(level);
        int maxAge = this.getMaxAge();
        if (nextAge > maxAge) {
            nextAge = maxAge;
        }

        if (this.getAge(state) == FIRST_STAGE_MAX_AGE && level.getBlockState(pos.above(1)).is(Blocks.AIR)) {
            level.setBlock(pos.above(1), this.getStateForAge(nextAge), 2);
        } else {
            level.setBlock(pos, this.getStateForAge(nextAge - SECOND_STAGE_MAX_AGE), 2);
        }
    }

    @Override
    public int getMaxAge() {
        return FIRST_STAGE_MAX_AGE + SECOND_STAGE_MAX_AGE;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.MARIJUANA_SEEDS.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
