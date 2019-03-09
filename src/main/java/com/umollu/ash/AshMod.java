package com.umollu.ash;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.ServerCommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.TextFormat;
import net.minecraft.text.TranslatableTextComponent;

public class AshMod implements ModInitializer {

    public static final String MOD_ID = "umollu_ash";
    public static boolean showHUD = true;

    @Override
    public void onInitialize() {
        CommandRegistry.INSTANCE.register(false, serverCommandSourceCommandDispatcher -> serverCommandSourceCommandDispatcher.register(
                ServerCommandManager.literal("toggleash")
                        .executes(context -> {
                            showHUD = !showHUD;
                            return 1;
                        })
        ));
    }
}