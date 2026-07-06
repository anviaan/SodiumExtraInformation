package net.anvian.sodiumextrainformation.client;

import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.structure.BooleanOptionBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.ModOptionsBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionGroupBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionPageBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SodiumExtraInformationConfig implements ConfigEntryPoint {

    @Override
    public void registerConfigLate(ConfigBuilder configBuilder) {
        ModOptionsBuilder modOptions = configBuilder.registerOwnModOptions();

        OptionPageBuilder page = configBuilder.createOptionPage();
        page.setName(Component.translatable("sodium-extra.option.extras"));

        OptionGroupBuilder group = configBuilder.createOptionGroup();

        group.addOption(booleanOption(configBuilder, "local_time", Component.translatable("sodium-extra-information.options.local_time"), Component.translatable("sodium-extra-information.options.local_time.tooltip"), value -> SodiumExtraInformationClientMod.options().extraInformationSettings.localTimeConfig.showLocalTime = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.localTimeConfig.showLocalTime));

        group.addOption(booleanOption(configBuilder, "local_time_12h", Component.translatable("sodium-extra-information.options.local_time_12h"), Component.translatable("sodium-extra-information.options.local_time_12h.tooltip"), value -> SodiumExtraInformationClientMod.options().extraInformationSettings.localTimeConfig.use12HourFormat = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.localTimeConfig.use12HourFormat));

        group.addOption(booleanOption(configBuilder, "word_time", Component.translatable("sodium-extra-information.options.word_time"), Component.translatable("sodium-extra-information.options.word_time.tooltip"), value -> SodiumExtraInformationClientMod.options().extraInformationSettings.wordTimeConfig.showWordTime = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.wordTimeConfig.showWordTime));

        group.addOption(booleanOption(configBuilder, "session_time", Component.translatable("sodium-extra-information.options.session_time"), Component.translatable("sodium-extra-information.options.session_time.tooltip"), value -> SodiumExtraInformationClientMod.options().extraInformationSettings.sessionTimeConfig.showSessionTime = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.sessionTimeConfig.showSessionTime));

        group.addOption(booleanOption(configBuilder, "memory_usage", Component.translatable("sodium-extra-information.options.memory_usage"), Component.translatable("sodium-extra-information.options.memory_usage.tooltip"), value -> SodiumExtraInformationClientMod.options().extraInformationSettings.memoryUsageConfig.showMemoryUsage = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.memoryUsageConfig.showMemoryUsage));

        group.addOption(booleanOption(configBuilder, "memory_usage_extended", Component.translatable("sodium-extra-information.options.memory_usage_extended"), Component.translatable("sodium-extra-information.options.memory_usage_extended.tooltip"), value -> SodiumExtraInformationClientMod.options().extraInformationSettings.memoryUsageConfig.showMemoryUsageExtended = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.memoryUsageConfig.showMemoryUsageExtended));

        group.addOption(booleanOption(configBuilder, "show_total_entity_count", Component.translatable("sodium-extra-information.options.show_total_entity_count"), Component.translatable("sodium-extra-information.options.show_total_entity_count.tooltip"), value -> SodiumExtraInformationClientMod.options().extraInformationSettings.totalEntityCountConfig.showTotalEntityCount = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.totalEntityCountConfig.showTotalEntityCount));

        group.addOption(booleanOption(configBuilder, "shows_rendered_entities", Component.translatable("sodium-extra-information.options.shows_rendered_entities"), Component.translatable("sodium-extra-information.options.shows_rendered_entities.tooltip"), value -> SodiumExtraInformationClientMod.options().extraInformationSettings.renderedEntitiesConfig.showsRenderedEntities = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.renderedEntitiesConfig.showsRenderedEntities));

        group.addOption(booleanOption(configBuilder, "show_biome", Component.translatable("sodium-extra-information.options.show_biome"), Component.translatable("sodium-extra-information.options.show_biome.tooltip"), value -> SodiumExtraInformationClientMod.options().extraInformationSettings.biomeConfig.showBiome = value, () -> SodiumExtraInformationClientMod.options().extraInformationSettings.biomeConfig.showBiome));

        page.addOptionGroup(group);
        modOptions.addPage(page);
    }

    private static BooleanOptionBuilder booleanOption(ConfigBuilder configBuilder, String id, Component name, Component tooltip, Consumer<Boolean> setter, Supplier<Boolean> getter) {
        return configBuilder.createBooleanOption(ResourceLocation.fromNamespaceAndPath("sodiumextrainformation", id)).setName(name).setTooltip(tooltip).setBinding(setter, getter).setDefaultValue(false).setStorageHandler(() -> {
            try {
                SodiumExtraInformationClientMod.options().writeChanges();
            } catch (IOException e) {
                throw new RuntimeException("Failed to save options", e);
            }
        });
    }
}
