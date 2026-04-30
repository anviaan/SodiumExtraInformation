package net.anvian.sodiumextrainformation.mixin;

import com.google.common.collect.ImmutableList;
import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraGameOptionPages;
import net.anvian.sodiumextrainformation.options.SodiumExtraInformationOptionsStorage;
import net.caffeinemc.mods.sodium.client.gui.options.OptionGroup;
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpl;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import net.caffeinemc.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = SodiumExtraGameOptionPages.class, remap = false)
public class SodiumExtraGameOptionPagesMixin {
    @Unique
    private static final SodiumExtraInformationOptionsStorage sodiumExtraInformation$sodiumExtraOpts = new SodiumExtraInformationOptionsStorage();

    @Inject(method = "extra", at = @At("RETURN"), cancellable = true)
    private static void inject(CallbackInfoReturnable<OptionPage> cir) {
        OptionPage optionPage = cir.getReturnValue();
        List<OptionGroup> groups = new ArrayList<>(optionPage.getGroups());

        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.local_time"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.local_time.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.localTimeConfig.showLocalTime = value, opts -> opts.extraInformationSettings.localTimeConfig.showLocalTime)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.local_time_12h"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.local_time_12h.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.localTimeConfig.use12HourFormat = value, opts -> opts.extraInformationSettings.localTimeConfig.use12HourFormat)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.word_time"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.word_time.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.wordTimeConfig.showWordTime = value, opts -> opts.extraInformationSettings.wordTimeConfig.showWordTime)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.session_time"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.session_time.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.sessionTimeConfig.showSessionTime = value, opts -> opts.extraInformationSettings.sessionTimeConfig.showSessionTime)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.memory_usage"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.memory_usage.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.memoryUsageConfig.showMemoryUsage = value, opts -> opts.extraInformationSettings.memoryUsageConfig.showMemoryUsage)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.memory_usage_extended"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.memory_usage_extended.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.memoryUsageConfig.showMemoryUsageExtended = value, opts -> opts.extraInformationSettings.memoryUsageConfig.showMemoryUsageExtended)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.show_total_entity_count"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.show_total_entity_count.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.totalEntityCountConfig.showTotalEntityCount = value, opts -> opts.extraInformationSettings.totalEntityCountConfig.showTotalEntityCount)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.shows_rendered_entities"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.shows_rendered_entities.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.renderedEntitiesConfig.showsRenderedEntities = value, opts -> opts.extraInformationSettings.renderedEntitiesConfig.showsRenderedEntities)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.show_biome"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.show_biome.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.biomeConfig.showBiome = value, opts -> opts.extraInformationSettings.biomeConfig.showBiome)
                        .build())
                .build());

        cir.setReturnValue(new OptionPage(Component.translatable("sodium-extra.option.extras"), ImmutableList.copyOf(groups)));
    }
}
