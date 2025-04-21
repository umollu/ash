package com.umollu.ash;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.umollu.ash.config.AshConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;

public class AshCommands {

    public static AshConfig config;

    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {


        if(config == null) {
            config = AutoConfig.getConfigHolder(AshConfig.class).getConfig();
        }
        dispatcher.register(ClientCommandManager.literal("toggleash")
                .executes(context -> {
                    config.showHud = !config.showHud;
                    ASHClient.configManager.save();
                    return 1;
                }));

        dispatcher.register(ClientCommandManager.literal("togglefps")
                .executes(context -> {
                    config.showFps = !config.showFps;
                    ASHClient.configManager.save();
                    return 1;
                }));

        dispatcher.register(ClientCommandManager.literal("togglecoords")
                .executes(context -> {
                    config.showCoords = !config.showCoords;
                    ASHClient.configManager.save();
                    return 1;
                }));

        dispatcher.register(ClientCommandManager.literal("toggledirection")
                .executes(context -> {
                    config.showDirection = !config.showDirection;
                    ASHClient.configManager.save();
                    return 1;
                }));

        dispatcher.register(ClientCommandManager.literal("ashcolor")
                .then(ClientCommandManager.argument("r", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("g", IntegerArgumentType.integer())
                                .then(ClientCommandManager.argument("b", IntegerArgumentType.integer())
                                        .executes(context -> {
                                            int r = IntegerArgumentType.getInteger(context,"r");
                                            int g = IntegerArgumentType.getInteger(context,"g");
                                            int b = IntegerArgumentType.getInteger(context,"b");

                                            config.hudColor = b + (g << 8) + (r << 16);
                                            ASHClient.configManager.save();
                                            return 1;
                                        })))));

        dispatcher.register(ClientCommandManager.literal("resetash")
                .executes(context -> {
                    config = new AshConfig();
                    ASHClient.configManager.save();
                    return 1;
                }));

        dispatcher.register(ClientCommandManager.literal("alignash")
                .then(ClientCommandManager.literal("left")
                        .executes(context -> {
                            config.align = 0;
                            ASHClient.configManager.save();
                            return 1;
                        }))
                .then(ClientCommandManager.literal("center")
                        .executes(context -> {
                            config.align = 1;
                            ASHClient.configManager.save();
                            return 1;
                        }))
                .then(ClientCommandManager.literal("right")
                        .executes(context -> {
                            config.align = 2;
                            ASHClient.configManager.save();
                            return 1;
                        })));

        dispatcher.register(ClientCommandManager.literal("valignash")
                .then(ClientCommandManager.literal("top")
                        .executes(context -> {
                            config.verticalAlign = 0;
                            ASHClient.configManager.save();
                            return 1;
                        }))
                .then(ClientCommandManager.literal("middle")
                        .executes(context -> {
                            config.verticalAlign = 1;
                            ASHClient.configManager.save();
                            return 1;
                        }))
                .then(ClientCommandManager.literal("bottom")
                        .executes(context -> {
                            config.verticalAlign = 2;
                            ASHClient.configManager.save();
                            return 1;
                        })));
    }
}