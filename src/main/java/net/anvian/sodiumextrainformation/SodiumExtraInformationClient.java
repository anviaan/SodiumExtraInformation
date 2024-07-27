package net.anvian.sodiumextrainformation;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SodiumExtraInformationClient implements ClientModInitializer {
    public static final String MOD_ID = "sodium-extra-information";
    public static final String MOD_NAME = "Sodium Extra Information";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello from " + MOD_NAME + "!");
    }
}