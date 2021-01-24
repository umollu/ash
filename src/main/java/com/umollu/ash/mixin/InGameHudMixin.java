package com.umollu.ash.mixin;

import com.umollu.ash.AshCommands;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(at = @At("TAIL"), method = "render")
    public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        Entity cameraEntity = client.getCameraEntity();

        if(!client.options.debugEnabled && AshCommands.config.showHud) {
            matrixStack.push();
            String ashString = "";
            if(AshCommands.config.showFps) {
                ashString += String.format("%d fps ", ((MinecraftClientMixin) MinecraftClient.getInstance()).getCurrentFps());
            }
            if(AshCommands.config.showCoords && !client.hasReducedDebugInfo()) {
                BlockPos blockPos = new BlockPos(cameraEntity.getX(), cameraEntity.getBoundingBox().getMin(Direction.Axis.Y), cameraEntity.getZ());
                ashString += String.format("%d %d %d ", blockPos.getX(), blockPos.getY(), blockPos.getZ());
            }
            if(AshCommands.config.showDirection) {
                Direction direction = cameraEntity.getHorizontalFacing();
                ashString += String.format("%5s ", direction);
            }

            float textPosX = 5;

            if (AshCommands.config.align == 1) {
                textPosX = (client.getWindow().getScaledWidth() - client.textRenderer.getWidth(ashString)) / 2f - textPosX;
            }
            if (AshCommands.config.align == 2) {
                textPosX = client.getWindow().getScaledWidth() - client.textRenderer.getWidth(ashString) - textPosX;
            }

            float textPosY = 5;

            if (AshCommands.config.verticalAlign == 1) {
                textPosY = (client.getWindow().getScaledHeight() - client.textRenderer.fontHeight) / 2f - textPosY;
            }
            if (AshCommands.config.verticalAlign == 2) {
                textPosY = client.getWindow().getScaledHeight() - client.textRenderer.fontHeight - textPosY;
            }

            client.textRenderer.drawWithShadow(matrixStack, ashString, textPosX, textPosY, AshCommands.config.hudColor);
            matrixStack.pop();
        }
    }
}