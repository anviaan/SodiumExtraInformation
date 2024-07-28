package net.anvian.sodiumextrainformation.mixin;

import com.google.common.collect.ImmutableList;
import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import net.anvian.sodiumextrainformation.options.SodiumExtraInformationOptionsStorage;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = SodiumExtraGameOptionPages.class, remap = false)
public class SodiumExtraGameOptionPagesMixin {
    @Unique
    private static final SodiumExtraInformationOptionsStorage sodiumExtraOpts = new SodiumExtraInformationOptionsStorage();

    @Inject(method = "extra", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void inject(CallbackInfoReturnable<OptionPage> cir) {
        OptionPage optionPage = cir.getReturnValue();
        List<OptionGroup> groups = new ArrayList<>(optionPage.getGroups());

        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraOpts)
                        .setName(Text.translatable("sodium-extra-information.options.local_time"))
                        .setTooltip(Text.translatable("sodium-extra-information.options.local_time.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showLocalTime = value, opts -> opts.extraInformationSettings.showLocalTime)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraOpts)
                        .setName(Text.translatable("sodium-extra-information.options.word_time"))
                        .setTooltip(Text.translatable("sodium-extra-information.options.word_time.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showWordTime = value, opts -> opts.extraInformationSettings.showWordTime)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraOpts)
                        .setName(Text.translatable("sodium-extra-information.options.session_time"))
                        .setTooltip(Text.translatable("sodium-extra-information.options.session_time.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showSessionTime = value, opts -> opts.extraInformationSettings.showSessionTime)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraOpts)
                        .setName(Text.translatable("sodium-extra-information.options.memory_usage"))
                        .setTooltip(Text.translatable("sodium-extra-information.options.memory_usage.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showMemoryUsage = value, opts -> opts.extraInformationSettings.showMemoryUsage)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraOpts)
                        .setName(Text.translatable("sodium-extra-information.options.memory_usage_extended"))
                        .setTooltip(Text.translatable("sodium-extra-information.options.memory_usage_extended.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showMemoryUsageExtended = value, opts -> opts.extraInformationSettings.showMemoryUsageExtended)
                        .build())
                .build());

        cir.setReturnValue(new OptionPage(Text.translatable("sodium-extra.option.extras"), ImmutableList.copyOf(groups)));
    }
}
