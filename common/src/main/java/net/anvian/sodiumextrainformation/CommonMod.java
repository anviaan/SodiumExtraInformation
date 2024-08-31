package net.anvian.sodiumextrainformation;

import net.anvian.sodiumextrainformation.options.SodiumExtraInformationGameOptions;
import net.anvian.sodiumextrainformation.util.SessionManager;

public class CommonMod {
    private static SodiumExtraInformationGameOptions CONFIG;
    public static final SessionManager SESSION_MANAGER = new SessionManager();

    public static SodiumExtraInformationGameOptions options() {
        return CONFIG;
    }

    public static void init() {
        Constants.LOG.info("Loading {}", Constants.MOD_NAME);
        CONFIG = SodiumExtraInformationGameOptions.load();
    }

    public static long getTotalTimePlayed() {
        return SESSION_MANAGER.getTotalTimePlayed();
    }
}