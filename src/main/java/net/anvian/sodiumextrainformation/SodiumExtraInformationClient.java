package net.anvian.sodiumextrainformation;

import net.anvian.sodiumextrainformation.options.SodiumExtraInformationGameOptions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
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

    public static SodiumExtraInformationGameOptions options() {
        if (CONFIG == null) {
            CONFIG = loadConfig();
        }

        return CONFIG;
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello from " + MOD_NAME + "!");

        sessionStartTime = System.currentTimeMillis();
        totalTimePlayed = 0;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (MinecraftClient.getInstance().world != null && !inSession) {
                startSession();
            } else if (MinecraftClient.getInstance().world == null && inSession) {
                endSession();
            }

            if (inSession) {
                long currentTime = System.currentTimeMillis();
                totalTimePlayed += (currentTime - sessionStartTime);
                sessionStartTime = currentTime;
            }
        });
    }

    private void startSession() {
        sessionStartTime = System.currentTimeMillis();
        inSession = true;
    }

    private void endSession() {
        inSession = false;
    }

    public static long getTotalTimePlayed() {
        return totalTimePlayed / 1000;
    }

    private static SodiumExtraInformationGameOptions loadConfig() {
        return SodiumExtraInformationGameOptions.load(FabricLoader.getInstance().getConfigDir().resolve("sodium-extra-information-options.json").toFile());
    }
}