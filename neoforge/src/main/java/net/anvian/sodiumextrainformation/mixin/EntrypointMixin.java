package net.anvian.sodiumextrainformation.mixin;

import net.anvian.anvianslib.config.TelemetryConfigManager;
import net.anvian.anvianslib.util.LibUtil;
import net.anvian.sodiumextrainformation.client.SodiumExtraInformationClientMod;
import net.caffeinemc.mods.sodium.client.services.PlatformRuntimeInformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.neoforged.fml.loading.FMLLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class EntrypointMixin {
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;loadSelectedResourcePacks(Lnet/minecraft/server/packs/repository/PackRepository;)V"))
    private void sodium$loadConfig(GameConfig gameConfig, CallbackInfo ci) {
        LibUtil.generateConfigPath(SodiumExtraInformationClientMod.MOD_ID, PlatformRuntimeInformation.getInstance().getConfigDirectory());

        TelemetryConfigManager.initialize(PlatformRuntimeInformation.getInstance().getConfigDirectory().resolve(SodiumExtraInformationClientMod.MOD_ID).toFile());
        if (TelemetryConfigManager.getConfig().enableTelemetry){
            TelemetryConfigManager.sendTelemetryData(
                    SodiumExtraInformationClientMod.MOD_ID,
                    SodiumExtraInformationClientMod.MOD_VERSION,
                    LibUtil.getMinecraftVersion(),
                    "NeoForge",
                    FMLLoader.isProduction()
            );
        }

        SodiumExtraInformationClientMod.onInitialization();
    }
}