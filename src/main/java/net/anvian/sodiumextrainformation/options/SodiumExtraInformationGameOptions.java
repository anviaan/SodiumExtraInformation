package net.anvian.sodiumextrainformation.options;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.anvian.sodiumextrainformation.SodiumExtraInformationClient;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SodiumExtraInformationGameOptions {
    private static final Gson gson;
    public final ExtraInformationSettings extraInformationSettings = new ExtraInformationSettings();
    private File file;

    public SodiumExtraInformationGameOptions() {
    }

    public static SodiumExtraInformationGameOptions load(File file) {
        SodiumExtraInformationGameOptions config;
        if (file.exists()) {
            try {
                FileReader reader = new FileReader(file);

                try {
                    config = gson.fromJson(reader, SodiumExtraInformationGameOptions.class);
                } catch (Throwable var6) {
                    try {
                        reader.close();
                    } catch (Throwable var5) {
                        var6.addSuppressed(var5);
                    }

                    throw var6;
                }

                reader.close();
            } catch (Exception e) {
                SodiumExtraInformationClient.LOGGER.error("Could not parse config, falling back to defaults!", e);
                config = new SodiumExtraInformationGameOptions();
            }
        } else {
            config = new SodiumExtraInformationGameOptions();
        }

        config.file = file;
        config.writeChanges();
        return config;
    }

    public void writeChanges() {
        File dir = this.file.getParentFile();
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("Could not create parent directories");
            }
        } else if (!dir.isDirectory()) {
            throw new RuntimeException("The parent file is not a directory");
        }

        try {
            FileWriter writer = new FileWriter(this.file);

            try {
                gson.toJson(this, writer);
            } catch (Throwable var6) {
                try {
                    writer.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not save configuration file", e);
        }
    }

    static {
        gson = (new GsonBuilder()).registerTypeAdapter(Identifier.class, new Identifier.Serializer()).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().excludeFieldsWithModifiers(new int[]{2}).create();
    }

    public static class ExtraInformationSettings {
        public boolean showLocalTime;

        public ExtraInformationSettings() {
            this.showLocalTime = false;
        }
    }
}
