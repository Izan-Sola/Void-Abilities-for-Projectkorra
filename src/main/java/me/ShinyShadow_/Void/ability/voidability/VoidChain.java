package me.ShinyShadow_.Void.ability.voidability;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.ShinyShadow_.Void.Void;
import me.ShinyShadow_.Void.ability.api.VoidAbility;
import me.ShinyShadow_.Void.listeners.AbilityListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class VoidChain extends VoidAbility  {

private Listener listener;

private Location playerLoc;
private Location targetLoc;
private Location particleLoc;
private Location rightSide;


private double chainDuration = 10D;

private int COOLDOWN;
private double DAMAGE;

private boolean noEffects = true;

public static Entity chainTarget = null;
public static boolean stop = false;
    public VoidChain(Player player) {
        super(player);
        noEffects = true;
        this.COOLDOWN = Void.plugin.getConfig().getInt("Abilities.Void.VoidChain.Cooldown");
        this.DAMAGE = Void.plugin.getConfig().getDouble("Abilities.Void.VoidChain.Damage");
        start();
    }

    @Override
    public void progress() {
        playerLoc = player.getLocation();
        if (!bPlayer.canBendIgnoreBindsCooldowns(this) || stop == true) {
            stop();
        }
        if (bPlayer.canBend(this)) {

       for (Entity target : GeneralMethods.getEntitiesAroundPoint(playerLoc, 5)) {
                if (target instanceof LivingEntity && !target.getUniqueId().equals(this.player.getUniqueId()))

                    if (chainTarget == null) {
                        chainTarget = target;
                        bPlayer.addCooldown(this);
                    }
            }
        }
            rightSide = GeneralMethods.getRightSide(playerLoc, 0.6);
            if (bPlayer.canBendIgnoreBindsCooldowns(this)) {

                if (chainTarget != null) {
                    targetLoc = chainTarget.getLocation();

                    for (int i = 0; i <= 30; i++) {
                        double progress = (double) i / 30;
                        Location interpolatedLocation = rightSide.clone().add(targetLoc.clone().subtract(rightSide).multiply(progress));
                        player.getWorld().spawnParticle(Particle.DUST, interpolatedLocation.add(0, 1, 0), 1, 0, 0, 0, 0, new Particle.DustOptions(org.bukkit.Color.BLACK, 1));
                        player.getWorld().spawnParticle(Particle.ASH, interpolatedLocation.add(0, 0, 0), 2, 0, 0, 0, 1);
                    }

                    for (int d = 0; d <= 20; d += 1) {
                        particleLoc = new Location(player.getWorld(), targetLoc.getX(), targetLoc.getY()+1, targetLoc.getZ());
                        particleLoc.setX(targetLoc.getX() + Math.cos(d) * 0.6);
                        particleLoc.setZ(targetLoc.getZ() + Math.sin(d) * 0.6);
                        player.getWorld().spawnParticle(Particle.DUST, particleLoc, 0, 0, 0, 0, 0, new Particle.DustOptions(org.bukkit.Color.BLACK, 1));
                        player.getWorld().spawnParticle(Particle.BLOCK_CRUMBLE, particleLoc, 1, 0.2, 0.2, 0.2, 1, Material.BEACON.createBlockData());
                    }
                    chainDuration -= 0.09D;
                   // player.sendMessage("s: " + chainDuration);
                    if(chainDuration <= 10) {
                        ((LivingEntity) chainTarget).addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20, 2));
                    }
                    if(chainDuration <= 7) {
                        ((LivingEntity) chainTarget).addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 20, -3));

                    }
                    if(chainDuration <= 4) {
                        if(noEffects) {
                            ((LivingEntity) chainTarget).addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 60, -3));
                            ((LivingEntity) chainTarget).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
                            ((LivingEntity) chainTarget).addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 60, 1));
                            noEffects = false;
                        }
                    }
                    if(chainDuration <= 0) {
                        DamageHandler.damageEntity(chainTarget, DAMAGE, this);
                        bPlayer.addCooldown(this);
                        getCooldown();
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
        return this.COOLDOWN;
    }

    @Override
    public String getName() {
        return "VoidChain";
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

        chainDuration = 0;

        stop = false;
        chainTarget = null;
        AbilityListener.chainExists = false;
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

        return  ChatColor.BOLD + " Left click near an entity to chain it. The longer the time " +
                "the entity is chained, the worst effects it will be inflicted with. " +
                "After the chain reaches max duration, it will break and cause damage. " +
                "The chain will break if you receive damage. " +
                "If broken this way, it wont damage the chained entity."  + ChatColor.DARK_GRAY;
    }

    public String getInstructions(){
        return "Left click air to chain an entity.";
    }
}
