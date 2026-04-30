package net.anvian.sodiumextrainformation.mixin;

import me.flashyreese.mods.sodiumextra.client.config.SodiumExtraConfig;
import net.anvian.sodiumextrainformation.client.SodiumExtraInformationClientMod;
import net.anvian.sodiumextrainformation.options.SodiumExtraInformationGameOptions;
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

    @Unique
    private static final SodiumExtraInformationGameOptions OPTION = SodiumExtraInformationClientMod.options();

    @Inject(method = "createExtraPage", at = @At("RETURN"))
    private void injectCreateExtraPage(ConfigBuilder builder, CallbackInfoReturnable<OptionPageBuilder> cir) {
        OptionPageBuilder pageBuilder = cir.getReturnValue();

        pageBuilder.addOptionGroup(builder.createOptionGroup()
                .setName(Component.translatable("sodium-extra-information.options.name"))
                .addOption(builder.createBooleanOption(id("show_local_time"))
                        .setName(Component.translatable("sodium-extra-information.options.local_time"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.local_time.tooltip"))
                        .setStorageHandler(OPTION::writeChanges)
                        .setBinding(value -> OPTION.extraInformationSettings.localTimeConfig.showLocalTime = value, () -> OPTION.extraInformationSettings.localTimeConfig.showLocalTime)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_local_time_12h"))
                        .setName(Component.translatable("sodium-extra-information.options.local_time_12h"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.local_time_12h.tooltip"))
                        .setStorageHandler(OPTION::writeChanges)
                        .setBinding(value -> OPTION.extraInformationSettings.localTimeConfig.use12HourFormat = value, () -> OPTION.extraInformationSettings.localTimeConfig.use12HourFormat)
                        .setDefaultValue(false))
                .addOption(builder.createBooleanOption(id("show_word_time"))
                        .setName(Component.translatable("sodium-extra-information.options.word_time"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.word_time.tooltip"))
                        .setStorageHandler(OPTION::writeChanges)
                        .setBinding(value -> OPTION.extraInformationSettings.wordTimeConfig.showWordTime = value, () -> OPTION.extraInformationSettings.wordTimeConfig.showWordTime)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_session_time"))
                        .setName(Component.translatable("sodium-extra-information.options.session_time"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.session_time.tooltip"))
                        .setStorageHandler(OPTION::writeChanges)
                        .setBinding(value -> OPTION.extraInformationSettings.sessionTimeConfig.showSessionTime = value, () -> OPTION.extraInformationSettings.sessionTimeConfig.showSessionTime)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_memory_usage"))
                        .setName(Component.translatable("sodium-extra-information.options.memory_usage"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.memory_usage.tooltip"))
                        .setStorageHandler(OPTION::writeChanges)
                        .setBinding(value -> OPTION.extraInformationSettings.memoryUsageConfig.showMemoryUsage = value, () -> OPTION.extraInformationSettings.memoryUsageConfig.showMemoryUsage)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_memory_usage_extended"))
                        .setName(Component.translatable("sodium-extra-information.options.memory_usage_extended"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.memory_usage_extended.tooltip"))
                        .setStorageHandler(OPTION::writeChanges)
                        .setBinding(value -> OPTION.extraInformationSettings.memoryUsageConfig.showMemoryUsageExtended = value, () -> OPTION.extraInformationSettings.memoryUsageConfig.showMemoryUsageExtended)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_total_entity_count"))
                        .setName(Component.translatable("sodium-extra-information.options.show_total_entity_count"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.show_total_entity_count.tooltip"))
                        .setStorageHandler(OPTION::writeChanges)
                        .setBinding(value -> OPTION.extraInformationSettings.totalEntityCountConfig.showTotalEntityCount = value, () -> OPTION.extraInformationSettings.totalEntityCountConfig.showTotalEntityCount)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("shows_rendered_entities"))
                        .setName(Component.translatable("sodium-extra-information.options.shows_rendered_entities"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.shows_rendered_entities.tooltip"))
                        .setStorageHandler(OPTION::writeChanges)
                        .setBinding(value -> OPTION.extraInformationSettings.renderedEntitiesConfig.showsRenderedEntities = value, () -> OPTION.extraInformationSettings.renderedEntitiesConfig.showsRenderedEntities)
                        .setDefaultValue(true))
                .addOption(builder.createBooleanOption(id("show_biome"))
                        .setName(Component.translatable("sodium-extra-information.options.show_biome"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.show_biome.tooltip"))
                        .setStorageHandler(OPTION::writeChanges)
                        .setBinding(value -> OPTION.extraInformationSettings.biomeConfig.showBiome = value, () -> OPTION.extraInformationSettings.biomeConfig.showBiome)
                        .setDefaultValue(true)));
    }
}
