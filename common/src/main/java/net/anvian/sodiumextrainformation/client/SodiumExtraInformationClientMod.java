package net.anvian.sodiumextrainformation.client;

import net.anvian.anvianslib.util.LibUtil;
import net.anvian.sodiumextrainformation.options.SodiumExtraInformationGameOptions;
import net.anvian.sodiumextrainformation.util.SessionManager;
import net.caffeinemc.mods.sodium.client.services.PlatformRuntimeInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SodiumExtraInformationClientMod {
    public static final String MOD_ID = "sodiumextrainformation";
    public static final String MOD_VERSION = "2.6.3";
    private static final String MOD_NAME = "Sodium Extra Information";
    private static Logger LOGGER;

    private static SodiumExtraInformationGameOptions CONFIG;

    public static final SessionManager SESSION_MANAGER = new SessionManager();

    public static SodiumExtraInformationGameOptions options() {
        if (CONFIG == null) {
            CONFIG = loadConfig();
        }

        return CONFIG;
    }

    public static Logger logger() {
        if (LOGGER == null) {
            LOGGER = LoggerFactory.getLogger(MOD_NAME);
        }

        return LOGGER;
    }

    private static SodiumExtraInformationGameOptions loadConfig() {
        return SodiumExtraInformationGameOptions.load(PlatformRuntimeInformation.getInstance().getConfigDirectory().resolve(MOD_ID).resolve("sodium-extra-information-options.json").toFile());
    }


    public static long getTotalTimePlayed() {
        return SESSION_MANAGER.getTotalTimePlayed();
    }

    public static void onInitialization() {
        LibUtil.setupTelemetry(MOD_ID, MOD_VERSION);
    }
}
