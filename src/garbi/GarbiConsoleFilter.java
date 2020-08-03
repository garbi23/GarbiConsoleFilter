package garbi;

import garbi.filter.LogFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static garbi.filtervar.AllVar.*;

public class GarbiConsoleFilter extends JavaPlugin {

    private LogFilter logFilter;

    @Override
    public void onEnable() {
        getLogger().info("[-Garbi-]GarbiConsoleFilter 활성화 완료");
        createConfig();
        getCommand("gcf").setExecutor(this);
        logFilter = new LogFilter();
        ((Logger) LogManager.getRootLogger()).addFilter(logFilter);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        Player p = (Player) commandSender;
        if (command.getName().equalsIgnoreCase("gcf") && p.isOp()) {
            if (args.length == 0) {
                p.sendMessage("/gcf reload");
            } else if (args[0].equals("reload")) {
                    customConfig[0] = YamlConfiguration.loadConfiguration(customConfigFile[0]);
                    list = (ArrayList<String>)customConfig[0].getList("FilterList");
                    if(list == null){
                        list = new ArrayList<String>();
                    }
                    p.sendMessage("콘솔필터가 리로드 되었습니다");
            }
        }
        return false;
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