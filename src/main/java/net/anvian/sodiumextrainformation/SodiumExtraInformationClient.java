package net.anvian.sodiumextrainformation;

import net.anvian.sodiumextrainformation.options.SodiumExtraInformationGameOptions;
import net.anvian.sodiumextrainformation.util.SessionManager;
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
    public static final SessionManager SESSION_MANAGER = new SessionManager();

    public static SodiumExtraInformationGameOptions options() {
        if (CONFIG == null) {
            CONFIG = loadConfig();
        }
        return CONFIG;
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello from {}!", MOD_NAME);

        SESSION_MANAGER.resetSession();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            try {
                handleClientTick(client);
            } catch (Exception e) {
                LOGGER.error("Error during client tick", e);
            }
        });
    }

    private void handleClientTick(MinecraftClient client) {
        if (client.world != null && !SESSION_MANAGER.isInSession()) {
            SESSION_MANAGER.startSession();
        } else if (client.world == null && SESSION_MANAGER.isInSession()) {
            SESSION_MANAGER.endSession();
        }

        if (SESSION_MANAGER.isInSession()) {
            if (client.isPaused()) {
                if (!SESSION_MANAGER.isPaused()) {
                    SESSION_MANAGER.pauseSession();
                }
            } else {
                if (SESSION_MANAGER.isPaused()) {
                    SESSION_MANAGER.resumeSession();
                } else {
                    SESSION_MANAGER.updateSessionTime();
                }
            }
        }
    }

    public static long getTotalTimePlayed() {
        return SESSION_MANAGER.getTotalTimePlayed();
    }

    private static SodiumExtraInformationGameOptions loadConfig() {
        return SodiumExtraInformationGameOptions.load(FabricLoader.getInstance().getConfigDir().resolve("sodium-extra-information-options.json").toFile());
    }
}