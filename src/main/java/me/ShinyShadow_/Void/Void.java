package me.ShinyShadow_.Void;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.util.CollisionInitializer;
import me.ShinyShadow_.Void.config.ConfigFile;
import me.ShinyShadow_.Void.listeners.AbilityListener;
//import me.ShinyShadow_.Void.utilities.VoidPlaceholder;
import me.ShinyShadow_.Void.listeners.PKEvents;
import me.ShinyShadow_.Void.utilities.VoidPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import me.ShinyShadow_.Void.config.Config;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.CoreAbility;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;

public class Void extends JavaPlugin {
	public Element VOID;
	public static Void plugin;

	public void onEnable() {
		plugin = this;

		VOID = VoidElement.VOID;;
		CoreAbility.registerPluginAbilities(this, "me.ShinyShadow_.Void.ability");
		getServer().getPluginManager().registerEvents((Listener)new PKEvents(), (Plugin)this);
		getServer().getPluginManager().registerEvents((Listener)new AbilityListener(), (Plugin)this);


		new Config(this.plugin);

		new ConfigFile();;
		CollisionInitializer collisionInitializer = ProjectKorra.getCollisionInitializer();
if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
		  (new VoidPlaceholder()).register();
		  plugin.getLogger().info("Successfully enabled Void.");
	}
}

public void onDisable() {

}

public static Void getInstance() {
	return plugin;
}


}
