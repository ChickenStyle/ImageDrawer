package me.chickenstyle.imagedrawer;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable(){
        //Loads the configs
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        saveResource("images/luffy.jpg",false);
        getCommand("draw").setExecutor(new DrawCommand());
        instance = this;
        ColorManager.getInstance();
        System.out.println("ImageDrawer Loaded!");
    }

    public static Main getInstance() {
        return instance;
    }
}
