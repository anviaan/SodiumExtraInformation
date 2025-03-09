package net.anvian.sodiumextrainformation.options;

import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.anvian.sodiumextrainformation.SodiumExtraInformationClient;

import java.io.IOException;

public class SodiumExtraInformationOptionsStorage implements OptionStorage<SodiumExtraInformationGameOptions> {
    private final SodiumExtraInformationGameOptions options = SodiumExtraInformationClient.options();

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

        SodiumExtraInformationClient.logger().info("Saved options");
    }
}
