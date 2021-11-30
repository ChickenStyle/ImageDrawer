package me.chickenstyle.imagedrawer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftBlock;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class ColorManager {

    private static ColorManager instance;
    private HashMap<Color, Material> colors;
    private List<String> blockedTypes = (List<String>) Main.getInstance().getConfig().get("blockedTypes");

    public static ColorManager getInstance() {
        instance = instance == null ? new ColorManager() : instance;
        return instance;
    }


    public ColorManager(){
        this.colors = new HashMap<>();
        A: for (Material material : Material.values()) {
            String mat = material.toString();
           for (String str : blockedTypes){
               if (mat.contains(str)) {
                   continue A;
               }
           }

            if (material.isBlock() && material.isOccluding() && material.isSolid()) {
                Main.getInstance().getServer().getWorld("world").getBlockAt(69,1,69).setType(material);
                Block block = Main.getInstance().getServer().getWorld("world").getBlockAt(69,1,69);

                int rgb = ((CraftBlock) block).getNMS().getBlock().s().al;
                Color color = new Color(rgb);

                colors.put(color,material);

            }
        }
    }

    public Color matchColorForExistingColor(Color origin) {
        if (colors.keySet().contains(origin)) return origin;

        ColorDifference difference = null;
        Color closestColor = null;
        for (Color color : colors.keySet()) {
            ColorDifference newDifference = new ColorDifference(origin.getRed() - color.getRed(),origin.getGreen() - color.getGreen(),origin.getBlue() - color.getBlue());
            if (difference == null || newDifference.getTotal() < difference.getTotal()) {
                difference = newDifference;
                closestColor = color;
            }
        }
        return closestColor;

    }
    public Material getBlocksFor(Color color) {
        color = matchColorForExistingColor(color);
        return colors.get(color);
    }

    private class ColorDifference {
        private int r,g,b;
        public ColorDifference(int r, int g,int b) {
            this.r = Math.abs(r);
            this.g = Math.abs(g);
            this.b = Math.abs(b);
        }

        public int getTotal() {
            return r + g + b;
        }
    }



}
