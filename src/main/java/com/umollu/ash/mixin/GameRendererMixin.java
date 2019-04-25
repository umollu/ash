package com.umollu.ash.mixin;

import com.umollu.ash.AshMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;alphaFunc(IF)V"), method = "render")
	public void render(float float_1, long long_1, boolean boolean_1, CallbackInfo info) {

		MinecraftClient client = MinecraftClient.getInstance();
		Entity cameraEntity = client.getCameraEntity();

		if(!client.options.debugEnabled && AshMod.config.showHud) {
			double scaleFactor = client.window.getScaleFactor();
			GlStateManager.pushMatrix();
			GlStateManager.scaled(1 * scaleFactor, 1 * scaleFactor, 1 * scaleFactor);
			String ashString = "";
			if(AshMod.config.showFps) {
				ashString += String.format("%d fps ", MinecraftClient.getCurrentFps());
			}
			if(AshMod.config.showCoords) {
				BlockPos blockPos = new BlockPos(cameraEntity.x, cameraEntity.getBoundingBox().minY, cameraEntity.z);
				ashString += String.format("%d %d %d ", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			}
			if(AshMod.config.showDirection) {
				Direction direction = cameraEntity.getHorizontalFacing();
				ashString += String.format("%5s ", direction);
			}

			float textPosX = 5;

			if (AshMod.config.align == 1) {
				textPosX = (client.window.getScaledWidth() - client.textRenderer.getStringWidth(ashString)) / 2f - textPosX;
			}
			if (AshMod.config.align == 2) {
				textPosX = client.window.getScaledWidth() - client.textRenderer.getStringWidth(ashString) - textPosX;
			}

			client.textRenderer.drawWithShadow(ashString, textPosX, 5, AshMod.config.hudColor);
			GlStateManager.popMatrix();
		}
	}
}