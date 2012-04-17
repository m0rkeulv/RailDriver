package ca.mcpnet.RailDriver;

import org.bukkit.Material;
import org.bukkit.material.Lever;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class RailDriverBlockListener implements Listener {

	private RailDriver plugin;

	public RailDriverBlockListener(RailDriver instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.hasBlock()) {
			Block interactedBlock = event.getClickedBlock();
			if(interactedBlock != null && interactedBlock.getType() == Material.LEVER) {
				if(interactedBlock.getBlockPower() > 0) {
					// Block is already powered, we're about to turn it off
					RailDriverTask task = plugin.findRailDriverTask(interactedBlock);
					if(task != null) {
						task.deactivate();
					}
				} else {
					// Block isn't powered, this interaction will turn it on
					if(plugin.isRailDriver(interactedBlock)) {
						RailDriverTask task = plugin.findCreateRailDriverTask(interactedBlock);
						task.activate(event.getPlayer());
					}
				}
			}
		}
	}

}
