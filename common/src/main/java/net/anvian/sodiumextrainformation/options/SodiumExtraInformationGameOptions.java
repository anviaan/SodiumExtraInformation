package net.anvian.sodiumextrainformation.options;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.flashyreese.mods.sodiumextra.common.util.IdentifierSerializer;
import net.anvian.sodiumextrainformation.client.SodiumExtraInformationClientMod;
import net.anvian.sodiumextrainformation.util.RGB;
import net.caffeinemc.mods.sodium.api.config.StorageEventHandler;
import net.minecraft.resources.Identifier;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SodiumExtraInformationGameOptions implements StorageEventHandler {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Identifier.class, new IdentifierSerializer())
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .excludeFieldsWithModifiers(Modifier.PRIVATE)
            .create();
    public final ExtraInformationSettings extraInformationSettings = new ExtraInformationSettings();
    private File file;

    public static SodiumExtraInformationGameOptions load(File file) {
        SodiumExtraInformationGameOptions config;

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                config = gson.fromJson(reader, SodiumExtraInformationGameOptions.class);
            } catch (Exception e) {
                SodiumExtraInformationClientMod.logger().error("Could not parse config, falling back to defaults!", e);
                config = new SodiumExtraInformationGameOptions();
            }
        } else {
            config = new SodiumExtraInformationGameOptions();
        }

        if (!config.extraInformationSettings.localTimeConfig.validateTimeFormat(config.extraInformationSettings.localTimeConfig.localTimeFormat)) {
            config.extraInformationSettings.localTimeConfig.localTimeFormat = "HH:mm:ss";
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

        try (FileWriter writer = new FileWriter(this.file)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            throw new RuntimeException("Could not save configuration file", e);
        }
    }

    @Override
    public void afterSave() {
        this.writeChanges();
    }

    public static class ExtraInformationSettings {
        public LocalTimeConfig localTimeConfig;
        public WordTimeConfig wordTimeConfig;
        public SessionTimeConfig sessionTimeConfig;
        public MemoryUsageConfig memoryUsageConfig;
        public TotalEntityCountConfig totalEntityCountConfig;
        public RenderedEntitiesConfig renderedEntitiesConfig;
        public BiomeConfig biomeConfig;

        public ExtraInformationSettings() {
            this.localTimeConfig = new LocalTimeConfig();
            this.wordTimeConfig = new WordTimeConfig();
            this.sessionTimeConfig = new SessionTimeConfig();
            this.memoryUsageConfig = new MemoryUsageConfig();
            this.totalEntityCountConfig = new TotalEntityCountConfig();
            this.renderedEntitiesConfig = new RenderedEntitiesConfig();
            this.biomeConfig = new BiomeConfig();
        }

        public static class LocalTimeConfig {
            public boolean showLocalTime;
            public boolean use12HourFormat;
            public String localTimeFormat;
            public RGB color;

            public LocalTimeConfig() {
                this.showLocalTime = false;
                this.use12HourFormat = false;
                this.localTimeFormat = "HH:mm";
                this.color = new RGB();
            }

            private boolean validateTimeFormat(String format) {
                try {
                    DateTimeFormatter.ofPattern(format);
                    return true;
                } catch (IllegalArgumentException | DateTimeParseException e) {
                    return false;
                }
            }
        }

        public static class WordTimeConfig {
            public boolean showWordTime;
            public RGB color;

            public WordTimeConfig() {
                this.showWordTime = false;
                this.color = new RGB();
            }
        }

        public static class SessionTimeConfig {
            public boolean showSessionTime;
            public RGB color;

            public SessionTimeConfig() {
                this.showSessionTime = false;
                this.color = new RGB();
            }
        }

        public static class MemoryUsageConfig {
            public boolean showMemoryUsage;
            public boolean showMemoryUsageExtended;
            public RGB color;

            public MemoryUsageConfig() {
                this.showMemoryUsage = false;
                this.showMemoryUsageExtended = false;
                this.color = new RGB();
            }
        }

        public static class TotalEntityCountConfig {
            public boolean showTotalEntityCount;
            public RGB color;

            public TotalEntityCountConfig() {
                this.showTotalEntityCount = false;
                this.color = new RGB();
            }
        }

        public static class RenderedEntitiesConfig {
            public boolean showsRenderedEntities;
            public RGB color;

            public RenderedEntitiesConfig() {
                this.showsRenderedEntities = false;
                this.color = new RGB();
            }
        }

        public static class BiomeConfig {
            public boolean showBiome;
            public RGB color;

            public BiomeConfig() {
                this.showBiome = false;
                this.color = new RGB();
            }
        }
    }

}
