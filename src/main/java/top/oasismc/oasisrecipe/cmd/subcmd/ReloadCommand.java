package top.oasismc.oasisrecipe.cmd.subcmd;

import org.bukkit.command.CommandSender;
import top.oasismc.oasisrecipe.OasisRecipe;
import top.oasismc.oasisrecipe.api.cmd.ISubCommand;
import top.oasismc.oasisrecipe.cmd.AbstractSubCommand;
import top.oasismc.oasisrecipe.item.ItemManager;
import top.oasismc.oasisrecipe.recipe.RecipeManager;
import top.oasismc.oasisrecipe.util.LangUtil;

import java.util.List;

public final class ReloadCommand extends AbstractSubCommand {

    public static final ISubCommand INSTANCE = new ReloadCommand();

    private ReloadCommand() {
        super("reload");
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        try {
            reloadPlugin();
            LangUtil.sendMsg(sender, "command.reload.success");
        } catch (Exception e) {
            e.printStackTrace();
            LangUtil.sendMsg(sender, "command.reload.exception");
        }
        return true;
    }

    public static void reloadPlugin() {
        reloadConfigs();
        ItemManager.loadItems();
        reloadRecipes();
    }

    public static void reloadConfigs() {
        OasisRecipe.getInstance().reloadConfig();
        LangUtil.reloadMsgConfig();
        RemoveCommand.getRemovedRecipeConfig().reloadConfig();
        ItemManager.loadItemFiles();
        RecipeManager.loadRecipeFiles();
    }

    public static void reloadRecipes() {
        RecipeManager.reloadRecipes();
    }

}
