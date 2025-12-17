package net.anvian.sodiumextrainformation.mixin;

import me.flashyreese.mods.sodiumextra.client.SodiumExtraClientMod;
import me.flashyreese.mods.sodiumextra.client.config.SodiumExtraConfig;
import net.anvian.sodiumextrainformation.client.SodiumExtraInformationClientMod;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionPageBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SodiumExtraConfig.class, remap = false)
public abstract class SodiumExtraConfigMixin {
    @Unique
    private static Identifier id(String path) {
        return Identifier.parse("sodium-extra:" + path);
    }

    @Inject(method = "createExtraPage", at = @At("RETURN"))
    private void injectCreateExtraPage(ConfigBuilder builder, CallbackInfoReturnable<OptionPageBuilder> cir) {
        OptionPageBuilder pageBuilder = cir.getReturnValue();

        pageBuilder.addOptionGroup(builder.createOptionGroup()
                .addOption(builder.createBooleanOption(id("show_local_time"))
                        .setName(Component.translatable("sodium-extra-information.options.local_time"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.local_time.tooltip"))
                        .setStorageHandler(SodiumExtraClientMod.options())
                        .setBinding(value -> SodiumExtraInformationClientMod.options().extraInformationSettings.localTimeConfig.showLocalTime = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.localTimeConfig.showLocalTime)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_word_time"))
                        .setName(Component.translatable("sodium-extra-information.options.word_time"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.word_time.tooltip"))
                        .setStorageHandler(SodiumExtraClientMod.options())
                        .setBinding(value -> SodiumExtraInformationClientMod.options().extraInformationSettings.wordTimeConfig.showWordTime = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.wordTimeConfig.showWordTime)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_session_time"))
                        .setName(Component.translatable("sodium-extra-information.options.session_time"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.session_time.tooltip"))
                        .setStorageHandler(SodiumExtraClientMod.options())
                        .setBinding(value -> SodiumExtraInformationClientMod.options().extraInformationSettings.sessionTimeConfig.showSessionTime = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.sessionTimeConfig.showSessionTime)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_memory_usage"))
                        .setName(Component.translatable("sodium-extra-information.options.memory_usage"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.memory_usage.tooltip"))
                        .setStorageHandler(SodiumExtraClientMod.options())
                        .setBinding(value -> SodiumExtraInformationClientMod.options().extraInformationSettings.memoryUsageConfig.showMemoryUsage = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.memoryUsageConfig.showMemoryUsage)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_memory_usage_extended"))
                        .setName(Component.translatable("sodium-extra-information.options.memory_usage_extended"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.memory_usage_extended.tooltip"))
                        .setStorageHandler(SodiumExtraClientMod.options())
                        .setBinding(value -> SodiumExtraInformationClientMod.options().extraInformationSettings.memoryUsageConfig.showMemoryUsageExtended = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.memoryUsageConfig.showMemoryUsageExtended)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_total_entity_count"))
                        .setName(Component.translatable("sodium-extra-information.options.show_total_entity_count"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.show_total_entity_count.tooltip"))
                        .setStorageHandler(SodiumExtraClientMod.options())
                        .setBinding(value -> SodiumExtraInformationClientMod.options().extraInformationSettings.totalEntityCountConfig.showTotalEntityCount = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.totalEntityCountConfig.showTotalEntityCount)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("shows_rendered_entities"))
                        .setName(Component.translatable("sodium-extra-information.options.shows_rendered_entities"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.shows_rendered_entities.tooltip"))
                        .setStorageHandler(SodiumExtraClientMod.options())
                        .setBinding(value -> SodiumExtraInformationClientMod.options().extraInformationSettings.renderedEntitiesConfig.showsRenderedEntities = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.renderedEntitiesConfig.showsRenderedEntities)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_biome"))
                        .setName(Component.translatable("sodium-extra-information.options.show_biome"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.show_biome.tooltip"))
                        .setStorageHandler(SodiumExtraClientMod.options())
                        .setBinding(value -> SodiumExtraInformationClientMod.options().extraInformationSettings.biomeConfig.showBiome = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.biomeConfig.showBiome)
                        .setDefaultValue(true)));
    }
}
