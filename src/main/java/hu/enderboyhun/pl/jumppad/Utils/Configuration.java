package hu.enderboyhun.pl.jumppad.Utils;

import hu.enderboyhun.pl.jumppad.JumpPad;
import hu.enderboyhun.pl.jumppad.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Configuration {
    private File pads_file;
    private YamlConfiguration pads_config;

    public Configuration() {
        pads_file = new File(Main.main.getDataFolder(), "pads.yml");
        pads_config = YamlConfiguration.loadConfiguration(pads_file);

        loadPads();
        try {
            pads_config.save(pads_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPads() {
        List<?> objects = pads_config.getList("Pads");
        if (objects == null) return;
        JumpPad.loadPads(objects);
    }

    public void savePads(List<JumpPad> pads) {
        pads_config.set("Pads", pads);
        try {
            pads_config.save(pads_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
