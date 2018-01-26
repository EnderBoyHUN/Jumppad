package hu.enderboyhun.pl.jumppad;

import hu.enderboyhun.pl.jumppad.Listener.Set;
import hu.enderboyhun.pl.jumppad.Listener.Use;
import hu.enderboyhun.pl.jumppad.Utils.Configuration;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    static public final String prefix = "§o§3[§bJumpPad§3] §r";
    static public Main main;
    static public Configuration config;

    @Override
    public void onEnable() {
        main = this;
        ConfigurationSerialization.registerClass(Location.class, "Location");
        ConfigurationSerialization.registerClass(JumpPad.class, "JumpPad");
        config = new Configuration();

        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new Use(), this);
        pm.registerEvents(new Set(), this);
    }

    @Override
    public void onDisable() {

    }
}
