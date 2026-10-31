package com.donutclient;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Stores client-side preferences in config/donutclient.properties. */
public final class ClientConfig {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("donutclient.properties");
    private static final Properties VALUES = new Properties();

    public static boolean coordinates = true;
    public static boolean fps = true;
    public static boolean armor = true;
    public static boolean totemAlert = true;
    public static boolean welcomeMessage = true;

    private ClientConfig() {}

    public static void load() {
        if (Files.exists(FILE)) {
            try (InputStream in = Files.newInputStream(FILE)) {
                VALUES.load(in);
                coordinates = bool("hud.coordinates", true);
                fps = bool("hud.fps", true);
                armor = bool("hud.armor", true);
                totemAlert = bool("utility.totem_alert", true);
                welcomeMessage = bool("general.welcome_message", true);
            } catch (IOException e) {
                DonutClient.LOGGER.error("Could not load Donut Client settings", e);
            }
        }
    }

    public static void save() {
        VALUES.setProperty("hud.coordinates", Boolean.toString(coordinates));
        VALUES.setProperty("hud.fps", Boolean.toString(fps));
        VALUES.setProperty("hud.armor", Boolean.toString(armor));
        VALUES.setProperty("utility.totem_alert", Boolean.toString(totemAlert));
        VALUES.setProperty("general.welcome_message", Boolean.toString(welcomeMessage));
        try {
            Files.createDirectories(FILE.getParent());
            try (OutputStream out = Files.newOutputStream(FILE)) {
                VALUES.store(out, "Donut Client settings");
            }
        } catch (IOException e) {
            DonutClient.LOGGER.error("Could not save Donut Client settings", e);
        }
    }

    private static boolean bool(String key, boolean fallback) {
        return Boolean.parseBoolean(VALUES.getProperty(key, Boolean.toString(fallback)));
    }
}
