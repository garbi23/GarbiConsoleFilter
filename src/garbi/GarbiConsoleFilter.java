package garbi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static garbi.var.AllVar.*;


public class GarbiConsoleFilter extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("[-Garbi-]BLDTpsSystem 활성화 완료");
        ((Logger) LogManager.getRootLogger()).addFilter(new garbi.LogFilter());
        createConfig();
    }


    private void createConfig() {
        customConfigFile[0] = new File(getDataFolder(), "Config.yml");
        if (!customConfigFile[0].exists()) {
            customConfigFile[0].getParentFile().mkdirs();
            saveResource("Config.yml", false);
        }
        customConfig[0] = new YamlConfiguration();
        try {
            customConfig[0].load(customConfigFile[0]);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}