package club.quar.util.item;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.*;
import java.util.stream.Stream;

@Getter
public class ItemBuilder {

    private final Material material;
    private final List<String> lore = new ArrayList<>();
    private final List<ItemFlag> itemFlags = new ArrayList<>();
    private final Map<Enchantment, Integer> enchantMap = new HashMap<>();
    private MaterialData data;
    private short durability = -1;
    private int amount;
    private String name;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    /**
     * Set the material data of the item
     * @param data The mmaterial data
     * @return The current ItemBuilder instance
     */
    public ItemBuilder setData(MaterialData data) {
        this.data = data;
        return this;
    }

    /**
     * Sets the amount of items to be generated
     * @param amount The quantity of the ItemStack
     * @throws IllegalArgumentException If amount is greater than 64 or less than 0
     * @return The current ItemBuilder instance
     */
    public ItemBuilder setAmount(int amount) throws IllegalArgumentException {
        if (amount > 64 || amount < 0) {
            throw new IllegalArgumentException("Amount cannot be greater than 64 or less than 1!");
        }
        this.amount = amount;
        return this;
    }

    /**
     * Set the name of the current ItemStack
     * @param name The target name, color-code friendly
     * @return The current ItemBuilder instance
     */
    public ItemBuilder setName(String name) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        return this;
    }

    /**
     * Set the lore of the current ItemStack
     * @param lore The target lore, color-code friendly
     * @return The current ItemBuilder instance
     */
    public ItemBuilder addLore(String... lore) {
        Stream.of(lore).map(string -> ChatColor.translateAlternateColorCodes('&', string))
                .forEach(this.lore::add);
        return this;
    }

    /**
     * Add an enchantment onto the current ItemStack.
     * If the ItemStack already has that enchantment present, it will be increased
     * by the amount parameter.
     * @param enchantment The target enchantment
     * @param amount The amount for the enchantment level to be increased by
     * @throws IllegalArgumentException If amount is less than 0
     * @return The current ItemBuilder instance
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be less than 0!");
        }
        amount += enchantMap.getOrDefault(enchantment, 0);
        enchantMap.put(enchantment, amount);
        return this;
    }

    /**
     * Set the amount of a certain enchant on the ItemStack
     * If the ItemStack already has that enchantment applied,
     * it will be overridden by the amount parameter.
     * @param enchantment The target enchantment
     * @param amount The target amount
     * @return The current ItemBuilder instance
     */
    public ItemBuilder setEnchant(Enchantment enchantment, int amount) {
        enchantMap.put(enchantment, amount);
        return this;
    }

    /**
     * Add ItemFlags to the current item
     * @param flags The item flags
     * @return The current ItemBuilder instance
     */
    public ItemBuilder addFlags(ItemFlag... flags) {
        itemFlags.addAll(Arrays.asList(flags));
        return this;
    }

    /**
     * Set the durability of the ItemStack
     * @param durability The target durability
     * @return The current ItemBuilder instance
     */
    public ItemBuilder setDurability(short durability) {
        this.durability = durability;
        return this;
    }

    /**
     * Generate the ItemStack
     * @return An ItemStack object
     */
    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();

        if (data != null) {
            itemStack.setData(data);
        }
        if (durability != -1) {
            itemStack.setDurability(durability);
        }
        if (name != null) {
            meta.setDisplayName(name);
        }
        if (lore.size() > 0) {
            meta.setLore(lore);
        }
        if (itemFlags.size() > 0) {
            itemFlags.forEach(meta::addItemFlags);
        }
        for (Map.Entry<Enchantment, Integer> entry : enchantMap.entrySet()) {
            itemStack.addUnsafeEnchantment(entry.getKey(), entry.getValue());
        }

        itemStack.setItemMeta(meta);

        return itemStack;
    }
}

