package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class ExperienceBonusMixin {

    @ModifyVariable(
            method = "addExperience",
            at = @At("HEAD"),
            argsOnly = true
    )
    private int applyExperienceBonus(int experience) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Get experience bonus from your custom attribute
        // Logic: 0.1 = 10% bonus, 1.0 = 100% bonus
        double expBonus = player.getAttributeValue(ModAttributes.EXPERIENCE_BONUS);

        if (expBonus > 0 && experience > 0) {
            double bonusAmount = (double) experience * expBonus;

            // Return the new total (original + bonus)
            return experience + (int) Math.round(bonusAmount);
        }

        return experience;
    }
}