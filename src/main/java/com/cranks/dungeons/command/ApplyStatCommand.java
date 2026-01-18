package com.cranks.dungeons.command;

import com.cranks.dungeons.equipment.ItemStatManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ApplyStatCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("teststat")
                    .then(CommandManager.argument("tier", IntegerArgumentType.integer(1, 5))
                            .executes(context -> {
                                ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
                                int tier = IntegerArgumentType.getInteger(context, "tier");

                                ItemStack sword = new ItemStack(Items.DIAMOND_BOOTS);

                                int addedStats = 0;
                                int maxRetries = 20;
                                int retries = 0;

                                while (addedStats < 4 && retries < maxRetries) {
                                    boolean success = ItemStatManager.addRandomStat(sword, tier);
                                    if (success) {
                                        addedStats++;
                                    }
                                    retries++;
                                }

                                System.out.println("Added " + addedStats + " stats to sword (took " + retries + " attempts)");

                                player.giveItemStack(sword);
                                player.sendMessage(Text.literal("Given diamond sword with " + addedStats + " tier " + tier + " stats!"), false);

                                return 1;
                            })));
        });
    }
}