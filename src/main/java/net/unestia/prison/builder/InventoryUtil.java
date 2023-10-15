package net.unestia.prison.builder;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class InventoryUtil {

    public void remove(Inventory inventory, ItemStack material, Integer amount) {
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && itemStack.getType() == material.getType()) {
                int newamount = itemStack.getAmount() - amount;
                if (newamount > 0) {
                    itemStack.setAmount(newamount);
                    break;
                }
                inventory.remove(itemStack);
                amount = -newamount;
                if (amount == 0) {
                    break;
                }
            }
        }
    }

    public void remove(Inventory inventory, ItemStack material, String localizedName, Integer amount) {
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && itemStack.getType() == material.getType()) {
                if (Objects.requireNonNull(itemStack.getItemMeta()).getLocalizedName().equals(localizedName)) {
                    int newamount = itemStack.getAmount() - amount;
                    if (newamount > 0) {
                        itemStack.setAmount(newamount);
                        break;
                    }
                    inventory.remove(itemStack);
                    amount = -newamount;
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }
    }

    public boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

}
