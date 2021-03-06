package com.hm.achievement.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import com.hm.achievement.AdvancedAchievements;
import com.hm.achievement.category.NormalAchievements;

/**
 * Listener class to deal with MaxLevel achievements.
 * 
 * @author Pyves
 *
 */
public class AchieveXPListener extends AbstractListener implements Listener {

	public AchieveXPListener(AdvancedAchievements plugin) {

		super(plugin);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerExpChange(PlayerLevelChangeEvent event) {

		Player player = event.getPlayer();

		NormalAchievements category = NormalAchievements.LEVELS;
		if (!shouldEventBeTakenIntoAccount(player, category)) {
			return;
		}

		int previousMaxLevel = plugin.getPoolsManager().getAndIncrementStatisticAmount(category, player, 0);

		if (event.getNewLevel() <= previousMaxLevel) {
			return;
		}

		updateStatisticAndAwardAchievementsIfAvailable(player, category, event.getNewLevel() - previousMaxLevel);
	}
}
