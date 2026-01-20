package com.cranks.dungeons.mixin;

import com.cranks.dungeons.CranksDungeons;
import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class MovementSpeedMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void applyMovementSpeed(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Only run on server side
        if (!(player instanceof ServerPlayerEntity)) return;

        // Get movement speed bonus from custom attribute
        double speedBonus = player.getAttributeValue(ModAttributes.MOVEMENT_SPEED);

        var speedAttr = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        if (speedAttr != null) {
            Identifier modifierId = Identifier.of(CranksDungeons.MOD_ID, "movement_speed_bonus");

            // Remove old modifier
            speedAttr.removeModifier(modifierId);

            // Add new modifier if bonus exists
            if (speedBonus > 0) {
                EntityAttributeModifier modifier = new EntityAttributeModifier(
                        modifierId,
                        speedBonus,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                speedAttr.addTemporaryModifier(modifier);
            }
        }
    }
}