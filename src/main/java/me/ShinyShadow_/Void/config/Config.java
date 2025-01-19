package me.ShinyShadow_.Void.config;

import com.projectkorra.projectkorra.configuration.ConfigManager;
import me.ShinyShadow_.Void.VoidElement;
import me.ShinyShadow_.Void.Void;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private static Void plugin;

    public Config(Void plugin) {
        Config.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = Void.plugin.getConfig();
        config.addDefault("Abilities.Void.VoidShot.Cooldown", Integer.valueOf(5000));
        config.addDefault("Abilities.Void.VoidShot.Damage", Integer.valueOf(1));
        config.addDefault("Abilities.Void.VoidShot.EffectDuration", Integer.valueOf(100));
        config.addDefault("Abilities.Void.BlackHole.Cooldown", Integer.valueOf(25000));
        config.addDefault("Abilities.Void.BlackHole.EffectDuration", Integer.valueOf(200));
        config.addDefault("Abilities.Void.BlackHole.BlackHoleDuration", Double.valueOf(15));
        config.addDefault("Abilities.Void.VoidChain.Cooldown", Integer.valueOf(10000));
        config.addDefault("Abilities.Void.VoidChain.Damage", Double.valueOf(2));
        config.addDefault("Abilities.Void.Combos.VoidCombustions.Damage", Double.valueOf(4));
        config.addDefault("Abilities.Void.Combos.VoidCombustions.Cooldown", Double.valueOf(15000));
        ConfigManager.languageConfig.save();
        config.options().copyDefaults(true);
       Void.plugin.saveConfig();
    }
}