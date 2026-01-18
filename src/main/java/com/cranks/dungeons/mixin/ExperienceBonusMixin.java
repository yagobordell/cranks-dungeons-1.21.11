package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public class ExperienceBonusMixin {

    @ModifyVariable(
            method = "addExperience",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private int applyExperienceBonus(int experience) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        double expBonus = player.getAttributeValue(ModAttributes.EXPERIENCE_BONUS);

        if (expBonus > 0) {
            return (int) (experience * (1.0 + expBonus));
        }

        return experience;
    }
}