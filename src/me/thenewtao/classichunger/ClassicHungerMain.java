package me.thenewtao.classichunger;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import me.thenewtao.events.EventListener;

public class ClassicHungerMain extends JavaPlugin {

	public HashMap<String, Long> cooldown = new HashMap<String, Long>();
	public HashMap<Material, Integer> foodType = new HashMap<Material, Integer>();

	@Override
	public void onEnable() {

		foodType.put(Material.RABBIT_STEW, 10);

		foodType.put(Material.COOKED_BEEF, 8);
		foodType.put(Material.MUSHROOM_SOUP, 8);
		foodType.put(Material.PUMPKIN_PIE, 8);
		foodType.put(Material.GRILLED_PORK, 8);

		foodType.put(Material.COOKED_CHICKEN, 6);
		foodType.put(Material.COOKED_MUTTON, 6);
		foodType.put(Material.GOLDEN_CARROT, 6);

		foodType.put(Material.COOKED_FISH, 5);
		foodType.put(Material.COOKED_RABBIT, 5);
		foodType.put(Material.BAKED_POTATO, 5);

		foodType.put(Material.APPLE, 4);
		foodType.put(Material.GOLDEN_APPLE, 4);

		foodType.put(Material.CARROT, 3);
		foodType.put(Material.PORK, 3);
		foodType.put(Material.RAW_BEEF, 3);
		foodType.put(Material.RABBIT, 3);

		foodType.put(Material.MUTTON, 2);
		foodType.put(Material.MELON, 2);
		foodType.put(Material.COOKIE, 2);
		foodType.put(Material.RAW_CHICKEN, 2);
		foodType.put(Material.POISONOUS_POTATO, 2);
		foodType.put(Material.RAW_FISH, 2);

		foodType.put(Material.POTATO_ITEM, 1);
		Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
		File config = new File(getDataFolder(), "config.yml");
		if (!config.exists()) {
			saveConfig();
		}
	}

}
