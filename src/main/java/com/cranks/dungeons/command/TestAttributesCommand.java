package com.cranks.dungeons.command;

import com.cranks.dungeons.registry.ModAttributes;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TestAttributesCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("testattributes")
                    .executes(TestAttributesCommand::execute));
        });
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            source.sendFeedback(() -> Text.literal("--- Cranks Dungeons Stats ---").formatted(Formatting.GOLD), false);

            showStat(source, player, "Fire Resistance", ModAttributes.FIRE_RESISTANCE);
            showStat(source, player, "Cold Resistance", ModAttributes.COLD_RESISTANCE);
            showStat(source, player, "Lightning Resistance", ModAttributes.LIGHTNING_RESISTANCE);
            showStat(source, player, "Void Resistance", ModAttributes.VOID_RESISTANCE);
            showStat(source, player, "Crit Chance", ModAttributes.CRIT_CHANCE);

            source.sendFeedback(() -> Text.literal("---------------------------").formatted(Formatting.GOLD), false);
        } else {
            source.sendError(Text.literal("Solo jugadores pueden usar este comando."));
        }

        return 1;
    }

    private static void showStat(ServerCommandSource source, ServerPlayerEntity player, String name, net.minecraft.registry.entry.RegistryEntry<net.minecraft.entity.attribute.EntityAttribute> attribute) {
        EntityAttributeInstance instance = player.getAttributeInstance(attribute);
        if (instance != null) {
            double val = instance.getValue();
            source.sendFeedback(() -> Text.literal("• " + name + ": ").append(Text.literal((val * 100) + "%").formatted(Formatting.AQUA)), false);
        } else {
            source.sendFeedback(() -> Text.literal("• " + name + ": ").append(Text.literal("ERROR (Not found)").formatted(Formatting.RED)), false);
        }
    }
}