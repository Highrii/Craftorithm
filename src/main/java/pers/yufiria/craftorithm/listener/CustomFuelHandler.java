package pers.yufiria.craftorithm.listener;

import crypticlib.listener.EventListener;
import crypticlib.util.InventoryViewHelper;
import crypticlib.util.ItemHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pers.yufiria.craftorithm.item.ItemManager;

@EventListener
public class CustomFuelHandler implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void putFuelOnClick(InventoryClickEvent event) {
        if (event.isCancelled())
            return;
        Inventory topInventory = InventoryViewHelper.getTopInventory(event);
        if (!(topInventory instanceof FurnaceInventory furnaceInventory))
            return;
        Inventory clickInv = event.getClickedInventory();
        ItemStack current = event.getCurrentItem();
        int slot = event.getSlot();
        switch (event.getClick()) {
            case LEFT:
            case RIGHT:
                if (!furnaceInventory.equals(clickInv)) {
                    return;
                }
                if (slot != 1)
                    return;
                ItemStack cursor = event.getCursor();
                if (ItemHelper.isAir(cursor))
                    return;
                if (cursor.getType().isFuel())
                    return;
                if (!ItemManager.INSTANCE.isCustomFuel(cursor))
                    return;
                event.setCancelled(true);
                if (cursor.isSimilar(current)) {
                    int canPlaceAmount = current.getMaxStackSize() - current.getAmount();
                    current.setAmount(current.getAmount() + Math.min(cursor.getAmount(), canPlaceAmount));
                    cursor.setAmount(cursor.getAmount() - canPlaceAmount);
                    event.setCurrentItem(current);
                    event.setCursor(cursor);
                } else {
                    event.setCursor(current);
                    event.setCurrentItem(cursor);
                }
                ((Player) event.getWhoClicked()).updateInventory();
                break;
            case SHIFT_LEFT:
            case SHIFT_RIGHT:
                if (furnaceInventory.equals(clickInv))
                    return;
                if (!ItemManager.INSTANCE.isCustomFuel(current))
                    return;
                if (ItemHelper.isAir(current))
                    return;
                if (!ItemHelper.isAir(furnaceInventory.getFuel())) {
                    ItemStack fuel = furnaceInventory.getFuel();
                    if (!current.isSimilar(fuel)) return;
                    int canPlaceAmount = fuel.getMaxStackSize() - fuel.getAmount();
                    fuel.setAmount(fuel.getAmount() + Math.min(current.getAmount(), canPlaceAmount));
                    furnaceInventory.setFuel(fuel);
                    current.setAmount(current.getAmount() - canPlaceAmount);
                    event.setCurrentItem(current);
                    return;
                } else {
                    event.setCancelled(true);
                    event.setCurrentItem(null);
                    furnaceInventory.setFuel(current);
                }
                ((Player) event.getWhoClicked()).updateInventory();
                break;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void setFuelBurnTime(FurnaceBurnEvent event) {
        if (event.isCancelled())
            return;
        ItemStack fuel = event.getFuel();
        Integer time = ItemManager.INSTANCE.matchCustomFuelBurnTime(fuel);
        if (time == null || time <= 0)
            return;
        event.setBurnTime(time);
    }

}
