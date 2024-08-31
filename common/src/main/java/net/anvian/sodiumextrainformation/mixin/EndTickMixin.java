package net.anvian.sodiumextrainformation.mixin;

import net.anvian.sodiumextrainformation.CommonMod;
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
        if (client.level != null && !CommonMod.SESSION_MANAGER.isInSession()) {
            CommonMod.SESSION_MANAGER.startSession();
        } else if (client.level == null && CommonMod.SESSION_MANAGER.isInSession()) {
            CommonMod.SESSION_MANAGER.endSession();
            CommonMod.SESSION_MANAGER.resetSession();
        }

        if (CommonMod.SESSION_MANAGER.isInSession()) {
            if (client.isPaused()) {
                if (!CommonMod.SESSION_MANAGER.isPaused()) {
                    CommonMod.SESSION_MANAGER.pauseSession();
                }
            } else {
                if (CommonMod.SESSION_MANAGER.isPaused()) {
                    CommonMod.SESSION_MANAGER.resumeSession();
                } else {
                    CommonMod.SESSION_MANAGER.updateSessionTime();
                }
            }
        }
    }
}
