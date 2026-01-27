package com.cranks.dungeons.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerMixin {

    @Unique
    private int lastValidSlot = -1;

    @Inject(method = "tick", at = @At("HEAD"))
    private void forceSlotLock(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        int currentSlot = player.getInventory().getSelectedSlot();

        if (player.getAttackCooldownProgress(0.0F) < 1.0F) {
            if (lastValidSlot == -1) {
                lastValidSlot = currentSlot;
            }

            if (currentSlot != lastValidSlot) {
                player.getInventory().setSelectedSlot(lastValidSlot);
                player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(lastValidSlot));
            }
        } else {
            lastValidSlot = -1;
        }
    }
}