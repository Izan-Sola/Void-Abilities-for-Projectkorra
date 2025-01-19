package me.ShinyShadow_.Void;

import com.projectkorra.projectkorra.Element;

import com.projectkorra.projectkorra.configuration.ConfigManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;

import org.bukkit.plugin.Plugin;


public class VoidElement extends Element{
	public static final VoidElement VOID = new VoidElement("Void", ChatColor.DARK_GRAY , "Void", 5555555);
	  
	  private ChatColor defaultColor;
	  
	  private String configName;

	  private int dust;
	  
	  private Color dustColor;

	 public VoidElement(String name, ChatColor defaultColor, String configName, int dustColor) {
		  super(name, Element.ElementType.NO_SUFFIX, (Plugin)Void.getInstance());
		    this.defaultColor = ChatColor.DARK_GRAY;
		    this.configName = configName;
		    this.dust = dustColor;
		    this.dustColor = Color.fromRGB(dustColor);

	  }

	public ChatColor getColor() {
		String color = ConfigManager.languageConfig.get().getString("Chat.Colors." + getName());
		return (color != null) ? ChatColor.of(color) : getDefaultColor();
	}

	public ChatColor getSubColor() {
		String color = ConfigManager.languageConfig.get().getString("Chat.Colors." + getColor() + "Sub");
		return (color != null) ? ChatColor.of(color) : ChatColor.WHITE;
	}

	public ChatColor getDefaultColor() {
		return this.defaultColor;
	}

	public int getDustHexColor() {
		return this.dust;
	}

	public String getConfigName() {
		return this.configName;
	}

	public Color getDustColor() {
		return this.dustColor;
	}
}
