package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.ToolStatHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class PrecisionMiningPlayerMixin {

    @Inject(method = "postMine", at = @At("HEAD"), cancellable = true)
    private void applyPrecisionMining(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
        if (!world.isClient() && miner instanceof PlayerEntity player) {
            if (state.getHardness(world, pos) != 0.0F) {
                // Get precision mining from the tool being used (the stack parameter)
                double precisionChance = ToolStatHelper.getPrecisionMining(stack);

                if (precisionChance > 0 && player.getRandom().nextDouble() < precisionChance) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}