package top.oasismc.oasisrecipe;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import top.oasismc.oasisrecipe.bstat.Metrics;
import top.oasismc.oasisrecipe.cmd.PluginCommand;
import top.oasismc.oasisrecipe.config.ConfigUpdater;
import top.oasismc.oasisrecipe.listener.FurnaceSmeltListener;
import top.oasismc.oasisrecipe.listener.RecipeCheckListener;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public final class OasisRecipe extends JavaPlugin {

    private static OasisRecipe PLUGIN;
    private int vanillaVersion;

    public OasisRecipe() {
        PLUGIN = this;
    }

    @Override
    public void onEnable() {
        loadBStat();
        loadVanillaVersion();
        saveDefaultConfig();
        loadCommands();
        loadListener();
        loadConfigs();
        info(getConfig().getString("messages.load.finish", "messages.load.finish"));
    }

    @Override
    public void onDisable() {
        Bukkit.resetRecipes();
    }

    private void loadVanillaVersion() {
        String versionStr = Bukkit.getBukkitVersion();
        int index1 = versionStr.indexOf(".");
        int index2 = versionStr.indexOf(".", index1 + 1);
        vanillaVersion = Integer.parseInt(versionStr.substring(index1 + 1, index2));
        info("&aLoad 1." + vanillaVersion + " Adapter");
    }

    private void loadBStat() {
        new Metrics(this, 15016);
    }

    private void loadCommands() {
        Bukkit.getPluginCommand("oasisrecipe").setExecutor(PluginCommand.getCommand());
        Bukkit.getPluginCommand("oasisrecipe").setTabCompleter(PluginCommand.getCommand());
    }

    private void loadListener() {
        if (getVanillaVersion() >= 18)
            Bukkit.getPluginManager().registerEvents(FurnaceSmeltListener.getListener(), this);
        Bukkit.getPluginManager().registerEvents(RecipeCheckListener.getListener(), this);
    }

    private void loadConfigs() {
        ConfigUpdater.getInstance().updateConfig();
        PluginCommand.getCommand().reloadPlugin();
    }

    public void sendMsg(CommandSender sender, String key) {
        if (sender == null) {
            return;
        }
        key = "messages." + key;
        String message = getConfig().getString(key, key);
        message = message.replace("%player%", sender.getName());
        message = message.replace("%version%", getDescription().getVersion());
        sender.sendMessage(color(message));
    }

    public static OasisRecipe getPlugin() {
        return PLUGIN;
    }

    public static String color(String text) {
        return translateAlternateColorCodes('&', text);
    }

    public static void info(String text) {
        Bukkit.getConsoleSender().sendMessage(color("&8[&3Oasis&bRecipe&8] &bINFO &8| &r" + text));
    }

    public int getVanillaVersion() {
        return vanillaVersion;
    }

}
