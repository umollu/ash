package com.umollu.ash.config;

import com.umollu.ash.AshMod;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = AshMod.MOD_ID)
public class AshConfig implements ConfigData {
    public boolean showHud = true;

    public int hudColor = 0xeeeeee;

    public boolean showFps = true;

    public boolean showCoords = true;

    public boolean showDirection = true;

    public int align = 0;
}
