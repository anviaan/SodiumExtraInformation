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
                        .setName(Text.of("Local Time"))
                        .setTooltip(Text.of("Show the local time on the overlay"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showLocalTime = value, opts -> opts.extraInformationSettings.showLocalTime)
                        .build())
                .add(OptionImpl.createBuilder(Boolean.TYPE, sodiumExtraOpts)
                        .setName(Text.of("Word time"))
                        .setTooltip(Text.of("Show the word time on the overlay"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.extraInformationSettings.showWordTime = value, opts -> opts.extraInformationSettings.showWordTime)
                        .build())
                .build());

        cir.setReturnValue(new OptionPage(Text.translatable("sodium-extra.option.extras"), ImmutableList.copyOf(groups)));
    }
}
