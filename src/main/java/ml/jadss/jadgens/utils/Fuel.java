package ml.jadss.jadgens.utils;

import lombok.Getter;
import ml.jadss.jadgens.JadGens;
import ml.jadss.jadgens.nbt.NBTCompound;
import ml.jadss.jadgens.nbt.NBTItem;
import ml.jadss.jadgens.nbt.NbtApiException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Fuel {

    private Integer drops;
    private Integer type;

    public Fuel() {
        this.drops = null;
        this.type = null;
    }

    public Fuel(ItemStack item) {
        NBTCompound nbtCompound = new NBTItem(item);
        if (nbtCompound.getBoolean("JadGens_fuel")) {
            this.type = nbtCompound.getInteger("JadGens_fuelType");
            this.drops = nbtCompound.getInteger("JadGens_drops");
        } else {
            this.drops = null;
            this.type = null;
        }
    }

    public boolean isFuel(ItemStack item) {
        if (item == null) return false;
        NBTCompound nbtCompound = new NBTItem(item);
        return nbtCompound.hasKey("JadGens_fuel");
    }

    public ItemStack createItem(int id) {
        ItemStack fuel = new ItemStack(Material.getMaterial(JadGens.getInstance().getConfig().getString("fuels." + id + ".item.material")), 1, (short) JadGens.getInstance().getConfig().getInt("fuels." + id + ".item.damage"));
        ItemMeta meta = fuel.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', JadGens.getInstance().getConfig().getString("fuels." + id + ".displayName")));
        List<String> lore = new ArrayList<>();
        for (String line : JadGens.getInstance().getConfig().getStringList("fuels." + id + ".lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        if (JadGens.getInstance().getConfig().getBoolean("fuels." + id + ".glow")) {
            meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        meta.setLore(lore);
        fuel.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(fuel);
        try {
            nbtItem.setBoolean("JadGens_fuel", true);
            nbtItem.setInteger("JadGens_fuelType", id);
            nbtItem.setInteger("JadGens_drops", JadGens.getInstance().getConfig().getInt("fuels." + id + ".drops"));
        } catch (NbtApiException e) {
            return null;
        }

        return nbtItem.getItem();
    }
}
