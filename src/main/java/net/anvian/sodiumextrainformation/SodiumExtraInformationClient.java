package net.anvian.sodiumextrainformation;

import net.anvian.anvianslib.config.TelemetryConfigManager;
import net.anvian.anvianslib.util.LibUtil;
import net.anvian.sodiumextrainformation.options.SodiumExtraInformationGameOptions;
import net.anvian.sodiumextrainformation.util.SessionManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SodiumExtraInformationClient implements ClientModInitializer {
    public static final String MOD_ID = "sodiumextrainformation";
    public static final String MOD_VERSION = "2.5";
    public static final String MOD_NAME = "Sodium Extra Information";
    private static Logger LOGGER;

    private static SodiumExtraInformationGameOptions CONFIG;

    public static final SessionManager SESSION_MANAGER = new SessionManager();

    public static SodiumExtraInformationGameOptions options() {
        return CONFIG;
    }

    public static Logger logger() {
        if (LOGGER == null) {
            LOGGER = LoggerFactory.getLogger(MOD_NAME);
        }

        return LOGGER;
    }

    public static long getTotalTimePlayed() {
        return SESSION_MANAGER.getTotalTimePlayed();
    }

    @Override
    public void onInitializeClient() {
        LibUtil.generateConfigPath(MOD_ID, FabricLoader.getInstance().getConfigDir());

        TelemetryConfigManager.initialize(FabricLoader.getInstance().getConfigDir().resolve(MOD_ID).toFile());
        if (TelemetryConfigManager.getConfig().enableTelemetry) {
            TelemetryConfigManager.sendTelemetryData(
                    MOD_ID,
                    MOD_VERSION,
                    LibUtil.getMinecraftVersion(),
                    "Fabric",
                    !FabricLoader.getInstance().isDevelopmentEnvironment()
            );
        }

        CONFIG = SodiumExtraInformationGameOptions.load();
    }
}