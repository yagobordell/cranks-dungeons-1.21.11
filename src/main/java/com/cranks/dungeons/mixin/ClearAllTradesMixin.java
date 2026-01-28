package com.cranks.dungeons.mixin;

import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TradeOffers.class)
public class ClearAllTradesMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void onStaticInit(CallbackInfo ci) {
        TradeOffers.PROFESSION_TO_LEVELED_TRADE.forEach((profession, trades) -> {
            trades.clear();
        });
    }
}