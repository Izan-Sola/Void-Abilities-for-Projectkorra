package me.ShinyShadow_.Void.ability.voidability;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import me.ShinyShadow_.Void.Void;
import me.ShinyShadow_.Void.ability.api.VoidAbility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
//import java.util.Vector;

public class BlackHole extends VoidAbility {

private List<Block> sight;

private Listener listener;

private double chargeTime = 4D;
private double currPoint;
private double angle;
private double n1angle;
private double n2angle;
private double n3angle;
private double radius = 1.5;
private double bHoleDuration;
private double semiCircleDelay = 45;
private double offSetY;
private double coveredDistance = 0;
private double distance = 0;

private int COOLDOWN;
private int effectDuration;

private boolean released;
private boolean noEffects = true;
private boolean summonHole = false;

private Location currentLocation;
private Location eyeLoc = null;
private Location bHoleLoc;
private Location semiCircleLoc;

private Vector pullDirection;
private Vector direction;

    public BlackHole(Player player) {
        super(player);
        this.COOLDOWN = Void.plugin.getConfig().getInt("Abilities.Void.BlackHole.Cooldown");
        this.bHoleDuration = Void.plugin.getConfig().getDouble("Abilities.Void.BlackHole.BlackHoleDuration");
        this.effectDuration = Void.plugin.getConfig().getInt("Abilities.Void.BlackHole.EffectDuration");
        start();
    }

    @Override
    public void progress() {
        sight = player.getLineOfSight(Collections.singleton(Material.AIR), 8);
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            stop();
        }
        if (bPlayer.canBendIgnoreBinds(this)) {
            if (!player.isSneaking() && chargeTime > 0) {
                stop();
            }
            if (player.isSneaking() && chargeTime >= 0) {
                chargeTime -= 0.09D;
                player.getWorld().spawnParticle(Particle.FALLING_DUST, player.getLocation().add(0, 0.7, 0), 20, 0.2, 0.5, 0.2, 1D, Material.DRAGON_EGG.createBlockData());
                player.getWorld().spawnParticle(Particle.ASH, player.getLocation().add(0, 0.7, 0), 20, 0.2, 0.5, 0.2, 1D);
            }
            if (!player.isSneaking() && chargeTime <= 0 && !released) {
                bPlayer.addCooldown(this);
                released = true;

            }
        }
        if (bPlayer.canBendIgnoreBindsCooldowns(this)) {
            if (released) {
                bHoleDuration -= 0.09D;
                if (bHoleDuration <= 0) {
                    summonHole = false;
                    stop();
                }

                if (sight.getLast().isPassable()) {
                    offSetY = 2;
                }
                if (!sight.getLast().isPassable()) {
                    offSetY = 6;
                }
                if (bHoleLoc == null) {
                    bHoleLoc = sight.getLast().getLocation().add(0, 1 + offSetY, 0);
                    semiCircleLoc = sight.getLast().getLocation().add(0, 1 + offSetY, 0);

                }
                if (eyeLoc == null) {
                    eyeLoc = player.getEyeLocation();
                    direction = player.getLocation().getDirection();
                }
                if (coveredDistance <= distance) {
                    coveredDistance += 0.5;
                    distance = eyeLoc.distance(bHoleLoc);
                    direction = bHoleLoc.toVector().subtract(eyeLoc.toVector()).normalize();
                    currentLocation = eyeLoc.clone().add(direction.clone().multiply(coveredDistance));
                    direction.multiply(1.025);
                    //player.getWorld().spawnParticle(Particle.FALLING_DUST, eyeLoc, 60, 0.2, 0.2, 0.2, 0.06D, Material.DRAGON_EGG.createBlockData());
                    player.getWorld().spawnParticle(Particle.FALLING_DUST, currentLocation, 40, 0.1, 0.1, 0.1, 1D, Material.DRAGON_EGG.createBlockData());
                    // player.getWorld().spawnParticle(Particle.ASH, currentLocation, 25, 0.2, 0.1, 0.2, 1D);
                    player.getWorld().spawnParticle(Particle.BLOCK_CRUMBLE, currentLocation, 90, 0.4, 0.4, 0.4, 0.8D, Material.BEACON.createBlockData());

                }
                if (coveredDistance >= distance && summonHole == false) {
                    player.getWorld().spawnParticle(Particle.FALLING_DUST, currentLocation, 300, 2, 2, 2, 4D, Material.DRAGON_EGG.createBlockData());
                    // player.getWorld().spawnParticle(Particle.ASH, currentLocation, 25, 0.2, 0.1, 0.2, 1D);
                    player.getWorld().spawnParticle(Particle.ASH, currentLocation, 300, 2, 2, 2, 4D);
                    summonHole = true;
                }
                if (summonHole) {

                    Collection<Entity> nearbyEntities = GeneralMethods.getEntitiesAroundPoint(bHoleLoc, 6);
                    for (Entity entity : nearbyEntities) {
                        if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {

                            pullDirection = GeneralMethods.getDirection(entity.getLocation(), bHoleLoc);
                            entity.setVelocity(pullDirection.multiply(0.012));
                            Collection<Entity> closeEntities = GeneralMethods.getEntitiesAroundPoint(bHoleLoc, 3);
                            for (Entity closeEntity : closeEntities) {
                                if (closeEntity instanceof LivingEntity) {
                                    ((LivingEntity) closeEntity).addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, effectDuration, 2));
                                    ((LivingEntity) closeEntity).addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, effectDuration, 2));
                                    ((LivingEntity) closeEntity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 135, 0));
                                }
                            }
                        }
                    }
                    player.getWorld().spawnParticle(Particle.DUST, bHoleLoc, 160, 0.6, 0.6, 0.6, 0.8, new Particle.DustOptions(Color.BLACK, 1));
                    player.getWorld().spawnParticle(Particle.BLOCK_CRUMBLE, bHoleLoc, 40, 1.2, 1.2, 1.2, 0.8D, Material.BEACON.createBlockData());
                    for (int d = 0; d <= 50; d += 1) {

                        Location particleLoc = new Location(player.getWorld(), bHoleLoc.getX(), bHoleLoc.getY(), bHoleLoc.getZ());
                        particleLoc.setX(bHoleLoc.getX() + Math.cos(d) * 3);
                        particleLoc.setZ(bHoleLoc.getZ() + Math.sin(d) * 3);
                        player.getWorld().spawnParticle(Particle.DUST, particleLoc, 1, 0, 0, 0, 0, new Particle.DustOptions(Color.BLACK, 1));
                    }
                    for (int i = 0; i < 6; i++) {
                        this.currPoint += 180 / 150;
                        if (this.currPoint > 180) {
                            this.currPoint = 0;
                        }
                        angle = (this.currPoint * Math.PI / 180.0D) + (Math.PI / 2);
                        double x = radius * Math.cos(angle);
                        double y = radius * Math.sin(angle);
                        double z = radius * Math.sin(angle);
                        semiCircleLoc.add(x, y, z);
                        player.getWorld().spawnParticle(Particle.ASH, semiCircleLoc.clone().add(0, offSetY, 2.5), 3, 0.1, 0.1, 0.1, 1);
                        player.getWorld().spawnParticle(Particle.DUST, semiCircleLoc.clone().add(0, offSetY, 2.5), 0, 0, 0, 0, 0D, new Particle.DustOptions(Color.BLACK, 1));
                        semiCircleLoc.subtract(x, y, z);

                        n1angle = (this.currPoint * Math.PI / 180.0D);
                        double n1x = radius * Math.cos(n1angle);
                        double n1y = radius * Math.sin(angle);
                        double n1z = radius * Math.sin(n1angle);
                        semiCircleLoc.add(n1x, -n1y, n1z);
                        player.getWorld().spawnParticle(Particle.ASH, semiCircleLoc.clone().add(2.5, -offSetY, 0), 3, 0.1, 0.1, 0.1, 1);
                        player.getWorld().spawnParticle(Particle.DUST, semiCircleLoc.clone().add(2.5, -offSetY, 0), 0, 0, 0, 0, 0D, new Particle.DustOptions(Color.BLACK, 1));
                        semiCircleLoc.subtract(n1x, -n1y, n1z);


                        n2angle = (this.currPoint * Math.PI / 180.0D) + 3 * (Math.PI / 2);
                        double n2x = radius * Math.cos(n2angle);
                        double n2y = radius * Math.sin(angle);
                        double n2z = radius * Math.sin(n2angle);
                        semiCircleLoc.add(n2x, y, n2z);
                        player.getWorld().spawnParticle(Particle.ASH, semiCircleLoc.clone().add(0, offSetY, -2.5), 3, 0.1, 0.1, 0.1, 1);
                        player.getWorld().spawnParticle(Particle.DUST, semiCircleLoc.clone().add(0, offSetY, -2.5), 0, 0, 0, 0, 0D, new Particle.DustOptions(Color.BLACK, 1));
                        semiCircleLoc.subtract(n2x, n2y, n2z);


                        n3angle = (this.currPoint * Math.PI / 180.0D) + Math.PI;
                        double n3x = radius * Math.cos(n3angle);
                        double n3y = radius * Math.sin(angle);
                        double n3z = radius * Math.sin(n3angle);
                        semiCircleLoc.add(n3x, -n3y, n3z);
                        player.getWorld().spawnParticle(Particle.ASH, semiCircleLoc.clone().add(-2.5, -offSetY, 0), 3, 0.1, 0.1, 0.1, 1);
                        player.getWorld().spawnParticle(Particle.DUST, semiCircleLoc.clone().add(-2.5, -offSetY, 0), 0, 0, 0, 0, 0D, new Particle.DustOptions(Color.BLACK, 1));
                        semiCircleLoc.subtract(n3x, -n3y, n3z);

                    }
                }
            }
        }
        }

    @Override
    public boolean isSneakAbility() {
        return true;
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
        return COOLDOWN;
    }

    @Override
    public String getName() {
        return "BlackHole";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(this.listener, (Plugin) ProjectKorra.plugin);
        ConfigManager.defaultConfig.save();
    }

    @Override
    public void stop() {

        HandlerList.unregisterAll(this.listener);

        remove();
    }

    @Override
    public String getAuthor() {
        return "ShinyShadow_";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
@Override
    public String getDescription() {

        return  ChatColor.BOLD + " Concentrate great amounts of void energy to " +
                "shoot a beam that will create a black hole which will suck in " +
                "any nearby entity."  + ChatColor.DARK_GRAY;
    }

    public String getInstructions(){
        return "Hold Shift to charge, release to throw";
    }
}
