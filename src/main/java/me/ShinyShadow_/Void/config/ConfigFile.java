package me.ShinyShadow_.Void.config;

import java.io.File;
import me.ShinyShadow_.Void.Void;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigFile {
    private Void plugin = Void.getInstance();

    private File file;

    public FileConfiguration config;

    public ConfigFile() {
        this(new File("config.yml"));
    }

    public ConfigFile(File file) {
        this.plugin = Void.plugin;
        this.file = new File(this.plugin.getDataFolder() + File.separator + file);
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
        reloadConfig();
    }

    public void createConfig() {
        if (!this.file.getParentFile().exists())
            try {
                this.file.getParentFile().mkdir();
                this.plugin.getLogger().info("Generating new directory for " + this.file.getName());
            } catch (Exception e) {
                this.plugin.getLogger().info("Failed to generate directory");
                e.printStackTrace();
            }
        if (!this.file.exists())
            try {
                this.file.createNewFile();
                this.plugin.getLogger().info("Generating new " + this.file.getName());
            } catch (Exception e) {
                this.plugin.getLogger().info("Failed to generate " + this.file.getName());
                e.printStackTrace();
            }
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void reloadConfig() {
        createConfig();
        try {
            this.config.load(this.file);
            this.plugin.getLogger().info("Loading configuration");
        } catch (Exception e) {
            this.plugin.getLogger().info("Failed to reload " + this.file.getName());
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            this.config.options().copyDefaults(true);
            this.config.save(this.file);
            this.plugin.getLogger().info("Successfully saved configuration");
        } catch (Exception e) {
            this.plugin.getLogger().info("Failed to save configuration");
            e.printStackTrace();
        }
    }
}
