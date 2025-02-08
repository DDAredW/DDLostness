package lunadev.ddlostness;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements org.bukkit.command.CommandExecutor, Listener {
    private final DDLostness plugin;

    public CommandHandler(DDLostness plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ddlostness.give")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("messages.no_permission")));
                return true;
            }

            if (args.length == 2) {
                String targetName = args[0];
                int amount;

                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("messages.invalid_amount")));
                    return true;
                }

                Player target = Bukkit.getPlayer(targetName);
                if (target != null) {
                    ItemStack item = new ItemStack(Material.IRON_BARS, amount);
                    ItemMeta meta = item.getItemMeta();

                    String itemName = plugin.getConfig().getString("item.name");
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemName));

                    List<String> lore = new ArrayList<>();
                    for (String loreLine : plugin.getConfig().getStringList("item.lore")) {
                        lore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
                    }
                    meta.setLore(lore);

                    item.setItemMeta(meta);
                    target.getInventory().addItem(item);

                    String giveMessage = plugin.getConfig().getString("messages.given_item")
                            .replace("{amount}", String.valueOf(amount))
                            .replace("{target}", targetName);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', giveMessage));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("messages.player_not_found")));
                }
                return true;
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("messages.usage")));
                return true;
            }
        }
        return false;
    }
}