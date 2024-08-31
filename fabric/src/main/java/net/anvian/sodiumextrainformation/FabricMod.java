package net.anvian.sodiumextrainformation;

import net.anvian.sodiumextrainformation.platform.FabricPlatformHelper;
import net.fabricmc.api.ClientModInitializer;

public class FabricMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Constants.LOG.info("Loading {} on {}", Constants.MOD_NAME, FabricPlatformHelper.INSTANCE.getPlatformName());
        CommonMod.init();
    }
}
