package me.ShinyShadow_.Void.ability.voidability;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.ShinyShadow_.Void.ability.api.VoidAbility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Collection;


public class Implode extends VoidAbility {
    private boolean isCharged = false;

    private double radius = 5;

    private int charge = 0;

    private Location playerLoc;

    private Vector pullDirection;

    public Implode(Player player) {
        super(player);

        if (bPlayer.canBend(this)) {
            start();
            getCooldown();
            bPlayer.addCooldown(this);
        }
        if (!bPlayer.canBendIgnoreCooldowns(this)) {
            stop();
        }
    }

    @Override
    public void progress() {

        if(bPlayer.canBendIgnoreBindsCooldowns(this)) {
            ((LivingEntity) player).addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 80, 2));
            player.setVelocity(new Vector(0, -2, 0));
            if (!isCharged) {
                playerLoc = player.getLocation();
                Collection<Entity> nearbyEntities = GeneralMethods.getEntitiesAroundPoint(playerLoc, radius);
                for (Entity entity : nearbyEntities) {
                    if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
                        ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
                    }
                }

                for (int d = 0; d <= 80; d += 1) {
                    Location particleLoc = new Location(player.getWorld(), playerLoc.getX(), playerLoc.getY() + 0.6, playerLoc.getZ());
                    particleLoc.setX(playerLoc.getX() + Math.cos(d) * radius);
                    particleLoc.setZ(playerLoc.getZ() + Math.sin(d) * radius);
                    player.getWorld().spawnParticle(Particle.DUST, particleLoc, 1, 0.2, 0.2, 0.2, 0.1, new Particle.DustOptions(Color.BLACK, 1));
                }

                player.getWorld().spawnParticle(Particle.ASH, playerLoc.clone().add(0, 0.3, 0), 20, 0.4, 1, 0.4, 0.1);

                radius -= 0.225;
                if (radius <= 0) {
                    radius = 5;
                    charge += 1;
                    if (charge == 2) {
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.8F, 2F);
                        player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, playerLoc, 320, 3.5, 2, 3.5, 0.2);
                        player.getWorld().spawnParticle(Particle.SQUID_INK, playerLoc, 200, 3.5, 2, 3.5, 0.2);
                        radius = 0;
                        isCharged = true;
                    }
                }
            }
            if (isCharged) {
                radius += 0.85;
                for (int d = 0; d <= 60 +(50*radius); d += 1) {
                    Location particleLoc = new Location(player.getWorld(), playerLoc.getX(), playerLoc.getY() + 0.6, playerLoc.getZ());
                    particleLoc.setX(playerLoc.getX() + Math.cos(d) * radius);
                    particleLoc.setZ(playerLoc.getZ() + Math.sin(d) * radius);
                    player.getWorld().spawnParticle(Particle.DUST, particleLoc, 1, 0.2, 0.2, 0.2, 0.1, new Particle.DustOptions(Color.BLACK, 1));
                }
                player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, playerLoc, 20, radius, 0.2, radius, 0.1);
                Collection<Entity> nearbyEntities = GeneralMethods.getEntitiesAroundPoint(playerLoc, radius);
                for (Entity entity : nearbyEntities) {
                    if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {

                        pullDirection = GeneralMethods.getDirection(entity.getLocation(), playerLoc);
                        entity.setVelocity(pullDirection.multiply(-0.66));
                        DamageHandler.damageEntity(entity, 2, this);
                    }
                }

                if(radius >= 8) {
                    stop();
                }
            }

        }
    }


    @Override
    public boolean isSneakAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public boolean isIgniteAbility() {
        return false;
    }

    @Override
    public boolean isExplosiveAbility() {
        return false;
    }

    @Override
    public long getCooldown() {
        return 15000;
    }

    @Override
    public String getName() {
        return "Implode";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {

    }

    @Override
    public void stop() {

        getCooldown();
        bPlayer.addCooldown(this);
        remove();
    }

    public String getDescription() {
            return ChatColor.BOLD + " Tap shift to start condensing void energy " +
                    "and implode after a brief moment, pulling away " +
                    "entities in the area of effect. " +
                    "While this ability charges, your movement will be " +
                    "severely affected." ;
    }

    public String getInstructions() {
            return "Tap shift (You can use other abilities while this ability charges)";
    }
    @Override
    public String getAuthor() {
        return "ShinyShadow_";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}
