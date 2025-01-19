package me.ShinyShadow_.Void.ability.voidability;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import me.ShinyShadow_.Void.VoidElement;
import me.ShinyShadow_.Void.ability.api.VoidAbility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WormHole extends VoidAbility {
    private List<Block> sight;

    private Location wormHoleLoc;
    private Location currentLocation;
    private Location curvePoint;
    private Location startPoint;

    public static boolean wormHoleExists = false;
    private boolean doTeleport = false;

    private int totalBlocks;
    private int middleBlock;

    private String currentWorld;
    private BukkitTask task;
    private double wormHoleDuration = 20;
    private double offSetY = 0;
    private double coveredDistance = 0;
    private double distance = 0;
    private double test = 0.35;
    private double radius = 0.5;
    private Vector direction;

    Set<Material> ignoredBlocks = new HashSet<>();

    public WormHole(Player player) {
        super(player);

        if(bPlayer.canBend(this)) {

        }
        if(!bPlayer.canBendIgnoreBinds(this)){
            stop();
        }
    }

    @Override
    public void progress() {
       task = new BukkitRunnable() {
            @Override
            public void run() {
                stop();

            }
        }.runTaskLater(VoidElement.VOID.getPlugin(), 120L);
        sight = player.getLineOfSight(ignoredBlocks, 20);

        if (bPlayer.canBendIgnoreBindsCooldowns(this)) {

            if (!wormHoleExists) {
                currentWorld = player.getWorld().getName();
                wormHoleLoc = sight.getLast().getLocation();
                totalBlocks = sight.size();
                middleBlock = totalBlocks / 2;
                curvePoint = sight.get(middleBlock).getLocation();
                if (player.isSneaking()) {
                    wormHoleExists = true;
                }
            }

            if (wormHoleExists) {
                if (!player.getWorld().getName().equals(currentWorld)) {
                    stop();
                }

if(this.wormHoleLoc == null) {
    //should never trigger this
    stop();
}
                player.getWorld().spawnParticle(Particle.DUST, wormHoleLoc.clone().add(0, 1, 0), 25, 0.2, 0.5, 0.2, 0.6D, new Particle.DustOptions(Color.BLACK, 1));
                for (int d = 0; d <= 20; d += 1) {
                    Location ringLoc = new Location(player.getWorld(), wormHoleLoc.getX(), wormHoleLoc.getY()+0.25, wormHoleLoc.getZ());
                    ringLoc.setX(wormHoleLoc.getX() + Math.cos(d) * 0.75);
                    ringLoc.setZ(wormHoleLoc.getZ() + Math.sin(d) * 0.75);
                    player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, ringLoc, 0, 0, 0, 0, 0);
                }
                test -= 0.09;
                if (bPlayer.canBend(this) && player.isSneaking() && test <= 0 && !doTeleport) {
                    startPoint = player.getLocation();
                    doTeleport = true;
                }
                if(!doTeleport && player.getWorld().getName().equals(currentWorld)) {
                    distance = player.getLocation().distance(wormHoleLoc);
                    if(distance >= 35) {
                        stop();
                    }
                }
                if (doTeleport) {
                    if (coveredDistance <= distance) {
                        //move the player in a curve
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WARDEN_TENDRIL_CLICKS, 0.9F, 0.5F);
                        coveredDistance += 0.2;
                        distance = player.getLocation().distance(wormHoleLoc);
                        direction = wormHoleLoc.toVector().subtract(player.getEyeLocation().toVector()).normalize();

                        if(offSetY == 0) {
                            offSetY = distance;
                        }
                     //   player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, player.getLocation(), 30, 0.1, 0.1, 0.1, 0.6);
                        currentLocation = player.getEyeLocation().clone().add(direction.clone().multiply(coveredDistance));
                        player.getWorld().spawnParticle(Particle.DUST, currentLocation, 20, 0.2, 0.2, 0.2, 0.2, new Particle.DustOptions(Color.BLACK, 1));
                       // player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, player.getLocation(), 30, 0.1, 0.1, 0.1, 0.6);
                        player.teleport(currentLocation);

                         for (int i = 0; i <= 160; i++) {
                            double t = i / (double) 160.0;
                            // Calculate the position of the particle on the Bézier curve
                            double x = Math.pow(1 - t, 2) * startPoint.getX() + 2 * (1 - t) * t * (curvePoint.getX() * 1) + Math.pow(t, 2) * wormHoleLoc.getX();
                            double y = Math.pow(1 - t, 2) * startPoint.getY() + 2 * (1 - t) * t * (curvePoint.clone().getY() + offSetY) + Math.pow(t, 2) * wormHoleLoc.getY();
                            double z = Math.pow(1 - t, 2) * startPoint.getZ() + 2 * (1 - t) * t * (curvePoint.getZ() * 1) + Math.pow(t, 2) * wormHoleLoc.getZ();
                            Location particleLocation = new Location(player.getWorld(), x, y, z);
                            player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, particleLocation, 3, 0, 0, 0, 0);
                        }

                    }
                    if(coveredDistance >= distance) {
                        for (int d = 0; d <= 20; d += 1) {
                            Location ringLoc = new Location(player.getWorld(), wormHoleLoc.getX(), wormHoleLoc.getY()+0.25, wormHoleLoc.getZ());
                            ringLoc.setX(wormHoleLoc.getX() + Math.cos(d) * radius);
                            ringLoc.setZ(wormHoleLoc.getZ() + Math.sin(d) * radius);
                            player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, ringLoc, 1, 0, 0, 0, 0.5);
                            player.getWorld().spawnParticle(Particle.DUST, player.getLocation(),  1, 0, 0, 0, 0.1, new Particle.DustOptions(Color.BLACK, 1));
                        }
                        radius += 0.2;
                       if(radius >= 2){
                           player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 0.7f);
                           stop();
                       }
                    }
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
        return 6000;
    }

    @Override
    public String getName() {
        return "WormHole";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
        ConfigManager.defaultConfig.save();
    }

    @Override
    public void stop() {
        task.cancel();
        wormHoleExists = false;
        getCooldown();
        bPlayer.addCooldown(this);

        remove();
    }

    public String getDescription() {
        return ChatColor.BOLD + " Create a wormhole you can use to travel " +
                "at high speeds.";
    }

    public String getInstructions() {
        return "Tap shift to create wormhole, tap shift to teleport. Hold shift for faster usage.";
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
