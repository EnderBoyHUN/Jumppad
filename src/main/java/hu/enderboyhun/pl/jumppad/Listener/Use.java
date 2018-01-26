package hu.enderboyhun.pl.jumppad.Listener;

import hu.enderboyhun.pl.jumppad.JumpPad;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static org.bukkit.GameMode.SPECTATOR;
import static org.bukkit.Material.*;
import static org.bukkit.Sound.ENTITY_FIREWORK_LAUNCH;

public class Use implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent pme) {
        Player p = pme.getPlayer();
        Location loc = p.getLocation().getBlock().getLocation();

        Material m = loc.getBlock().getType();
        if (!(m == WOOD_PLATE || m == STONE_PLATE ||m == IRON_PLATE ||m == GOLD_PLATE)) return;

        JumpPad jp = JumpPad.getPadAtLoc(loc);
        if (jp == null) return;

        if (!p.hasPermission("jumpPad.use")) return;
        if (p.getGameMode() == SPECTATOR || p.isFlying() || p.isSneaking()) return;

        p.setVelocity(p.getLocation().getDirection().multiply(jp.getForceH()));
        p.setVelocity(p.getVelocity().setY(jp.getForceV()));
        p.playSound(p.getLocation(), ENTITY_FIREWORK_LAUNCH, 1.5f, 1);
        p.setFallDistance(-999);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent bbe) {
        Location loc = bbe.getBlock().getLocation().clone();
        if (JumpPad.getPadAtLoc(loc.add(0, 1, 0)) != null) {
            bbe.setCancelled(true);
        }

        boolean _x = false;
        for (int i = loc.getBlockY(); i < 256; i++) {
            loc.setY(i);
            if (JumpPad.getPadAtLoc(loc) != null && _x) bbe.setCancelled(true);
            if (loc.getBlock().getType().hasGravity()) _x = true;
            else return;
        }
    }
}
