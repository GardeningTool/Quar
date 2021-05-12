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

    public ItemBuilder setData(MaterialData data) {
        this.data = data;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        Stream.of(lore).map(string -> ChatColor.translateAlternateColorCodes('&', string))
                .forEach(this.lore::add);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int amount) {
        amount += enchantMap.getOrDefault(enchantment, 0);
        enchantMap.put(enchantment, amount);
        return this;
    }

    public ItemBuilder setEnchant(Enchantment enchantment, int amount) {
        enchantMap.put(enchantment, amount);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        itemFlags.addAll(Arrays.asList(flags));
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        this.durability = durability;
        return this;
    }

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
        for(Map.Entry<Enchantment, Integer> entry : enchantMap.entrySet()) {
            itemStack.addUnsafeEnchantment(entry.getKey(), entry.getValue());
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}

