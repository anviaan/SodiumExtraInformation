package net.anvian.sodiumextrainformation.mixin;

import net.anvian.sodiumextrainformation.SodiumExtraInformationClient;
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
        if (client.level != null && !SodiumExtraInformationClient.SESSION_MANAGER.isInSession()) {
            SodiumExtraInformationClient.SESSION_MANAGER.startSession();
        } else if (client.level == null && SodiumExtraInformationClient.SESSION_MANAGER.isInSession()) {
            SodiumExtraInformationClient.SESSION_MANAGER.endSession();
            SodiumExtraInformationClient.SESSION_MANAGER.resetSession();
        }

        if (SodiumExtraInformationClient.SESSION_MANAGER.isInSession()) {
            if (client.isPaused()) {
                if (!SodiumExtraInformationClient.SESSION_MANAGER.isPaused()) {
                    SodiumExtraInformationClient.SESSION_MANAGER.pauseSession();
                }
            } else {
                if (SodiumExtraInformationClient.SESSION_MANAGER.isPaused()) {
                    SodiumExtraInformationClient.SESSION_MANAGER.resumeSession();
                } else {
                    SodiumExtraInformationClient.SESSION_MANAGER.updateSessionTime();
                }
            }
        }
    }
}
