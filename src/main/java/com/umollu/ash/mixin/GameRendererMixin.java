package com.umollu.ash.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.umollu.ash.AshCommands;
import com.umollu.ash.config.AshConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Inject(at = @At(value = "INVOKE", target = "com/mojang/blaze3d/systems/RenderSystem.defaultAlphaFunc()V"), method = "render")
	public void render(float float_1, long long_1, boolean boolean_1, CallbackInfo info) {

		MinecraftClient client = MinecraftClient.getInstance();
		Entity cameraEntity = client.getCameraEntity();

		if(!client.options.debugEnabled && AshCommands.config.showHud) {
			RenderSystem.pushMatrix();
			String ashString = "";
			if(AshCommands.config.showFps) {
				ashString += String.format("%d fps ", ((MinecraftClientMixin) MinecraftClient.getInstance()).getCurrentFps());
			}
			if(AshCommands.config.showCoords) {
				BlockPos blockPos = new BlockPos(cameraEntity.getX(), cameraEntity.getBoundingBox().getMin(Direction.Axis.Y), cameraEntity.getZ());
				ashString += String.format("%d %d %d ", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			}
			if(AshCommands.config.showDirection) {
				Direction direction = cameraEntity.getHorizontalFacing();
				ashString += String.format("%5s ", direction);
			}

			float textPosX = 5;

			if (AshCommands.config.align == 1) {
				textPosX = (client.getWindow().getScaledWidth() - client.textRenderer.getStringWidth(ashString)) / 2f - textPosX;
			}
			if (AshCommands.config.align == 2) {
				textPosX = client.getWindow().getScaledWidth() - client.textRenderer.getStringWidth(ashString) - textPosX;
			}

			client.textRenderer.drawWithShadow(ashString, textPosX, 5, AshCommands.config.hudColor);
			RenderSystem.popMatrix();
		}
	}
}