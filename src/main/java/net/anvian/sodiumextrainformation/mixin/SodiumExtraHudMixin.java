package net.anvian.sodiumextrainformation.mixin;

import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraHud;
import net.anvian.sodiumextrainformation.SodiumExtraInformationClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mixin(SodiumExtraHud.class)
public class SodiumExtraHudMixin {
    @Final
    @Shadow
    private List<Text> textList;

    @Inject(method = "onStartTick", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void inject(MinecraftClient client, CallbackInfo ci) {
        if (SodiumExtraInformationClient.options().extraInformationSettings.showLocalTime) {
            LocalDateTime now = LocalDateTime.now();

            String timeFormat = SodiumExtraInformationClient.options().extraInformationSettings.localTimeFormat;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
            String formattedNow = now.format(formatter);

            textList.add(Text.of(formattedNow));
        }

        if (SodiumExtraInformationClient.options().extraInformationSettings.showWordTime) {
            if (client.world != null) {
                long worldTime = client.world.getTimeOfDay();
                long currentDay = worldTime / 24000;
                textList.add(Text.of("Day: " + currentDay));
            }
        }
    }
}
