package lunadev.ddlostness;

import org.bukkit.plugin.java.JavaPlugin;

public class DDLostness extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("dlos").setExecutor(new CommandHandler(this));
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemUseListener(this), this);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }
}