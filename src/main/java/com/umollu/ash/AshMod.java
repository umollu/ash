package com.umollu.ash;

import com.umollu.ash.config.AshConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigManager;
import me.sargunvohra.mcmods.autoconfig1u.gui.registry.GuiRegistry;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.util.Utils;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.ClientModInitializer;

import java.util.Collections;

public class AshMod implements ClientModInitializer {

    public static final String MOD_ID = "umollu_ash";
    public static ConfigManager configManager;

    @Override
    public void onInitializeClient() {
        configManager = (ConfigManager) AutoConfig.register(AshConfig.class, GsonConfigSerializer::new);
        GuiRegistry registry = AutoConfig.getGuiRegistry(AshConfig.class);
        registry.registerPredicateProvider((i13n, field, config, defaults, guiProvider) -> {

            ConfigEntryBuilder ENTRY_BUILDER = ConfigEntryBuilder.create();

            String[] ints = new String[3];

            ints[0] = "Left";
            ints[1] = "Center";
            ints[2] = "Right";

            return Collections.singletonList(ENTRY_BUILDER.startSelector(i13n, ints, alignToString((Integer) Utils.getUnsafely(field, config, (Object)null))).setDefaultValue(() -> {
                return alignToString(Utils.getUnsafely(field, defaults));
            }).setSaveConsumer((newValue) -> {
                int intValue = 0;
                if(newValue.equals("Center"))
                    intValue = 1;
                else if(newValue.equals("Right"))
                    intValue = 2;

                Utils.setUnsafely(field, config, intValue);
            }).build());
        }, (field) -> {
            return field.getName().equals("align");
        });
    }

    private String alignToString(int align) {
        switch (align) {
            case 0:
                return "Left";
            case 1:
                return "Center";
            case 2:
                return "Right";
            default:
                return "";
        }
    }
}