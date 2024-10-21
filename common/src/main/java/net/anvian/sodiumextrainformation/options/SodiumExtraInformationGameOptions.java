package net.anvian.sodiumextrainformation.options;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.anvian.sodiumextrainformation.client.SodiumExtraInformationClientMod;
import net.caffeinemc.mods.sodium.client.services.PlatformRuntimeInformation;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SodiumExtraInformationGameOptions {
    public final ExtraInformationSettings extraInformationSettings = new ExtraInformationSettings();
    private static final String DEFAULT_FILE_NAME = SodiumExtraInformationClientMod.MOD_ID + ".json";
    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().excludeFieldsWithModifiers(Modifier.PRIVATE).create();
    private Path configPath;

    public static SodiumExtraInformationGameOptions load() {
        Path path = PlatformRuntimeInformation.getInstance().getConfigDirectory().resolve(DEFAULT_FILE_NAME);
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

        if (!config.extraInformationSettings.validateTimeFormat(config.extraInformationSettings.localTimeFormat)) {
            config.extraInformationSettings.localTimeFormat = "HH:mm:ss";
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
        public boolean showLocalTime;
        public String localTimeFormat;
        public boolean showWordTime;
        public boolean showSessionTime;
        public boolean showMemoryUsage;
        public boolean showMemoryUsageExtended;
        public boolean showTotalEntityCount;
        public boolean showsRenderedEntities;
        public boolean showBiome;

        public ExtraInformationSettings() {
            this.showLocalTime = false;
            this.localTimeFormat = "HH:mm";
            this.showWordTime = false;
            this.showSessionTime = false;
            this.showMemoryUsage = false;
            this.showMemoryUsageExtended = false;
            this.showTotalEntityCount = false;
            this.showsRenderedEntities = false;
            this.showBiome = false;
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

}
