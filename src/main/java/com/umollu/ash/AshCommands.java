package com.umollu.ash;

import com.google.gson.Gson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import static com.umollu.ash.AshMod.MOD_ID;

public class AshCommands implements ClientCommandPlugin {

    public static AshConfig config;

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> commandDispatcher) {

        String configPath = FabricLoader.getInstance().getConfigDirectory() + "/" + MOD_ID + ".json";

        Gson gson = new Gson();

        File configFile = new File(configPath);

        if(!configFile.exists()) {
            config = new AshConfig();
            String result = gson.toJson(config);
            try {
                FileOutputStream out = new FileOutputStream(configFile, false);

                out.write(result.getBytes());
                out.flush();
                out.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {

            try {
                config = gson.fromJson( new FileReader(configFile), AshConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                config = (config == null? new AshConfig() : config);
            }
        }


        commandDispatcher.register(ArgumentBuilders.literal("toggleash")
            .executes(context -> {
                config.showHud = !config.showHud;
                config.saveConfig();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("togglefps")
            .executes(context -> {
                config.showFps = !config.showFps;
                config.saveConfig();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("togglecoords")
            .executes(context -> {
                config.showCoords = !config.showCoords;
                config.saveConfig();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("toggledirection")
            .executes(context -> {
                config.showDirection = !config.showDirection;
                config.saveConfig();
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
                                        config.saveConfig();
                                        return 1;
                                    })))));

        commandDispatcher.register(ArgumentBuilders.literal("resetash")
            .executes(context -> {
                config = new AshConfig();
                config.saveConfig();
                return 1;
            }));

        commandDispatcher.register(ArgumentBuilders.literal("alignash")
            .then(ArgumentBuilders.literal("left")
                    .executes(context -> {
                        config.align = 0;
                        config.saveConfig();
                        return 1;
                    }))
            .then(ArgumentBuilders.literal("center")
                    .executes(context -> {
                        config.align = 1;
                        config.saveConfig();
                        return 1;
                    }))
            .then(ArgumentBuilders.literal("right")
                    .executes(context -> {
                        config.align = 2;
                        config.saveConfig();
                        return 1;
                    })));
    }
}
