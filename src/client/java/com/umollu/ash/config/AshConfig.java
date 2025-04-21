package com.umollu.ash.config;

import com.umollu.ash.ASHClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = ASHClient.MOD_ID)
public class AshConfig implements ConfigData {
    public boolean showHud = true;

    @ConfigEntry.ColorPicker
    public int hudColor = 0xeeeeee;

    public boolean showFps = true;

    public boolean showCoords = true;

    public boolean showDirection = true;

    public int align = 0;

    public int verticalAlign = 0;
}