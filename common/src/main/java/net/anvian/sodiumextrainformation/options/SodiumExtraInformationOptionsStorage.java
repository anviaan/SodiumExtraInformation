package net.anvian.sodiumextrainformation.options;

import net.anvian.sodiumextrainformation.CommonMod;
import net.anvian.sodiumextrainformation.Constants;
import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;

import java.io.IOException;

public class SodiumExtraInformationOptionsStorage implements OptionStorage<SodiumExtraInformationGameOptions> {
    private final SodiumExtraInformationGameOptions options = CommonMod.options();

    @Override
    public SodiumExtraInformationGameOptions getData() {
        return this.options;
    }

    @Override
    public void save() {
        try {
            this.options.writeChanges();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save options", e);
        }

        Constants.LOG.info("Saved options");
    }
}
