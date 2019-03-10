package com.umollu.ash.mixin;

import com.umollu.ash.AshMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.BlockPos;

import com.mojang.blaze3d.platform.GlStateManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;alphaFunc(IF)V"), method = "render")
	public void render(float float_1, long long_1, boolean boolean_1, CallbackInfo info) {

		MinecraftClient client = MinecraftClient.getInstance();
		if(!client.options.debugEnabled && AshMod.config.showHud) {
			BlockPos blockPos = new BlockPos(client.getCameraEntity().x, client.getCameraEntity().getBoundingBox().minY, client.getCameraEntity().z);
			double scaleFactor = client.window.getScaleFactor();
			GlStateManager.pushMatrix();
			GlStateManager.scaled(1 * scaleFactor, 1 * scaleFactor, 1 * scaleFactor);
			client.textRenderer.drawWithShadow(String.format("%d fps %d %d %d", MinecraftClient.getCurrentFps(), blockPos.getX(), blockPos.getY(), blockPos.getZ()), 5, 5, AshMod.config.hudColor);
			GlStateManager.popMatrix();
		}
	}
}