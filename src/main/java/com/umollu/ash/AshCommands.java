package com.umollu.ash;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import net.minecraft.server.command.CommandSource;

public class AshCommands implements ClientCommandPlugin {
	@Override
	public void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(
			ArgumentBuilders.literal("toggleash")
				.executes(context -> {
					AshMod.config.showHud = !AshMod.config.showHud;
					AshMod.config.saveConfig();
					return 1;
				})
		);

		dispatcher.register(
			ArgumentBuilders.literal("togglefps")
				.executes(context -> {
					AshMod.config.showFps = !AshMod.config.showFps;
					AshMod.config.saveConfig();
					return 1;
				})
		);

		dispatcher.register(
			ArgumentBuilders.literal("togglecoords")
				.executes(context -> {
					AshMod.config.showCoords = !AshMod.config.showCoords;
					AshMod.config.saveConfig();
					return 1;
				})
		);

		dispatcher.register(
			ArgumentBuilders.literal("toggledirection")
				.executes(context -> {
					AshMod.config.showDirection = !AshMod.config.showDirection;
					AshMod.config.saveConfig();
					return 1;
				})
		);

		dispatcher.register(
			ArgumentBuilders.literal("ashcolor")
				.then(ArgumentBuilders.argument("r", IntegerArgumentType.integer())
					.then(ArgumentBuilders.argument("g", IntegerArgumentType.integer())
						.then(ArgumentBuilders.argument("b", IntegerArgumentType.integer())
							.executes(context -> {
								int r = IntegerArgumentType.getInteger(context,"r");
								int g = IntegerArgumentType.getInteger(context,"g");
								int b = IntegerArgumentType.getInteger(context,"b");

								AshMod.config.hudColor = b + (g << 8) + (r << 16);
								AshMod.config.saveConfig();
								return 1;
							}))))
		);

		dispatcher.register(
			ArgumentBuilders.literal("resetash")
				.executes(context -> {
					AshMod.config = new AshConfig();
					AshMod.config.saveConfig();
					return 1;
				})
		);

		dispatcher.register(
			ArgumentBuilders.literal("alignash")
				.then(ArgumentBuilders.literal("left")
					.executes(context -> {
						AshMod.config.align = 0;
						AshMod.config.saveConfig();
						return 1;
					}))
				.then(ArgumentBuilders.literal("center")
					.executes(context -> {
						AshMod.config.align = 1;
						AshMod.config.saveConfig();
						return 1;
					}))
				.then(ArgumentBuilders.literal("right")
					.executes(context -> {
						AshMod.config.align = 2;
						AshMod.config.saveConfig();
						return 1;
					}))
		);
	}
}
