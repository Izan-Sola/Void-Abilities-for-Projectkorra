package me.ShinyShadow_.Void.ability.voidability;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.SpiritualAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.MovementHandler;
import me.ShinyShadow_.Void.Void;
import me.ShinyShadow_.Void.ability.api.VoidAbility;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;

import static org.bukkit.Particle.*;

public class VoidCombustions extends VoidAbility implements ComboAbility {

    private Location currentLocation;
    private Location rightHandLoc;
    private Location leftHandLoc;

    private Vector direction;

    private boolean explode = false;

    private double distance = 0;
    private double radius = 0.5;
    private double DAMAGE;

    private int COOLDOWN;
    private int charges = 2;

    public VoidCombustions(Player player) {
        super(player);
        COOLDOWN = Void.plugin.getConfig().getInt("Abilities.Void.Combos.VoidCombustions.Cooldown");
        DAMAGE = Void.plugin.getConfig().getDouble("Abilities.Void.Combos.VoidCombustions.Damage");
        start();
    }

    @Override
    public void progress() {
        if (!bPlayer.getBoundAbilityName().equals("VoidChain")) {
            stop();
        }
        if (bPlayer.canBendIgnoreBinds(this)) {
            rightHandLoc = GeneralMethods.getRightSide(player.getLocation(), 2);
            leftHandLoc = GeneralMethods.getLeftSide(player.getLocation(), 2);
            if (charges == 2) {
                player.getWorld().spawnParticle(Particle.DUST, rightHandLoc.add(0, 1, 0), 15, 0.25, 0.25, 0.25, 0.6D, new DustOptions(Color.BLACK, 1));
                player.getWorld().spawnParticle(Particle.DUST, leftHandLoc.add(0, 1, 0), 15, 0.25, 0.25, 0.25, 0.6D, new DustOptions(Color.BLACK, 1));
                player.getWorld().spawnParticle(ELECTRIC_SPARK, rightHandLoc.add(0, 0, 0), 6, 0.2, 0.2, 0.2, 0.6D);
                player.getWorld().spawnParticle(ELECTRIC_SPARK, leftHandLoc.add(0, 0, 0), 6, 0.2, 0.2, 0.2, 0.6D);
            }
            if (charges == 1) {
                player.getWorld().spawnParticle(Particle.DUST, leftHandLoc.add(0, 1, 0), 15, 0.25, 0.25, 0.25, 0.6D, new DustOptions(Color.BLACK, 1));
                player.getWorld().spawnParticle(ELECTRIC_SPARK, leftHandLoc.add(0, 0, 0), 6, 0.2, 0.2, 0.2, 0.6D);
            }
            for (int d = 0; d <= 50; d += 1) {
                Location ringLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 0.5, player.getLocation().getZ());
                ringLoc.setX(player.getLocation().getX() + Math.cos(d) * 2);
                ringLoc.setZ(player.getLocation().getZ() + Math.sin(d) * 2);
                player.getWorld().spawnParticle(Particle.DUST, ringLoc, 0, 0, 0, 0, 0, new DustOptions(org.bukkit.Color.BLACK, 1));
            }
            if (!player.isSneaking() && !explode) {
                currentLocation = player.getLocation();
                direction = currentLocation.getDirection();
            }
            if (player.isSneaking()) {
                direction.multiply(1.025);
                //player.getWorld().spawnParticle(Particle.FALLING_DUST, eyeLoc, 60, 0.2, 0.2, 0.2, 0.06D, Material.DRAGON_EGG.createBlockData());
                player.getWorld().spawnParticle(FALLING_DUST, currentLocation, 60, 0.1, 0.1, 0.1, 1D, Material.DRAGON_EGG.createBlockData());
                player.getWorld().spawnParticle(SQUID_INK, currentLocation, 5, 0.1, 0.1, 0.1, 0.2);
                player.getWorld().spawnParticle(ELECTRIC_SPARK, currentLocation, 30, 0.1, 0.1, 0.1, 1D);
                currentLocation.add(direction);
                distance += direction.length();

                explode = true;
            }
            if (!player.isSneaking() && explode) {

                Collection<Entity> nearbyEntities = player.getWorld().getNearbyEntities(currentLocation, 4, 4, 4);
                for (Entity entity : nearbyEntities) {
                    if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
                         DamageHandler.damageEntity(entity, DAMAGE, this);
                    }
                }
                for (int d = 0; d <= 30 + (30 * radius); d += 1) {
                    Location particleLoc = new Location(player.getWorld(), currentLocation.getX(), currentLocation.getY(), currentLocation.getZ());
                    particleLoc.setX(currentLocation.getX() + Math.cos(d) * radius);
                    particleLoc.setZ(currentLocation.getZ() + Math.sin(d) * radius);
                    player.getWorld().spawnParticle(Particle.DUST, particleLoc, 1, 0.2, 0.2, 0.2, 0.1, new DustOptions(org.bukkit.Color.BLACK, 1));

                    if(radius <4)  {
                    radius += 0.50;
                    }
                }
                    if (radius == 4) {
                        player.getWorld().spawnParticle(Particle.DUST, currentLocation, 50, 2, 1, 2, 0.3, new DustOptions(org.bukkit.Color.BLACK, 1));
                        player.getWorld().spawnParticle(SQUID_INK, currentLocation, 50, 2, 1, 2, 0.3);
                        player.getWorld().spawnParticle(ELECTRIC_SPARK, currentLocation, 80, 2, 1, 2, 0.1);

                        charges -= 1;
                        for (Entity entity : nearbyEntities) {
                            if (entity instanceof LivingEntity) {
                                DamageHandler.damageEntity(entity, DAMAGE, this);
                                ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2));
                                final MovementHandler mh = new MovementHandler((LivingEntity) entity, this);
                                mh.stopWithDuration(25, "* -- * -- * -- *");

                            }
                        }
                        player.getWorld().playSound(currentLocation, Sound.ENTITY_GENERIC_EXPLODE, 1.1F, 2F);
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.1F, 2F);
                        radius = 0.5;

                    }


                if (charges <= 0) {
                    stop();
                }
                explode = false;
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
        return COOLDOWN;
    }

    @Override
    public String getName() {
        return "VoidCombustions";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
        ProjectKorra.getCollisionInitializer().addComboAbility((CoreAbility)this);
    }

    @Override
    public void stop() {
        getCooldown();
        bPlayer.addCooldown(this);
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

        return ChatColor.BOLD + " Charge 2 beams of void energy that you can shoot " +
                "and explode, harming any entities in the area of effect " +
                "and impairing their movement for a brief moment. " +
                "Be careful! The explosion can also harm you!"  + ChatColor.DARK_GRAY;
    }
    public String getInstructions(){

        return "Void Shot (Left Click) > Void Chain (Tap Shift) " +
                "Shift to shoot, release to explode. ";
    }
    @Override
    public Object createNewComboInstance(Player player) {
        return new VoidCombustions(player);
    }

    @Override
    public ArrayList<ComboManager.AbilityInformation> getCombination() {
        ArrayList<ComboManager.AbilityInformation> combo = new ArrayList<>();
        combo.add(new ComboManager.AbilityInformation("VoidShot", ClickType.LEFT_CLICK));
        combo.add(new ComboManager.AbilityInformation("VoidChain", ClickType.SHIFT_DOWN));
        combo.add(new ComboManager.AbilityInformation("VoidChain", ClickType.SHIFT_UP));
        return combo;
    }
}
