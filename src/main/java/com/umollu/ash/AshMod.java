package com.umollu.ash;

import com.google.gson.Gson;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class AshMod implements ClientModInitializer {

    public static final String MOD_ID = "umollu_ash";
    public static AshConfig config;

    @Override
    public void onInitializeClient() {
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
    }
}