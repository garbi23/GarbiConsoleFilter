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

import static garbi.var.AllVar.*;

public class GarbiConsoleFilter extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("[-Garbi-]GarbiConsoleFilter 활성화 완료");
        ((Logger) LogManager.getRootLogger()).addFilter(new LogFilter());
        createConfig();
        getCommand("gcf").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        Player p = (Player) commandSender;
        if (command.getName().equalsIgnoreCase("gcf") && p.isOp()) {
            if (args.length == 0) {
                p.sendMessage("/gcf reload");
            } else if (args[0].equals("reload")) {
                if (args.length == 1) {
                    p.sendMessage("/gcf reload");
                } else {
                    list = (ArrayList<String>)customConfig[0].getList("FilterList");
                    if(list == null){
                        list = new ArrayList<String>();
                    }
                    p.sendMessage("콘솔필터가 리로드 되었습니다");
                }
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