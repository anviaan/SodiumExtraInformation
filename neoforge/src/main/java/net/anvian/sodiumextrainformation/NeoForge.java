package net.anvian.sodiumextrainformation;

import net.anvian.sodiumextrainformation.platform.NeoForgePlatformHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class NeoForge {
    public NeoForge(IEventBus eventBus) {
        Constants.LOG.info("Loading {} on {}", Constants.MOD_NAME, NeoForgePlatformHelper.INSTANCE.getPlatformName());
        CommonMod.init();
    }
}