package com.cranks.dungeons.command;

import com.cranks.dungeons.equipment.ItemStatManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ApplyStatCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("teststat").then(argument("item", ItemStackArgumentType.itemStack(registryAccess)).then(argument("tier", IntegerArgumentType.integer(1, 5))
                                    .executes(context -> {
                                        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

                                        ItemStack stack = ItemStackArgumentType.getItemStackArgument(context, "item").createStack(1, false);
                                        int tier = IntegerArgumentType.getInteger(context, "tier");

                                        int addedStats = 0;
                                        int maxRetries = 20;
                                        int retries = 0;

                                        while (addedStats < 5 && retries < maxRetries) {
                                            boolean success = ItemStatManager.addRandomStat(stack, tier);
                                            if (success) {
                                                addedStats++;
                                            }
                                            retries++;
                                        }

                                        player.giveItemStack(stack);

                                        player.sendMessage(Text.literal("Given ")
                                                .append(stack.getName())
                                                .append(" with " + addedStats + " tier " + tier + " stats!"), false);

                                        return 1;
                                    }))));
        });
    }
}