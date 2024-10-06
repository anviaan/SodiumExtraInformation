package net.anvian.sodiumextrainformation.mixin;

import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraHud;
import net.anvian.sodiumextrainformation.client.SodiumExtraInformationClientMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mixin(SodiumExtraHud.class)
public class SodiumExtraHudMixin {
    @Final
    @Shadow
    private List<Component> textList;

    @Inject(method = "onStartTick", at = @At("RETURN"))
    private void inject(Minecraft client, CallbackInfo ci) {
        if (SodiumExtraInformationClientMod.options().extraInformationSettings.showLocalTime) {
            LocalDateTime now = LocalDateTime.now();

            String timeFormat = SodiumExtraInformationClientMod.options().extraInformationSettings.localTimeFormat;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
            String formattedNow = now.format(formatter);

            textList.add(Component.nullToEmpty(formattedNow));
        }

        if (SodiumExtraInformationClientMod.options().extraInformationSettings.showWordTime) {
            if (client.level != null) {
                long worldTime = client.level.getDayTime();
                long currentDay = worldTime / 24000;
                textList.add(Component.translatable("sodium-extra-information.hud.word_time").append(": ").append(String.valueOf(currentDay)));
            }
        }

        if (SodiumExtraInformationClientMod.options().extraInformationSettings.showSessionTime) {
            long totalTimePlayed = SodiumExtraInformationClientMod.getTotalTimePlayed();
            int hours = (int) (totalTimePlayed / 3600);
            int minutes = (int) ((totalTimePlayed % 3600) / 60);
            int seconds = (int) (totalTimePlayed % 60);

            textList.add(Component.nullToEmpty(hours + "h " + minutes + "m " + seconds + "s"));
        }

        if (SodiumExtraInformationClientMod.options().extraInformationSettings.showMemoryUsage) {
            Runtime runtime = Runtime.getRuntime();
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            long maxMemory = runtime.maxMemory();

            int memoryUsagePercent = (int) ((double) usedMemory / maxMemory * 100);
            textList.add(Component.nullToEmpty(memoryUsagePercent + "%"));

            if (SodiumExtraInformationClientMod.options().extraInformationSettings.showMemoryUsageExtended) {
                long usedMemoryMB = usedMemory / (1024 * 1024);
                long maxMemoryMB = maxMemory / (1024 * 1024);
                textList.add(Component.nullToEmpty(usedMemoryMB + "MB / " + maxMemoryMB + "MB"));
            }
        }

        if (SodiumExtraInformationClientMod.options().extraInformationSettings.showTotalEntityCount) {
            if (client.level != null) {
                textList.add(Component.translatable("sodium-extra-information.hud.show_total_entity_count").append(": ").append(String.valueOf(client.level.getEntityCount())));
            }
        }

        if (SodiumExtraInformationClientMod.options().extraInformationSettings.showsRenderedEntities) {
            if (client.level != null) {
                textList.add(Component.translatable("sodium-extra-information.hud.shows_rendered_entities").append(": ").append(String.valueOf(client.levelRenderer.renderedEntities)));
            }
        }
    }
}
