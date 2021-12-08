package com.umollu.ash;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.umollu.ash.config.AshConfig;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class AshCommands {

    public static AshConfig config;

        public static void registerCommands() {

        if(config == null) {
            config = AutoConfig.getConfigHolder(AshConfig.class).getConfig();
        }

        ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("ash")
        .then(ClientCommandManager.literal("color")
            .then(ClientCommandManager.argument("r", IntegerArgumentType.integer())
                .then(ClientCommandManager.argument("g", IntegerArgumentType.integer())
                    .then(ClientCommandManager.argument("b", IntegerArgumentType.integer())
                        .executes(context -> {
                            int r = IntegerArgumentType.getInteger(context,"r");
                            int g = IntegerArgumentType.getInteger(context,"g");
                            int b = IntegerArgumentType.getInteger(context,"b");
                            config.hudColor = b + (g << 8) + (r << 16);
                            AshMod.configManager.save();
                            return 1;
                        })))))
        .then(ClientCommandManager.literal("halign")
            .then(ClientCommandManager.literal("left")
                .executes(context -> {
                    config.align = 0;
                    AshMod.configManager.save();
                    return 1;
                }))
            .then(ClientCommandManager.literal("center")
                .executes(context -> {
                    config.align = 1;
                    AshMod.configManager.save();
                    return 1;
                }))
            .then(ClientCommandManager.literal("right")
                .executes(context -> {
                    config.align = 2;
                    AshMod.configManager.save();
                    return 1;
                })))
        .then(ClientCommandManager.literal("valign")
            .then(ClientCommandManager.literal("top")
                .executes(context -> {
                    config.verticalAlign = 0;
                    AshMod.configManager.save();
                    return 1;
                }))
            .then(ClientCommandManager.literal("middle")
                .executes(context -> {
                    config.verticalAlign = 1;
                    AshMod.configManager.save();
                    return 1;
                }))
            .then(ClientCommandManager.literal("bottom")
                .executes(context -> {
                    config.verticalAlign = 2;
                    AshMod.configManager.save();
                    return 1;
                })))
        .then(ClientCommandManager.literal("toggle")
            .then(ClientCommandManager.literal("enabled")
                .executes(context -> {
                    config.showHud = !config.showHud;
                    AshMod.configManager.save();
                    return 1;
                }))
            .then(ClientCommandManager.literal("coords")
                .executes(context -> {
                    config.showCoords = !config.showCoords;
                    msg("toggle.coords." + config.showCoords);
                    AshMod.configManager.save();
                    return 1;
                }))
            .then(ClientCommandManager.literal("diretion")
                .executes(context -> {
                    config.showDirection = !config.showDirection;
                    msg("toggle.direction." + config.showDirection);
                    AshMod.configManager.save();
                    return 1;
                }))
            .then(ClientCommandManager.literal("fps")
                .executes(context -> {
                    config.showFps = !config.showFps;
                    msg("toggle.fps." + config.showFps);
                    AshMod.configManager.save();
                    return 1;
                })))
        .then(ClientCommandManager.literal("reset")
            .executes(context -> {
                config = new AshConfig();
                msg("reset");
                AshMod.configManager.save();
                return 1;
            })));
    }

    private static void msg(String message) {
        MinecraftClient instance = MinecraftClient.getInstance();
        instance.inGameHud.getChatHud().addMessage(new TranslatableText("command.ash." + message).formatted(Formatting.GRAY));
    }
}