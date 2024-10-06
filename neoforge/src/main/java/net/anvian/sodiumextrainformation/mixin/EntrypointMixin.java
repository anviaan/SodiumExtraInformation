package net.anvian.sodiumextrainformation.mixin;

import net.anvian.sodiumextrainformation.client.SodiumExtraInformationClientMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class EntrypointMixin {
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;loadSelectedResourcePacks(Lnet/minecraft/server/packs/repository/PackRepository;)V"))
    private void sodium$loadConfig(GameConfig gameConfig, CallbackInfo ci) {
        SodiumExtraInformationClientMod.onInitialization();
    }
}