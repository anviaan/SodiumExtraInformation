package net.anvian.sodiumextrainformation.mixin;

import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraHud;
import net.anvian.sodiumextrainformation.client.SodiumExtraInformationClientMod;
import net.anvian.sodiumextrainformation.options.SodiumExtraInformationGameOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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
        SodiumExtraInformationGameOptions options = SodiumExtraInformationClientMod.options();

        sodiumextrainformation$displayLocalTime(options);
        sodiumextrainformation$displayWorldTime(client, options);
        sodiumextrainformation$displaySessionTime(options);
        sodiumextrainformation$displayMemoryUsage(options);
        sodiumextrainformation$displayTotalEntityCount(client, options);
        sodiumextrainformation$displayRenderedEntities(client, options);
        sodiumextrainformation$displayBiome(client, options);
    }

    @Unique
    private void sodiumextrainformation$displayLocalTime(SodiumExtraInformationGameOptions options) {
        if (options.extraInformationSettings.localTimeConfig.showLocalTime) {
            LocalDateTime now = LocalDateTime.now();
            String timeFormat = options.extraInformationSettings.localTimeConfig.use12HourFormat
                    ? "hh:mm a"
                    : "HH:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
            String formattedNow = now.format(formatter);

            textList.add(Component.literal(formattedNow)
                    .withColor(options.extraInformationSettings.localTimeConfig.color.rgbToDecimal()));
        }
    }

    @Unique
    private void sodiumextrainformation$displayWorldTime(Minecraft client, SodiumExtraInformationGameOptions options) {
        if (!options.extraInformationSettings.wordTimeConfig.showWordTime || client.level == null) {
            return;
        }

        long worldTime = client.level.getDayTime();
        long currentDay = worldTime / 24000;
        textList.add(Component.translatable("sodium-extra-information.hud.word_time")
                .append(": ")
                .append(String.valueOf(currentDay))
                .withColor(options.extraInformationSettings.wordTimeConfig.color.rgbToDecimal()));
    }

    @Unique
    private void sodiumextrainformation$displaySessionTime(SodiumExtraInformationGameOptions options) {
        if (options.extraInformationSettings.sessionTimeConfig.showSessionTime) {
            long totalTimePlayed = SodiumExtraInformationClientMod.getTotalTimePlayed();
            int hours = (int) (totalTimePlayed / 3600);
            int minutes = (int) ((totalTimePlayed % 3600) / 60);
            int seconds = (int) (totalTimePlayed % 60);

            textList.add(Component.literal(hours + "h " + minutes + "m " + seconds + "s")
                    .withColor(options.extraInformationSettings.sessionTimeConfig.color.rgbToDecimal()));
        }
    }

    @Unique
    private void sodiumextrainformation$displayMemoryUsage(SodiumExtraInformationGameOptions options) {
        if (!options.extraInformationSettings.memoryUsageConfig.showMemoryUsage) {
            return;
        }

        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        int color = options.extraInformationSettings.memoryUsageConfig.color.rgbToDecimal();

        int memoryUsagePercent = (int) ((double) usedMemory / maxMemory * 100);
        textList.add(Component.literal(memoryUsagePercent + "%").withColor(color));

        if (options.extraInformationSettings.memoryUsageConfig.showMemoryUsageExtended) {
            long usedMemoryMB = usedMemory / (1024 * 1024);
            long maxMemoryMB = maxMemory / (1024 * 1024);
            textList.add(Component.literal(usedMemoryMB + "MB / " + maxMemoryMB + "MB").withColor(color));
        }
    }

    @Unique
    private void sodiumextrainformation$displayTotalEntityCount(Minecraft client, SodiumExtraInformationGameOptions options) {
        if (!options.extraInformationSettings.totalEntityCountConfig.showTotalEntityCount || client.level == null) {
            return;
        }

        textList.add(Component.translatable("sodium-extra-information.hud.show_total_entity_count")
                .append(": ")
                .append(String.valueOf(client.level.getEntityCount()))
                .withColor(options.extraInformationSettings.totalEntityCountConfig.color.rgbToDecimal()));
    }

    @Unique
    private void sodiumextrainformation$displayRenderedEntities(Minecraft client, SodiumExtraInformationGameOptions options) {
        if (!options.extraInformationSettings.renderedEntitiesConfig.showsRenderedEntities ||
                client.level == null) {
            return;
        }

        textList.add(Component.translatable("sodium-extra-information.hud.shows_rendered_entities")
                .append(": ")
                .append(String.valueOf(client.levelRenderer.visibleEntityCount))
                .withColor(options.extraInformationSettings.renderedEntitiesConfig.color.rgbToDecimal()));
    }

    @Unique
    private void sodiumextrainformation$displayBiome(Minecraft client, SodiumExtraInformationGameOptions options) {
        if (!options.extraInformationSettings.biomeConfig.showBiome ||
                client.level == null || client.player == null) {
            return;
        }

        ClientLevel level = client.level;
        BlockPos playerPos = client.player.blockPosition();
        Biome biome = level.getBiome(playerPos).value();
        String biomeName = level.registryAccess().lookupOrThrow(Registries.BIOME).getKey(biome).toString();

        MutableComponent txt = biomeName.startsWith("minecraft:")
                ? Component.translatable("biome.minecraft." + biomeName.substring("minecraft:".length()))
                : Component.literal(biomeName);

        textList.add(txt.withColor(options.extraInformationSettings.biomeConfig.color.rgbToDecimal()));
    }
}
