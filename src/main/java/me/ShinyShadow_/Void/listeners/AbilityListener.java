//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.ShinyShadow_.Void.listeners;


import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.ShinyShadow_.Void.ability.voidability.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class AbilityListener implements Listener {
    private Player player;
    private Player hitPlayer;
    public static boolean chainExists = false;
    @EventHandler
    public void onShift(PlayerToggleSneakEvent event) {
        player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (!VoidShot.exists && bPlayer.getBoundAbilityName().equalsIgnoreCase("VoidShot") && bPlayer.canBend(CoreAbility.getAbility(VoidShot.class))) {
            new VoidShot(player);
        }

        if (bPlayer.getBoundAbilityName().equalsIgnoreCase("BlackHole") && bPlayer.canBend(CoreAbility.getAbility(BlackHole.class))) {
            new BlackHole(player);
        }

        if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Implode") && bPlayer.canBend(CoreAbility.getAbility(Implode.class))) {
            new Implode(player);
        }

        if (WormHole.wormHoleExists == false && bPlayer.getBoundAbilityName().equalsIgnoreCase("WormHole") && bPlayer.canBend(CoreAbility.getAbility(WormHole.class))) {
            new WormHole(player);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if (event.getAction() == Action.LEFT_CLICK_AIR) {

            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("VoidShot")) {
                VoidShot.shoot = true;
            }
                if (bPlayer.getBoundAbilityName().equalsIgnoreCase("VoidChain") && VoidChain.chainTarget == null ) {
                    for (Entity target : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), 5)) {
                        if (chainExists == false && target instanceof LivingEntity && !target.getUniqueId().equals(player.getUniqueId())) {
                            new VoidChain(player);
                            chainExists = true;
                        }
                    }
                }
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("VoidChain") && VoidChain.chainTarget != null) {
                    VoidChain.stop = true;
            }
        }
    }
    @EventHandler
    public void onDamageTaken(EntityDamageEvent event) {
        if (player != null && event.getEntityType() == EntityType.PLAYER && event.getEntity().getUniqueId().equals(player.getUniqueId()) && VoidChain.chainTarget != null) {
                VoidChain.stop = true;
        }
    }
}