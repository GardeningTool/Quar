package dev.gardeningtool.example.inventory;

import club.quar.util.inventory.InventoryBuilderDynamic;
import club.quar.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ExampleInventory extends InventoryBuilderDynamic {

    /**
     * Creates a new Inventory with the name "Example" and 9 slots
     */
    public ExampleInventory() {
        super("&4&lExample", 9);
    }

    @Override
    public Inventory build(Player player) {
        setItem(new ItemBuilder(Material.STONE).setName(player.getName()), 0);
        Inventory inventory = build();
        clear();
        return inventory;
    }
}
