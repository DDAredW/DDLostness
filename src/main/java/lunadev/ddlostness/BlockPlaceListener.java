package lunadev.ddlostness;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    private final DDLostness plugin;

    public BlockPlaceListener(DDLostness plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.IRON_BARS) {
            event.getPlayer().sendMessage(ChatColor.RED + plugin.getConfig().getString("messages.block_place_denied"));
            event.setCancelled(true);
        }
    }
}