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
                        .setBinding((opts, value) -> opts.extraInformationSettings.showLocalTime = value, opts -> opts.extraInformationSettings.showLocalTime)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.word_time"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.word_time.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showWordTime = value, opts -> opts.extraInformationSettings.showWordTime)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.session_time"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.session_time.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showSessionTime = value, opts -> opts.extraInformationSettings.showSessionTime)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.memory_usage"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.memory_usage.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showMemoryUsage = value, opts -> opts.extraInformationSettings.showMemoryUsage)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.memory_usage_extended"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.memory_usage_extended.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showMemoryUsageExtended = value, opts -> opts.extraInformationSettings.showMemoryUsageExtended)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.show_total_entity_count"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.show_total_entity_count.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showTotalEntityCount = value, opts -> opts.extraInformationSettings.showTotalEntityCount)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.shows_rendered_entities"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.shows_rendered_entities.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showsRenderedEntities = value, opts -> opts.extraInformationSettings.showsRenderedEntities)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraInformation$sodiumExtraOpts)
                        .setName(Component.translatable("sodium-extra-information.options.show_biome"))
                        .setTooltip(Component.translatable("sodium-extra-information.options.show_biome.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showBiome = value, opts -> opts.extraInformationSettings.showBiome)
                        .build())
                .build());

        cir.setReturnValue(new OptionPage(Component.translatable("sodium-extra.option.extras"), ImmutableList.copyOf(groups)));
    }
}
