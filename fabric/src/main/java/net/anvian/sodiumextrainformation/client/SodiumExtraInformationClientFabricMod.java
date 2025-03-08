package net.anvian.sodiumextrainformation.client;

import net.anvian.anvianslib.config.TelemetryConfigManager;
import net.anvian.anvianslib.util.LibUtil;
import net.caffeinemc.mods.sodium.client.services.PlatformRuntimeInformation;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class SodiumExtraInformationClientFabricMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LibUtil.generateConfigPath(SodiumExtraInformationClientMod.MOD_ID, PlatformRuntimeInformation.getInstance().getConfigDirectory());

        TelemetryConfigManager.initialize(PlatformRuntimeInformation.getInstance().getConfigDirectory().resolve(SodiumExtraInformationClientMod.MOD_ID).toFile());
        if (TelemetryConfigManager.getConfig().enableTelemetry){
            TelemetryConfigManager.sendTelemetryData(
                    SodiumExtraInformationClientMod.MOD_ID,
                    SodiumExtraInformationClientMod.MOD_VERSION,
                    LibUtil.getMinecraftVersion(),
                    "Fabric",
                    !FabricLoader.getInstance().isDevelopmentEnvironment()
            );
        }

        SodiumExtraInformationClientMod.onInitialization();
    }
}
