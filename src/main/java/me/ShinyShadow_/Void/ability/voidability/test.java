


                 //   NEVERMIND lol \\










import org.bukkit.Particle;

/*package me.ShinyShadow_.Void.ability.voidability;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.ShinyShadow_.Void.ability.api.VoidAbility;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.Collection;



public class test extends VoidAbility {

    private boolean summoned = false;

    private double coveredDistanceToTarget;
    private double coveredDistanceToRest;
    private double distanceToTarget = 0;
    private double distanceToRest;
    private double shotDuration = 20D;

    private BlockDisplay start;
    private BlockDisplay end;

    private Location targetLoc;
    private Location curvePoint1;
    private Location curvePoint2;
    private Location currentLocation;
    private Location restLoc;

    private Vector direction;

    private Entity target;

    private boolean rest = false;

    public test(Player player) {
        super(player);

        start();
    }

    @Override
    public void progress() {

        if (!summoned) {

            start = (BlockDisplay) player.getWorld().spawnEntity(player.getLocation(), EntityType.BLOCK_DISPLAY);
            start.setBlock(Material.END_ROD.createBlockData());
            start.setVisibleByDefault(false);
            curvePoint1 = start.getLocation().clone().add(4, 1, 0);
            end = (BlockDisplay) player.getWorld().spawnEntity(player.getLocation().add(0, 3, 0), EntityType.BLOCK_DISPLAY);
            end.setBlock(Material.END_ROD.createBlockData());
            curvePoint2 = end.getLocation().clone().add(0, 0, 0);
           // end.setVisibleByDefault(false);
            restLoc = end.getLocation().clone();
            currentLocation = end.getLocation();
            end.setRotation(6, 3);
            summoned = true;
        }
        if (summoned) {
            if(distanceToTarget >= 20) {
                target = null;
                rest = false;
                coveredDistanceToTarget = 0;
                distanceToTarget = 0;
            }
            Collection<Entity> nearbyEntities = GeneralMethods.getEntitiesAroundPoint(start.getLocation(), 8);
            for (Entity entity : nearbyEntities) {

                if (!rest && entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
                    if (coveredDistanceToTarget <= distanceToTarget) {
                        if (target == null || target.isDead()) {
                            target = entity;
                        }
                    }
                }
                }

            if (!rest && target != null && !target.isDead() && distanceToTarget <= 20) {
                coveredDistanceToTarget += 0.05D;

                shotDuration -= 0.09D;
                targetLoc = target.getLocation();
                distanceToTarget = end.getLocation().distance(targetLoc);
                direction = targetLoc.toVector().subtract(end.getLocation().toVector()).normalize();
                currentLocation = end.getLocation().clone().add(direction.clone().multiply(coveredDistanceToTarget));
                end.teleport(currentLocation);
                if(coveredDistanceToTarget >= distanceToTarget) {
                    rest = true;
                    coveredDistanceToTarget = 0;
                }
            }
            if(target != null) {
                if(rest || target.isDead()) {
                    distanceToRest = currentLocation.distance(restLoc);
                    coveredDistanceToRest += 0.10D;
                    direction = restLoc.toVector().subtract(currentLocation.toVector()).normalize();
                    currentLocation = currentLocation.clone().add(direction.clone().multiply(coveredDistanceToRest));
                    end.teleport(currentLocation);
                    if (coveredDistanceToRest >= distanceToRest) {
                        rest = false;
                        coveredDistanceToRest = 0;
                    }
                }
            }

           for (int i = 0; i <= 10; i++) {
                double t = i / (double) 10;
                // Calculate the position of the particle on the Bézier curve
               double x = Math.pow(1 - t, 2) * start.getLocation().getX() + 2 * (1 - t) * t * curvePoint.getX() + Math.pow(t, 2) * end.getLocation().getX();
                double y = Math.pow(1 - t, 2) * start.getLocation().getY() + 2 * (1 - t) * t * curvePoint.getY() + Math.pow(t, 2) * end.getLocation().getY();
                double z = Math.pow(1 - t, 2) * start.getLocation().getZ() + 2 * (1 - t) * t * curvePoint.getZ() + Math.pow(t, 2) * end.getLocation().getZ();
                double x = Math.pow(1 - t, 3) * start.getLocation().getX()
                        + 3 * Math.pow(1 - t, 2) * t * curvePoint1.getX()
                        + 3 * (1 - t) * Math.pow(t, 2) * curvePoint2.getX()
                        + Math.pow(t, 3) * end.getLocation().getX();

                double y = Math.pow(1 - t, 3) * start.getLocation().getY()
                        + 3 * Math.pow(1 - t, 2) * t * curvePoint1.getY()
                        + 3 * (1 - t) * Math.pow(t, 2) * curvePoint2.getY()
                        + Math.pow(t, 3) * end.getLocation().getY();

                double z = Math.pow(1 - t, 3) * start.getLocation().getZ()
                        + 3 * Math.pow(1 - t, 2) * t * curvePoint1.getZ()
                        + 3 * (1 - t) * Math.pow(t, 2) * curvePoint2.getZ()
                        + Math.pow(t, 3) * end.getLocation().getZ();

                Location particleLocation = new Location(start.getWorld(), x, y, z);

                // Spawn the particle
                start.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 0, 0, 0, 0, 0,  new Particle.DustOptions(Color.BLACK, 1));
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
        return 5000;
    }

    @Override
    public String getName() {
        return "test";
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
}
*/