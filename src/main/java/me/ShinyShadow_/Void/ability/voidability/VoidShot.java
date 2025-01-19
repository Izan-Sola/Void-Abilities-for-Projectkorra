
// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

package me.ShinyShadow_.Void.ability.voidability;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.ShinyShadow_.Void.Void;
import me.ShinyShadow_.Void.ability.api.VoidAbility;
import me.ShinyShadow_.Void.listeners.AbilityListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public class VoidShot extends VoidAbility {
    private static Void plugin;
    private int range;
    private int COOLDOWN;
    private double DAMAGE;
    private double pTimer = 10;
    private double witherDuration;
    private double coveredDistance;
    private double distance = 0;
    private double shotDuration = 20D;
    private int effectDuration = 10;
    private Location handLoc;
    private Location eyeLoc;
    private Location targetLoc;
    private Location currentLocation;
    private Vector direction = null;
    private Listener listener;
    private Entity target = null;
    public static boolean shoot = false;
    public static boolean exists = false;
    private boolean noEffects = true;

    public VoidShot(Player player) {
        super(player);
        shoot = false;
        eyeLoc = player.getEyeLocation();
        this.COOLDOWN = Void.plugin.getConfig().getInt("Abilities.Void.VoidShot.Cooldown");
        this.DAMAGE = Void.plugin.getConfig().getDouble("Abilities.Void.VoidShot.Damage");
        this.effectDuration = Void.plugin.getConfig().getInt("Abilities.Void.VoidShot.EffectDuration");
        exists = true;
        start();
    }

    public void progress() {
        if(!bPlayer.canBendIgnoreCooldowns(this) && !shoot ){
            stop();
        }
        if(bPlayer.canBend(this)) {

            if (!shoot) {
                handLoc = GeneralMethods.getRightSide(player.getLocation(), 1);
                player.getWorld().spawnParticle(Particle.DUST, handLoc.add(0, 1, 0), 25, 0.1, 0.1, 0.1, 1D, new Particle.DustOptions(Color.BLACK, 1));
            }
        }
        if(bPlayer.canBendIgnoreBindsCooldowns(this)) {
        if(shoot) {

            if(target == null) {
                bPlayer.addCooldown(this);
                if (direction == null) {
                    eyeLoc = player.getEyeLocation();
                    direction = player.getLocation().getDirection();
                }
                direction.multiply(1.025);
                //player.getWorld().spawnParticle(Particle.FALLING_DUST, eyeLoc, 60, 0.2, 0.2, 0.2, 0.06D, Material.DRAGON_EGG.createBlockData());
                player.getWorld().spawnParticle(Particle.FALLING_DUST, eyeLoc, 80, 0.1, 0.1, 0.1, 1D,  Material.DRAGON_EGG.createBlockData());
                player.getWorld().spawnParticle(Particle.ASH, eyeLoc, 25, 0.2, 0.1, 0.2, 1D);
                eyeLoc.add(direction);
                distance += direction.length();
                if (distance >= 25) {
                    stop();
                }
            }
            Collection<Entity> nearbyEntities = player.getWorld().getNearbyEntities(eyeLoc, 4, 4, 4);

            for (Entity entity : nearbyEntities) {
                if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
                    if(target == null) {
                        target = entity;
                    }
                    shotDuration -= 0.09D;
                    coveredDistance += 0.25D;
                    targetLoc = target.getLocation();
                    distance = eyeLoc.distance(targetLoc);
                    direction = targetLoc.toVector().subtract(eyeLoc.toVector()).normalize();
                    currentLocation = eyeLoc.clone().add(direction.clone().multiply(coveredDistance));
                    player.getWorld().spawnParticle(Particle.FALLING_DUST, currentLocation, 80, 0.1, 0.1, 0.1, 1D,  Material.DRAGON_EGG.createBlockData());
                }
            }
    if(target != null) {
        if (coveredDistance >= distance) {
            if(noEffects) {
                DamageHandler.damageEntity(target, DAMAGE, this);
                ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, effectDuration, 1));
                noEffects = false;
            }
            pTimer -= 0.09D;
            targetLoc = target.getLocation();
            player.getWorld().spawnParticle(Particle.FALLING_DUST, targetLoc, 2, 0.2, 1, 0.2, 1D, Material.DRAGON_EGG.createBlockData());
            if (pTimer <= 0) {

                stop();
            }
        }
        if (shotDuration <= 0) {

            stop();
        }
    }
        }
        }
    }



    public java.lang.String getAuthor() { return "ShinyShadow_"; }

    public java.lang.String getVersion() { return "1.0"; }

    public void load() {
       // this.listener = new AbilityListener();
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(this.listener, (Plugin) ProjectKorra.plugin);
        ConfigManager.defaultConfig.save();
    }

    public void stop() {
       // bPlayer.getCooldown();
        exists = false;
        HandlerList.unregisterAll(this.listener);
        remove();
    }

    public long getCooldown() { return COOLDOWN; }
@Override
    public String getDescription() {

            return  ChatColor.BOLD + " Charge a small amount of void energy that you can shoot, " +
                    "damaging and inflicting wither effect on your target. " +
                    "If you don't hit your target directly, the projectile " +
                    "will track nearby entities and automatically change its trajectory." + ChatColor.DARK_GRAY;
    }

    public String getInstructions(){
        return "Tap shift to charge and left click to shoot.";
    }
    public org.bukkit.Location getLocation() { return player.getLocation(); }

    public java.lang.String getName() { return "VoidShot"; }

    public boolean isExplosiveAbility() { return false; }

    public boolean isHarmlessAbility() { return false; }

    public boolean isIgniteAbility() { return false; }

    public boolean isSneakAbility() { return false;  }


}