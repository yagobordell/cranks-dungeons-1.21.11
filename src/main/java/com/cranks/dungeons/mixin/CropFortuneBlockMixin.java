package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Block.class)
public class CropFortuneBlockMixin {

    @Inject(method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true)
    private static void onDropStacks(BlockState state, World world, BlockPos pos, net.minecraft.block.entity.BlockEntity blockEntity, net.minecraft.entity.Entity entity, ItemStack tool, CallbackInfo ci) {
        if (world.isClient() || !(entity instanceof PlayerEntity player)) return;

        if (!isFullyGrown(state) || !tool.getItem().toString().toLowerCase().contains("hoe")) return;

        var attributeInstance = player.getAttributeInstance(ModAttributes.CROP_FORTUNE);
        if (attributeInstance == null) return;

        double chance = attributeInstance.getValue();

        if (world.random.nextDouble() < chance) {
            if (world instanceof net.minecraft.server.world.ServerWorld serverWorld) {
                java.util.List<ItemStack> originalDrops = Block.getDroppedStacks(state, serverWorld, pos, blockEntity, entity, tool);

                for (ItemStack stack : originalDrops) {
                    stack.setCount(stack.getCount() * 2);

                    Block.dropStack(world, pos, stack);
                }

                world.playSound(null, pos, net.minecraft.sound.SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                        net.minecraft.sound.SoundCategory.BLOCKS, 0.5f, 1.5f);
                serverWorld.spawnParticles(net.minecraft.particle.ParticleTypes.HAPPY_VILLAGER,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.2, 0.2, 0.2, 0.05);

                ci.cancel();
            }
        }
    }

    private static boolean isFullyGrown(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof CropBlock crop) return crop.isMature(state);
        if (block instanceof NetherWartBlock) return state.get(NetherWartBlock.AGE) >= 3;
        if (block instanceof CocoaBlock) return state.get(CocoaBlock.AGE) >= 2;
        return false;
    }
}