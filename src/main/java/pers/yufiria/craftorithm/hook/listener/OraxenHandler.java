package pers.yufiria.craftorithm.hook.listener;

import io.th0rgal.oraxen.api.events.OraxenItemsLoadedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import pers.yufiria.craftorithm.Craftorithm;

public enum OraxenHandler implements Listener {

    INSTANCE;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onOraxenLoaded(OraxenItemsLoadedEvent event) {
        Craftorithm.instance().reloadPlugin();
//        RecipeManager.INSTANCE.reloadRecipeManager();
//        OtherPluginsListenerProxy.INSTANCE.reloadOtherPluginsListener();
    }

}