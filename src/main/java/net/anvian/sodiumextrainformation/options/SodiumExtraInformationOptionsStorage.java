package net.anvian.sodiumextrainformation.options;

import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.anvian.sodiumextrainformation.SodiumExtraInformationClient;

public class SodiumExtraInformationOptionsStorage implements OptionStorage<SodiumExtraInformationGameOptions> {
    private final SodiumExtraInformationGameOptions options = SodiumExtraInformationClient.options();

    @Override
    public SodiumExtraInformationGameOptions getData() {
        return this.options;
    }

    @Override
    public void save() {
        this.options.writeChanges();
    }
}
