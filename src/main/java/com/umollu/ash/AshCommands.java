package com.umollu.ash;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.umollu.ash.config.AshConfig;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigManager;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;

public class AshCommands implements ClientCommandPlugin {

    public static AshConfig config;
    private ConfigManager configManager;

    @Override
        public void registerCommands(CommandDispatcher<CottonClientCommandSource> commandDispatcher) {

        if(config == null) {
            configManager = (ConfigManager)AutoConfig.register(AshConfig.class, GsonConfigSerializer::new);
            config = AutoConfig.getConfigHolder(AshConfig.class).getConfig();
        }
        commandDispatcher.register(ArgumentBuilders.literal("toggleash")
            .executes(context -> {
                config.showHud = !config.showHud;
                configManager.save();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("togglefps")
            .executes(context -> {
                config.showFps = !config.showFps;
                configManager.save();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("togglecoords")
            .executes(context -> {
                config.showCoords = !config.showCoords;
                configManager.save();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("toggledirection")
            .executes(context -> {
                config.showDirection = !config.showDirection;
                configManager.save();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("ashcolor")
            .then(ArgumentBuilders.argument("r", IntegerArgumentType.integer())
                    .then(ArgumentBuilders.argument("g", IntegerArgumentType.integer())
                            .then(ArgumentBuilders.argument("b", IntegerArgumentType.integer())
                                    .executes(context -> {
                                        int r = IntegerArgumentType.getInteger(context,"r");
                                        int g = IntegerArgumentType.getInteger(context,"g");
                                        int b = IntegerArgumentType.getInteger(context,"b");

                                        config.hudColor = b + (g << 8) + (r << 16);
                                        configManager.save();
                                        return 1;
                                    })))));

        commandDispatcher.register(ArgumentBuilders.literal("resetash")
            .executes(context -> {
                config = new AshConfig();
                configManager.save();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("alignash")
            .then(ArgumentBuilders.literal("left")
                    .executes(context -> {
                        config.align = 0;
                        configManager.save();
                        return 1;
                    }))
            .then(ArgumentBuilders.literal("center")
                    .executes(context -> {
                        config.align = 1;
                        configManager.save();
                        return 1;
                    }))
            .then(ArgumentBuilders.literal("right")
                    .executes(context -> {
                        config.align = 2;
                        configManager.save();
                        return 1;
                    })));
    }
}
