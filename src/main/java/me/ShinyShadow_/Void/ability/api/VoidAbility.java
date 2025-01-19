package me.ShinyShadow_.Void.ability.api;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ElementalAbility;

import me.ShinyShadow_.Void.VoidElement;

import org.bukkit.entity.Player;

public abstract class VoidAbility extends ElementalAbility implements AddonAbility {

    public VoidAbility(Player player) {
        super(player);
    }

    @Override
    public Element getElement() {
        return (Element)VoidElement.VOID;
    }
}