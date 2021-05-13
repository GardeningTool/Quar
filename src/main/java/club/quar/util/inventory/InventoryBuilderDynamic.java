package club.quar.util.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class InventoryBuilderDynamic extends InventoryBuilder {

    /**
     * Create a new InventoryBuilder from the specified arguments
     * @param name The name of the inventory (color code friendly)
     * @param size The size of the inventory (slots, not rows)
     */
    public InventoryBuilderDynamic(String name, int size) {
        super(name, size);
    }

    /**
     * A method to be overridden in subclasses where the inventory will
     * actually be built.
     * @return The completed inventory
     */
    public abstract Inventory build(Player player);

}
