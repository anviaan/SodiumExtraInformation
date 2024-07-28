package net.anvian.sodiumextrainformation;

import net.anvian.sodiumextrainformation.options.SodiumExtraInformationGameOptions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SodiumExtraInformationClient implements ClientModInitializer {
    public static final String MOD_ID = "sodium-extra-information";
    public static final String MOD_NAME = "Sodium Extra Information";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static SodiumExtraInformationGameOptions CONFIG;

    public static SodiumExtraInformationGameOptions options() {
        if (CONFIG == null) {
            CONFIG = loadConfig();
        }

        return CONFIG;
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello from " + MOD_NAME + "!");
    }

    private static SodiumExtraInformationGameOptions loadConfig() {
        return SodiumExtraInformationGameOptions.load(FabricLoader.getInstance().getConfigDir().resolve("sodium-extra-information-options.json").toFile());
    }
}