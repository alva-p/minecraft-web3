package com.mycompany.minecraftweb3.service;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DataService {

    private static File file;
    private static FileConfiguration config;

    public static void init(JavaPlugin plugin) {

plugin.getLogger().info("DataService inicializado correctamente");

        // Crear carpeta del plugin SIEMPRE
        if (!plugin.getDataFolder().exists()) {
            plugin.getLogger().info("Creando carpeta del plugin...");
            plugin.getDataFolder().mkdirs();
        }

        file = new File(plugin.getDataFolder(), "data.yml");

        if (!file.exists()) {
            plugin.getLogger().info("data.yml no existe, copiando desde resources...");
            plugin.saveResource("data.yml", false);
        } else {
            plugin.getLogger().info("data.yml encontrado, cargando...");
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void setToken(UUID uuid, String token) {

        if (config == null) {
            System.err.println("ERROR: DataService no inicializado");
            return;
        }

        config.set("players." + uuid + ".token", token);
        config.set("players." + uuid + ".linked", false);
        save();
    }

    public static String getToken(UUID uuid) {
        return config.getString("players." + uuid + ".token");
    }

    public static boolean isLinked(UUID uuid) {
        return config.getBoolean("players." + uuid + ".linked", false);
    }

    public static void setLinked(UUID uuid, boolean linked) {
        config.set("players." + uuid + ".linked", linked);
        save();
    }

    private static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
