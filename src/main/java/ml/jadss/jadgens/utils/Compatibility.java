package ml.jadss.jadgens.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class Compatibility {

    public Compatibility() {
    }

    public String aDontUseThisMethod() {
        return "DON'T USE THIS METHOD! IT IS FOR COMPATIBILITY FOR ALL THE VERSION SUPPORTED BY THE PLUGIN!";
    }

    public String thisIsForCompatbility() {
        return "DON'T USE THIS METHOD! IT IS FOR COMPATIBILITY FOR ALL THE VERSION SUPPORTED BY THE PLUGIN!";
    }

    public Material getMaterial(String block) {
        if (block.equalsIgnoreCase("STAINED_GLASS")) {
            return glassPane();
        }
        return Material.getMaterial(block);
    }

    private Material glassPane() {
        try {
            return Material.valueOf("GLASS_PANE");
        } catch (IllegalArgumentException exception) {
            return Material.STAINED_GLASS_PANE;
        }
    }

    public String getTitle(Inventory inv, InventoryView invView) {
        try {
            return inv.getName();
        } catch (NoSuchMethodError e) {
            return invView.getTitle();
        }
    }
}
