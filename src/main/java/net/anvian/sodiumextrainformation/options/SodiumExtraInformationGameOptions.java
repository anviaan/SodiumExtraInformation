package net.anvian.sodiumextrainformation.options;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.anvian.sodiumextrainformation.SodiumExtraInformationClient;
import net.anvian.sodiumextrainformation.util.RGB;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SodiumExtraInformationGameOptions {
    public final ExtraInformationSettings extraInformationSettings = new ExtraInformationSettings();
    private static final String DEFAULT_FILE_NAME = SodiumExtraInformationClient.MOD_ID + ".json";
    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().excludeFieldsWithModifiers(Modifier.PRIVATE).create();
    private Path configPath;

    public static SodiumExtraInformationGameOptions load() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(SodiumExtraInformationClient.MOD_ID).resolve(DEFAULT_FILE_NAME);
        SodiumExtraInformationGameOptions config;

        if (Files.exists(path)) {
            try (FileReader reader = new FileReader(path.toFile())) {
                config = GSON.fromJson(reader, SodiumExtraInformationGameOptions.class);
            } catch (IOException e) {
                throw new RuntimeException("Could not parse SSPB config", e);
            }
        } else {
            config = new SodiumExtraInformationGameOptions();
        }

        config.configPath = path;

        if (!config.extraInformationSettings.localTimeConfig.validateTimeFormat(config.extraInformationSettings.localTimeConfig.localTimeFormat)) {
            config.extraInformationSettings.localTimeConfig.localTimeFormat = "HH:mm:ss";
        }

        try {
            config.writeChanges();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't update SSPB config", e);
        }

        return config;
    }

    public void writeChanges() throws IOException {
        Path dir = this.configPath.getParent();

        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        } else if (!Files.isDirectory(dir)) {
            throw new IOException("Not a directory: " + dir);
        }

        Files.writeString(this.configPath, GSON.toJson(this));
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
            public String localTimeFormat;
            public RGB color;

            public LocalTimeConfig() {
                this.showLocalTime = false;
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
