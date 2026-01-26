package com.cranks.dungeons.screen;

import com.cranks.dungeons.block.entity.RunicEnhancementAltarBlockEntity;
import com.cranks.dungeons.equipment.EquipmentType;
import com.cranks.dungeons.item.RunicTomeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class RunicEnhancementAltarScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final RunicEnhancementAltarBlockEntity blockEntity;

    // Client constructor
    public RunicEnhancementAltarScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3));
    }

    // Server constructor
    public RunicEnhancementAltarScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.RUNIC_ENHANCEMENT_ALTAR, syncId);
        this.inventory = inventory;
        this.blockEntity = inventory instanceof RunicEnhancementAltarBlockEntity ? (RunicEnhancementAltarBlockEntity) inventory : null;

        checkSize(inventory, 3);
        inventory.onOpen(playerInventory.player);

        // Equipment slot - Adjusted to fit the top gray box
        this.addSlot(new Slot(inventory, RunicEnhancementAltarBlockEntity.EQUIPMENT_SLOT, 44, 17) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return EquipmentType.getTypeForItem(stack).isPresent();
            }
        });

        // Tome slot - Adjusted to fit the bottom gray box
        this.addSlot(new Slot(inventory, RunicEnhancementAltarBlockEntity.TOME_SLOT, 44, 53) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof RunicTomeItem;
            }
        });

        // Result slot - Centered inside the blue circle
        this.addSlot(new Slot(inventory, RunicEnhancementAltarBlockEntity.RESULT_SLOT, 116, 35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        // Player inventory - Centered to match the 165 width
        // Offset by 4 pixels from the left edge to center the 162px wide inventory
        int inventoryX = 8;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, inventoryX + j * 18, 84 + i * 18));
            }
        }

        // Player hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, inventoryX + i * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot clickedSlot = this.slots.get(slot);

        if (clickedSlot.hasStack()) {
            ItemStack clickedStack = clickedSlot.getStack();
            newStack = clickedStack.copy();

            if (slot == RunicEnhancementAltarBlockEntity.RESULT_SLOT) {
                // Taking from result slot
                if (!this.insertItem(clickedStack, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                clickedSlot.onQuickTransfer(clickedStack, newStack);
            } else if (slot >= 3) {
                // From player inventory
                if (clickedStack.getItem() instanceof RunicTomeItem) {
                    if (!this.insertItem(clickedStack, RunicEnhancementAltarBlockEntity.TOME_SLOT, RunicEnhancementAltarBlockEntity.TOME_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (EquipmentType.getTypeForItem(clickedStack).isPresent()) {
                    if (!this.insertItem(clickedStack, RunicEnhancementAltarBlockEntity.EQUIPMENT_SLOT, RunicEnhancementAltarBlockEntity.EQUIPMENT_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slot < 30) {
                    if (!this.insertItem(clickedStack, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.insertItem(clickedStack, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(clickedStack, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (clickedStack.isEmpty()) {
                clickedSlot.setStack(ItemStack.EMPTY);
            } else {
                clickedSlot.markDirty();
            }

            if (clickedStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            clickedSlot.onTakeItem(player, clickedStack);
        }

        return newStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }

    public boolean tryEnhance() {
        if (blockEntity != null) {
            return blockEntity.tryEnhance();
        }
        return false;
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id == 0) {
            return tryEnhance();
        }
        return false;
    }
}