package net.anvian.sodiumextrainformation.mixin;

import net.anvian.sodiumextrainformation.client.SodiumExtraInformationClientMod;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class EndTickMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void onEndTick(CallbackInfo ci) {
        sodiumExtraInformation$handleClientTick((Minecraft) (Object) this);
    }

    @Unique
    private void sodiumExtraInformation$handleClientTick(Minecraft client) {
        if (client.level != null && !SodiumExtraInformationClientMod.SESSION_MANAGER.isInSession()) {
            SodiumExtraInformationClientMod.SESSION_MANAGER.startSession();
        } else if (client.level == null && SodiumExtraInformationClientMod.SESSION_MANAGER.isInSession()) {
            SodiumExtraInformationClientMod.SESSION_MANAGER.endSession();
            SodiumExtraInformationClientMod.SESSION_MANAGER.resetSession();
        }

        if (SodiumExtraInformationClientMod.SESSION_MANAGER.isInSession()) {
            if (client.isPaused()) {
                if (!SodiumExtraInformationClientMod.SESSION_MANAGER.isPaused()) {
                    SodiumExtraInformationClientMod.SESSION_MANAGER.pauseSession();
                }
            } else {
                if (SodiumExtraInformationClientMod.SESSION_MANAGER.isPaused()) {
                    SodiumExtraInformationClientMod.SESSION_MANAGER.resumeSession();
                } else {
                    SodiumExtraInformationClientMod.SESSION_MANAGER.updateSessionTime();
                }
            }
        }
    }
}
