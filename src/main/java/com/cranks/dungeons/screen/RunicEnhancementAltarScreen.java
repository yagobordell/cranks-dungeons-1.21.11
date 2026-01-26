package com.cranks.dungeons.screen;

import com.cranks.dungeons.CranksDungeons;
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
    // Fixed: Added .png extension to the texture path
    private static final Identifier TEXTURE = Identifier.of(CranksDungeons.MOD_ID, "textures/gui/runic_enhancement_altar_gui.png");
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

        int buttonWidth = 48;
        int buttonHeight = 14;
        int buttonX = this.x + 28;
        int buttonY = this.y + 36; // Moved down slightly to not overlap the result slot

        this.enhanceButton = ButtonWidget.builder(Text.literal("Enhance"), button -> {
                    if (this.client != null && this.client.interactionManager != null) {
                        this.client.interactionManager.clickButton(this.handler.syncId, 0);
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

        context.drawTexture(
                net.minecraft.client.gl.RenderPipelines.GUI_TEXTURED,
                TEXTURE,
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