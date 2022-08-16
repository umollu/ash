package com.umollu.ash;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.umollu.ash.config.AshConfig;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class AshCommands {

	public static AshConfig config;

	public static void registerCommands() {

		if (config == null) {
			config = AutoConfig.getConfigHolder(AshConfig.class).getConfig();
		}

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("toggleash").executes(context -> {
				config.showHud = !config.showHud;
				AshMod.configManager.save();
				return 1;
			}));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("togglefps").executes(context -> {
				config.showFps = !config.showFps;
				AshMod.configManager.save();
				return 1;
			}));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("togglecoords").executes(context -> {
				config.showCoords = !config.showCoords;
				AshMod.configManager.save();
				return 1;
			}));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("toggledirection").executes(context -> {
				config.showDirection = !config.showDirection;
				AshMod.configManager.save();
				return 1;
			}));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("ashcolor")
					.then(ClientCommandManager.argument("r", IntegerArgumentType.integer())
					.then(ClientCommandManager.argument("g", IntegerArgumentType.integer())
					.then(ClientCommandManager.argument("b", IntegerArgumentType.integer())
					.executes(context -> {
						int r = IntegerArgumentType.getInteger(context, "r");
						int g = IntegerArgumentType.getInteger(context, "g");
						int b = IntegerArgumentType.getInteger(context, "b");
						
						config.hudColor = b + (g << 8) + (r << 16);
						AshMod.configManager.save();
						return 1;
			})))));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("resetash").executes(context -> {
				config = new AshConfig();
				AshMod.configManager.save();
				return 1;
			}));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("alignash")
					.then(ClientCommandManager.literal("left").executes(context -> {
						config.align = 0;
						AshMod.configManager.save();
						return 1;
					})).then(ClientCommandManager.literal("center").executes(context -> {
						config.align = 1;
						AshMod.configManager.save();
						return 1;
					})).then(ClientCommandManager.literal("right").executes(context -> {
						config.align = 2;
						AshMod.configManager.save();
						return 1;
					})));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("valignash")
					.then(ClientCommandManager.literal("top").executes(context -> {
						config.verticalAlign = 0;
						AshMod.configManager.save();
						return 1;
					})).then(ClientCommandManager.literal("middle").executes(context -> {
						config.verticalAlign = 1;
						AshMod.configManager.save();
						return 1;
					})).then(ClientCommandManager.literal("bottom").executes(context -> {
						config.verticalAlign = 2;
						AshMod.configManager.save();
						return 1;
					})));
		});
	}
}