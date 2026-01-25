package com.cranks.dungeons.block;

import com.cranks.dungeons.block.entity.RunicEnhancementAltarBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RunicEnhancementAltarBlock extends BlockWithEntity {
    public static final MapCodec<RunicEnhancementAltarBlock> CODEC = createCodec(RunicEnhancementAltarBlock::new);

    public RunicEnhancementAltarBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RunicEnhancementAltarBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
        if (screenHandlerFactory != null) {
            player.openHandledScreen(screenHandlerFactory);
        }

        return ActionResult.CONSUME;
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        if (!moved) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof RunicEnhancementAltarBlockEntity altar) {
                ItemScatterer.spawn(world, pos, altar);
            }
        }
        super.onStateReplaced(state, world, pos, moved);
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }

    @Override
    public int getOpacity(BlockState state) {
        return 0; // Allows light to pass through the empty spaces
    }

    // This is the most important one for fixing the "X-ray" look
    @Override
    public boolean isSideInvisible(BlockState state, BlockState neighbor, Direction direction) {
        return false;
    }
}