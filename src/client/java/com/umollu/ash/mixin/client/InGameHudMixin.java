package com.umollu.ash.mixin.client;

import com.umollu.ash.AshCommands;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(at = @At("HEAD"), method = "render")
    public void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        Entity cameraEntity = client.getCameraEntity();
        if(!client.getDebugHud().shouldShowDebugHud() && AshCommands.config.showHud) {
            String ashString = "";
            if(AshCommands.config.showFps) {
                ashString += String.format("%d fps ", ((MinecraftClientMixin) MinecraftClient.getInstance()).getCurrentFps());
            }
            if(AshCommands.config.showCoords && !client.hasReducedDebugInfo()) {
                BlockPos blockPos = cameraEntity.getBlockPos();
                ashString += String.format("%d %d %d ", blockPos.getX(), blockPos.getY(), blockPos.getZ());
            }
            if(AshCommands.config.showDirection) {
                Direction direction = cameraEntity.getHorizontalFacing();
                ashString += String.format("%5s ", direction);
            }

            int textPosX = 5;

            if (AshCommands.config.align == 1) {
                textPosX = (client.getWindow().getScaledWidth() - client.textRenderer.getWidth(ashString)) / 2 - textPosX;
            }
            if (AshCommands.config.align == 2) {
                textPosX = client.getWindow().getScaledWidth() - client.textRenderer.getWidth(ashString) - textPosX;
            }

            int textPosY = 5;

            if (AshCommands.config.verticalAlign == 1) {
                textPosY = (client.getWindow().getScaledHeight() - client.textRenderer.fontHeight) / 2 - textPosY;
            }
            if (AshCommands.config.verticalAlign == 2) {
                textPosY = client.getWindow().getScaledHeight() - client.textRenderer.fontHeight - textPosY;
            }

            context.drawText(client.textRenderer, ashString, textPosX, textPosY, AshCommands.config.hudColor, true);
        }
    }
}