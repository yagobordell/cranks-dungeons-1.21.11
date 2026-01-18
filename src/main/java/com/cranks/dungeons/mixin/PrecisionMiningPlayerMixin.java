package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PrecisionMiningPlayerMixin {

    @Inject(method = "isBlockBreakingRestricted", at = @At("HEAD"))
    private void storeDamageBeforeBreaking(net.minecraft.world.World world, BlockPos pos, net.minecraft.world.GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack stack = player.getMainHandStack();

        if (stack.isDamageable()) {
            double precisionChance = player.getAttributeValue(ModAttributes.PRECISION_MINING);

            if (precisionChance > 0 && Math.random() < precisionChance) {
                // We'll handle this in a different way
                // Store that this player should not lose durability
            }
        }
    }
}