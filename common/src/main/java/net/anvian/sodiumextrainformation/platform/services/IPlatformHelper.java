package net.anvian.sodiumextrainformation.platform.services;

import net.anvian.sodiumextrainformation.platform.Services;

import java.nio.file.Path;

public interface IPlatformHelper {
    IPlatformHelper INSTANCE = Services.load(IPlatformHelper.class);

    Path getConfigDirectory();

    String getPlatformName();
}