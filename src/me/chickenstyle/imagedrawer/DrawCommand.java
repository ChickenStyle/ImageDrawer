package me.chickenstyle.imagedrawer;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class DrawCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {

        Player player = (Player) sender;
        String imageName = args[0];
        int width = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);

        File file = new File(Main.getInstance().getDataFolder() + "/images/" + imageName);

        if (file.exists()) {
            try {
                BufferedImage image = ImageIO.read(file);
                image = resize(image,width,height);
                World world = player.getWorld();
                for (int x = 0; x < width;x++) {
                    for (int z = 0; z < height; z++) {
                        Color color = new Color(image.getRGB(x,z));
                        Material block = ColorManager.getInstance().getBlocksFor(color);
                        world.getBlockAt(player.getLocation().clone().add(x,0,z)).setType(block);
                    }
                }
                player.sendMessage("Loaded Image! :)");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            player.sendMessage("File '" + imageName + "' doesn't exist");
        }

        return false;
    }

    private BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
