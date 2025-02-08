package lunadev.ddlostness;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemUseListener implements Listener {
    private final DDLostness plugin;

    public ItemUseListener(DDLostness plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.getType() == Material.IRON_BARS) {
            Player player = event.getPlayer();

            // Звуковой эффект из конфига
            String soundEffect = plugin.getConfig().getString("effects.sound.type");
            float volume = (float) plugin.getConfig().getDouble("effects.sound.volume");
            float pitch = (float) plugin.getConfig().getDouble("effects.sound.pitch");
            player.getWorld().playSound(player.getLocation(), Sound.valueOf(soundEffect), volume, pitch);

            // Параметры эффектов из конфига
            int duration = plugin.getConfig().getInt("effects.duration") * 20; // конвертируем в тики
            int radius = plugin.getConfig().getInt("effects.radius");

            // Применяем эффекты
            for (String effectName : plugin.getConfig().getConfigurationSection("effects.potion_effects").getKeys(false)) {
                PotionEffectType type = PotionEffectType.getByName(
                        plugin.getConfig().getString("effects.potion_effects." + effectName + ".type"));
                int level = plugin.getConfig().getInt("effects.potion_effects." + effectName + ".level") - 1;

                if (type != null) {
                    player.getNearbyEntities(radius, radius, radius).stream()
                            .filter(entity -> entity instanceof Player)
                            .map(entity -> (Player) entity)
                            .forEach(p -> p.addPotionEffect(new PotionEffect(type, duration, level)));
                }
            }

            // Уменьшаем количество предметов
            item.setAmount(item.getAmount() - 1);
        }
    }
}