package hu.enderboyhun.pl.jumppad;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.Material.AIR;
import static org.bukkit.Material.WOOD_PLATE;

public class JumpPad implements ConfigurationSerializable {

    private static List<JumpPad> pads = new ArrayList<>();
    private Location loc;
    private double forceH, forceV;

    public JumpPad(Location loc) {
        this.loc = loc;
        this.forceH = 1;
        this.forceV = 1;
    }

    public JumpPad(Map<String, Object> map) {
        forceV = (double) map.get("fVertical");
        forceH = (double) map.get("fHorizontal");
        loc = (Location) map.get("location");
    }

    static public JumpPad getPadAtLoc(Location loc) {
        for (JumpPad pad : pads) {
            if (pad.loc.equals(loc))
                return pad;
        }
        return null;
    }

    static public void createPad(Location loc) {
        if (getPadAtLoc(loc) == null) {
            pads.add(new JumpPad(loc));
            savePads();
        }
    }

    static public void modifyPadAdd(JumpPad jp, double v, double h) {
        if (jp == null) return;
        jp.forceV += v;
        jp.forceH += h;
        if (jp.forceV > 5) jp.forceV = 5;
        if (jp.forceV < 1 && v < 0) jp.forceV = 0;
        if (jp.forceV < 1 && v > 0) jp.forceV = 1;
        if (jp.forceH > 5) jp.forceH = 5;
        if (jp.forceH < -5) jp.forceH = -5;
        savePads();
    }

    static public void deletePad(JumpPad jp) {
        if (jp == null) return;
        jp.loc.getWorld().dropItem(jp.loc, new ItemStack(jp.loc.getBlock().getType()));
        jp.loc.getBlock().setType(AIR);
        pads.remove(jp);
        savePads();
    }

    static public void loadPads(List<?> objects) {
        pads.clear();
        for (Object o : objects) {
            if (o instanceof JumpPad) pads.add((JumpPad) o);
        }
    }

    private static void savePads() {
        Main.config.savePads(pads);
    }

    public double getForceH() {
        return forceH;
    }

    public double getForceV() {
        return forceV;
    }

    public String getForcesText() {
        return MessageFormat.format("§6Vertical: §e{0} §1- §6Horizontal: §e{1}§r", forceV, forceH);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("fVertical", forceV);
        map.put("fHorizontal", forceH);
        map.put("location", loc);

        return map;
    }
}
