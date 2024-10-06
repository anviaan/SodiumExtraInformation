package net.anvian.sodiumextrainformation.client;

import net.fabricmc.api.ClientModInitializer;

public class SodiumExtraInformationClientFabricMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SodiumExtraInformationClientMod.onInitialization();
    }
}
