package com.cranks.dungeons.equipment;

import com.cranks.dungeons.stat.CustomStat;
import com.cranks.dungeons.stat.StatCategory;
import com.cranks.dungeons.stat.StatRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.*;

public class ItemStatManager {
    private static final String STATS_KEY = "CustomStats";
    private static final int MAX_STATS = 5;

    public static class ItemStat {
        public final String statId;
        public final double value;
        public final int tier;

        public ItemStat(String statId, double value, int tier) {
            this.statId = statId;
            this.value = value;
            this.tier = tier;
        }

        // Legacy constructor for backwards compatibility
        public ItemStat(String statId, double value) {
            this(statId, value, 1);
        }
    }

    public static List<ItemStat> getStats(ItemStack stack) {
        List<ItemStat> stats = new ArrayList<>();
        NbtCompound nbt = stack.getOrDefault(net.minecraft.component.DataComponentTypes.CUSTOM_DATA,
                net.minecraft.component.type.NbtComponent.DEFAULT).copyNbt();

        if (nbt.contains(STATS_KEY)) {
            Optional<NbtList> statsListOpt = nbt.getList(STATS_KEY);
            if (statsListOpt.isPresent()) {
                NbtList statsList = statsListOpt.get();
                for (int i = 0; i < statsList.size(); i++) {
                    Optional<NbtCompound> statNbtOpt = statsList.getCompound(i);
                    if (statNbtOpt.isPresent()) {
                        NbtCompound statNbt = statNbtOpt.get();
                        Optional<String> idOpt = statNbt.getString("id");
                        Optional<Double> valueOpt = statNbt.getDouble("value");

                        // Read tier (default to 1 if not present for backwards compatibility)
                        int tier = statNbt.getInt("tier").orElse(1);

                        if (idOpt.isPresent() && valueOpt.isPresent()) {
                            stats.add(new ItemStat(idOpt.get(), valueOpt.get(), tier));
                        }
                    }
                }
            }
        }

        return stats;
    }

    public static boolean canAddStat(ItemStack stack) {
        return getStats(stack).size() < MAX_STATS;
    }

    public static boolean addRandomStat(ItemStack stack, int tier) {
        System.out.println("=== addRandomStat called ===");
        System.out.println("Current stats: " + getStats(stack).size() + "/5");
        System.out.println("Tier: " + tier);

        if (!canAddStat(stack)) {
            System.out.println("Cannot add stat - already at max");
            return false;
        }

        Optional<EquipmentType> equipType = EquipmentType.getTypeForItem(stack);
        if (equipType.isEmpty()) {
            System.out.println("Cannot add stat - item not recognized as equipment");
            return false;
        }

        System.out.println("Equipment type: " + equipType.get());

        List<StatCategory> allowedCategories = equipType.get().getAllowedCategories();
        if (allowedCategories.isEmpty()) {
            System.out.println("Cannot add stat - no allowed categories");
            return false;
        }

        System.out.println("Allowed categories: " + allowedCategories);

        StatCategory category = allowedCategories.get(new Random().nextInt(allowedCategories.size()));
        System.out.println("Selected category: " + category);

        CustomStat stat = StatRegistry.getRandomStatForCategory(category);
        if (stat == null) {
            System.out.println("Cannot add stat - no stats available for category " + category);
            return false;
        }

        System.out.println("Selected stat: " + stat.getId());

        List<ItemStat> existingStats = getStats(stack);
        CustomStat newTmpStat = stat;

        boolean alreadyHasStat = existingStats.stream()
                .anyMatch(s -> {
                    CustomStat existingStat = StatRegistry.getStat(s.statId);
                    if (existingStat == null) return false;

                    if (s.statId.equals(newTmpStat.getId())) return true;

                    if (existingStat.getAttribute().equals(newTmpStat.getAttribute())) {
                        return true;
                    }

                    return false;
                });

        if (alreadyHasStat) {
            System.out.println("Cannot add stat - already has " + stat.getId() + " or same attribute");
            return false;
        }

        double value = stat.rollValue(tier);
        System.out.println("Rolled value: " + value);

        NbtCompound nbt = stack.getOrDefault(net.minecraft.component.DataComponentTypes.CUSTOM_DATA,
                net.minecraft.component.type.NbtComponent.DEFAULT).copyNbt();

        NbtList statsList;
        Optional<NbtList> statsListOpt = nbt.getList(STATS_KEY);
        if (statsListOpt.isPresent()) {
            statsList = statsListOpt.get();
        } else {
            statsList = new NbtList();
        }

        NbtCompound newStat = new NbtCompound();
        newStat.putString("id", stat.getId());
        newStat.putDouble("value", value);
        newStat.putInt("tier", tier); // Store the tier
        statsList.add(newStat);

        nbt.put(STATS_KEY, statsList);
        stack.set(net.minecraft.component.DataComponentTypes.CUSTOM_DATA,
                net.minecraft.component.type.NbtComponent.of(nbt));

        System.out.println("Successfully added stat!");
        return true;
    }

    public static void addStatsToTooltip(ItemStack stack, List<Text> tooltip) {
        List<ItemStat> stats = getStats(stack);

        if (!stats.isEmpty()) {
            tooltip.add(Text.empty());
            tooltip.add(Text.literal("Enhanced Stats:").formatted(Formatting.GOLD, Formatting.BOLD));

            for (ItemStat itemStat : stats) {
                CustomStat stat = StatRegistry.getStat(itemStat.statId);
                if (stat != null) {
                    // Create the stat line with tier indicator
                    Text statText = createStatTooltip(stat, itemStat.value, itemStat.tier);
                    tooltip.add(Text.literal("  ").append(statText));
                }
            }
        }
    }

    private static Text createStatTooltip(CustomStat stat, double value, int tier) {
        String valueStr = stat.formatValue(value);

        Formatting tierColor = getTierColor(tier);

        return Text.literal(valueStr)
                .formatted(Formatting.WHITE)
                .append(Text.literal(" (T" + tier + ")")
                        .formatted(tierColor));
    }

    private static Formatting getTierColor(int tier) {
        return switch (tier) {
            case 1 -> Formatting.GRAY;
            case 2 -> Formatting.GRAY;
            case 3 -> Formatting.GREEN;
            case 4 -> Formatting.GREEN;
            case 5 -> Formatting.GOLD;
            default -> Formatting.WHITE;
        };
    }
}