package hu.enderboyhun.pl.jumppad.Listener;

import hu.enderboyhun.pl.jumppad.JumpPad;
import hu.enderboyhun.pl.jumppad.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.Material.*;
import static org.bukkit.event.block.Action.*;

public class Set implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent pie) {
        Player p = pie.getPlayer();
        Action a = pie.getAction();

        if (a == LEFT_CLICK_AIR || a == RIGHT_CLICK_AIR || a == PHYSICAL) return;

        Material handItem = p.getInventory().getItemInMainHand().getType();
        Location loc = pie.getClickedBlock().getLocation();

        Material m = loc.getBlock().getType();
        if (!(m == WOOD_PLATE || m == STONE_PLATE ||m == IRON_PLATE ||m == GOLD_PLATE)) return;

        JumpPad jp = JumpPad.getPadAtLoc(loc);

        if (jp != null) pie.setCancelled(true);
        if (!p.hasPermission("jumpPad.set")) return;

        if (handItem.equals(REDSTONE)) {
            if (a == RIGHT_CLICK_BLOCK) {
                if (jp == null) p.sendMessage(Main.prefix + "§4This is not JumpPad.");
                else p.sendMessage(Main.prefix + jp.getForcesText());
            } else if (a == LEFT_CLICK_BLOCK) {
                JumpPad.deletePad(jp);
            }
        }

        if (handItem.equals(TRIPWIRE_HOOK)) {
            if (jp == null) {
                if (a != RIGHT_CLICK_BLOCK) return;
                JumpPad.createPad(loc);
                p.sendMessage(Main.prefix + "§aJumpPad success created.");
            } else {
                if (p.isSneaking()) {
                    if (a == RIGHT_CLICK_BLOCK) JumpPad.modifyPadAdd(jp, -.25, 0);
                    if (a == LEFT_CLICK_BLOCK) JumpPad.modifyPadAdd(jp, 0, -.25);
                } else {
                    if (a == RIGHT_CLICK_BLOCK) JumpPad.modifyPadAdd(jp, +.25, 0);
                    if (a == LEFT_CLICK_BLOCK) JumpPad.modifyPadAdd(jp, 0, +.25);
                }
                p.sendMessage(Main.prefix + jp.getForcesText());
            }
        }
    }
}