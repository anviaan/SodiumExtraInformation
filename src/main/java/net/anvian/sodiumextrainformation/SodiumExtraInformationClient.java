package net.anvian.sodiumextrainformation;

import net.anvian.sodiumextrainformation.options.SodiumExtraInformationGameOptions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SodiumExtraInformationClient implements ClientModInitializer {
    public static final String MOD_ID = "sodium-extra-information";
    public static final String MOD_NAME = "Sodium Extra Information";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static SodiumExtraInformationGameOptions CONFIG;

    private static long sessionStartTime;
    private static long totalTimePlayed;
    private static boolean inSession;
    private static boolean isPaused;

    public static SodiumExtraInformationGameOptions options() {
        if (CONFIG == null) {
            CONFIG = loadConfig();
        }

        return CONFIG;
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello from " + MOD_NAME + "!");

        sessionStartTime = 0;
        totalTimePlayed = 0;
        inSession = false;
        isPaused = false;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null && !inSession) {
                startSession();
            } else if (client.world == null && inSession) {
                endSession();
            }

            if (inSession) {
                if (client.isPaused()) {
                    if (!isPaused) {
                        pauseSession();
                    }
                } else {
                    if (isPaused) {
                        resumeSession();
                    } else {
                        long currentTime = System.currentTimeMillis();
                        totalTimePlayed += (currentTime - sessionStartTime);
                        sessionStartTime = currentTime;
                    }
                }
            }
        });
    }

    private void startSession() {
        sessionStartTime = System.currentTimeMillis();
        inSession = true;
        isPaused = false;
    }

    private void endSession() {
        inSession = false;
    }

    private void pauseSession() {
        isPaused = true;
        totalTimePlayed += (System.currentTimeMillis() - sessionStartTime);
    }

    private void resumeSession() {
        isPaused = false;
        sessionStartTime = System.currentTimeMillis();
    }

    public static long getTotalTimePlayed() {
        return totalTimePlayed / 1000;
    }

    private static SodiumExtraInformationGameOptions loadConfig() {
        return SodiumExtraInformationGameOptions.load(FabricLoader.getInstance().getConfigDir().resolve("sodium-extra-information-options.json").toFile());
    }
}