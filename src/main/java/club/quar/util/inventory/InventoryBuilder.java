package club.quar.util.inventory;

import club.quar.util.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder {

    private final Inventory inventory;

    /**
     * Create a new InventoryBuilder from the specified arguments
     * @param name The name of the inventory (color code friendly)
     * @param size The size of the inventory (slots, not rows)
     */
    public InventoryBuilder(String name, int size) {
        this.inventory = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', name));
    }

    /**
     * Create an instance of InventoryBuilder directly from an Inventory
     * @param inventory The target Inventory
     */
    public InventoryBuilder(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Set a specified slot with an ItemStack
     * @param item The target ItemStack
     * @param slot The target slot
     * @return The current InventoryBuilder instance
     */
    public InventoryBuilder setItem(ItemStack item, int slot) {
        inventory.setItem(slot, item);
        return this;
    }

    /**
     * Set a specified slot with a built ItemBuilder
     * @param item The target ItemBuilder
     * @param slot THe target slot
     * @return The current InventoryBuilder instance
     */
    public InventoryBuilder setItem(ItemBuilder item, int slot) {
        setItem(item.build(), slot);
        return this;
    }

    /**
     * Set a range of slots to the same item
     * @param itemBuilder The target ItemBuilder
     * @param begin The beginning of the range
     * @param end The end of the range
     * @return The current InventoryBuilder instance
     */
    public InventoryBuilder setRange(ItemBuilder itemBuilder, int begin, int end) {
        setRange(itemBuilder.build(), begin, end);
        return this;
    }

    /**
     * Set a range of slots to the same item
     * @param itemStack The target ItemStack
     * @param begin The beginning of the range
     * @param end The end of the range
     * @return The current InventoryBuilder instanc e
     */
    public InventoryBuilder setRange(ItemStack itemStack, int begin, int end) {
        for(int i = begin; i < end; i++) {
            inventory.setItem(i, itemStack);
        }
        return this;
    }

    /**
     * Clear the inventory
     * @return The current InventoryBuilder instance
     */
    public InventoryBuilder clear() {
        inventory.clear();
        return this;
    }

    public Inventory build() {
        return inventory;
    }
}
