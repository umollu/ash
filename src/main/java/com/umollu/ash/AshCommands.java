package com.umollu.ash;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.umollu.ash.config.AshConfig;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

public class AshCommands implements ClientCommandPlugin {

    public static AshConfig config;

    @Override
        public void registerCommands(CommandDispatcher<CottonClientCommandSource> commandDispatcher) {

        if(config == null) {
            config = AutoConfig.getConfigHolder(AshConfig.class).getConfig();
        }
        commandDispatcher.register(ArgumentBuilders.literal("toggleash")
            .executes(context -> {
                config.showHud = !config.showHud;
                AshMod.configManager.save();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("togglefps")
            .executes(context -> {
                config.showFps = !config.showFps;
                AshMod.configManager.save();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("togglecoords")
            .executes(context -> {
                config.showCoords = !config.showCoords;
                AshMod.configManager.save();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("toggledirection")
            .executes(context -> {
                config.showDirection = !config.showDirection;
                AshMod.configManager.save();
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
                                        AshMod.configManager.save();
                                        return 1;
                                    })))));

        commandDispatcher.register(ArgumentBuilders.literal("resetash")
            .executes(context -> {
                config = new AshConfig();
                AshMod.configManager.save();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("alignash")
            .then(ArgumentBuilders.literal("left")
                    .executes(context -> {
                        config.align = 0;
                        AshMod.configManager.save();
                        return 1;
                    }))
            .then(ArgumentBuilders.literal("center")
                    .executes(context -> {
                        config.align = 1;
                        AshMod.configManager.save();
                        return 1;
                    }))
            .then(ArgumentBuilders.literal("right")
                    .executes(context -> {
                        config.align = 2;
                        AshMod.configManager.save();
                        return 1;
                    })));
    }
}
