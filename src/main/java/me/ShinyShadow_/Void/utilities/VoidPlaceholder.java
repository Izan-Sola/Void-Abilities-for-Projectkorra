package me.ShinyShadow_.Void.utilities;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.ShinyShadow_.Void.listeners.PKEvents;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.ShinyShadow_.Void.VoidElement;
import me.ShinyShadow_.Void.Void;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class VoidPlaceholder extends PlaceholderExpansion {
    public String onPlaceholderRequest(Player player, String params) {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if (bPlayer == null)
            return "";
        List<Element> elements = new ArrayList<>(bPlayer.getElements());
        elements.remove(VoidElement.VOID);
        if (params.equals("element") || params.equals("elementcolor")) {
            ChatColor chatColor;
            String e = "Nonbender";
            ChatColor chatColor1 = ChatColor.WHITE;
            if (bPlayer.hasElement(Element.AVATAR)) {
                chatColor = Element.AVATAR.getColor();
                e = Element.AVATAR.getName();
            } //else if (elements.size() == 2 && bPlayer.hasElement((Element)SpiritElement.DARK) &&
            //bPlayer.hasElement((Element)SpiritElement.LIGHT)) {
            chatColor = VoidElement.VOID.getColor();
            e = VoidElement.VOID.getName();
            if (elements.size() > 0) {
                chatColor = ((Element)elements.get(0)).getColor();
                e = ((Element)elements.get(0)).getName();
            }
            if (params.equals("element"))
                return e;
            return (String)chatColor.toString() + ChatColor.BOLD;
        }
        if (params.equals("elements"))
            return elements.stream().map(item -> item.getColor() + item.getName()).collect(Collectors.joining(" "));
        return null;
    }

    public boolean persist() {
        return true;
    }

    public boolean canRegister() {
        return true;
    }

    public String getAuthor() {
        return Void.getInstance().getDescription().getAuthors().toString();
    }

    public String getIdentifier() {
        return "Void";
    }

    public String getVersion() {
        return Void.getInstance().getDescription().getVersion();
    }
}