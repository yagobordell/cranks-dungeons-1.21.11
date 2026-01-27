package com.cranks.dungeons.screen;

import com.cranks.dungeons.CranksDungeons;
import com.cranks.dungeons.block.entity.RunicEnhancementAltarBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RunicEnhancementAltarScreen extends HandledScreen<RunicEnhancementAltarScreenHandler> {
    private static final Identifier STANDARD_TEXTURE = Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui.png");

    private static final Identifier[] TEXTURES = {
            Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui_1.png"),
            Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui_2.png"),
            Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui_3.png"),
            Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui_4.png"),
            Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui_5.png"),
            Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui_6.png"),
            Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui_7.png"),
            Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui_8.png"),
            Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui_9.png"),
            Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui_10.png")
    };

    private int currentTextureIndex = 0;
    private int tickCounter = 0;
    private static final int TICKS_PER_TEXTURE = 200;

    private ButtonWidget enhanceButton;

    public RunicEnhancementAltarScreen(RunicEnhancementAltarScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 165;
        this.backgroundWidth = 176;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        createButton();
    }

    private void createButton() {
        int buttonWidth = 48;
        int buttonHeight = 14;
        int buttonX = this.x + 28;
        int buttonY = this.y + 36;

        if (this.enhanceButton != null) {
            this.remove(this.enhanceButton);
        }

        this.enhanceButton = ButtonWidget.builder(Text.literal("Enhance"), button -> {
                    if (this.client != null && this.client.interactionManager != null) {
                        this.client.interactionManager.clickButton(this.handler.syncId, 0);

                        // Recreate the button to reset its state
                        this.client.execute(this::createButton);
                    }
                })
                .dimensions(buttonX, buttonY, buttonWidth, buttonHeight)
                .build();

        this.addDrawableChild(this.enhanceButton);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;

        // Check if equipment slot has an item
        boolean hasEquipment = !handler.getSlot(RunicEnhancementAltarBlockEntity.EQUIPMENT_SLOT).getStack().isEmpty();

        Identifier textureToUse;

        if (hasEquipment) {
            // Show standard texture when equipment is present
            textureToUse = STANDARD_TEXTURE;
            // Reset rotation when equipment is added
            tickCounter = 0;
            currentTextureIndex = 0;
        } else {
            // Rotate through textures when slots are empty
            tickCounter++;
            if (tickCounter >= TICKS_PER_TEXTURE) {
                tickCounter = 0;
                currentTextureIndex = (currentTextureIndex + 1) % TEXTURES.length;
            }
            textureToUse = TEXTURES[currentTextureIndex];
        }

        context.drawTexture(
                net.minecraft.client.gl.RenderPipelines.GUI_TEXTURED,
                textureToUse,
                x, y,
                0.0f, 0.0f,
                this.backgroundWidth,
                this.backgroundHeight,
                176, 165
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 4210752, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 4210752, false);
    }
}