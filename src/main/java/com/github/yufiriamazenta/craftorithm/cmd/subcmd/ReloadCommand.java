package com.github.yufiriamazenta.craftorithm.cmd.subcmd;

import com.github.yufiriamazenta.craftorithm.Craftorithm;
import com.github.yufiriamazenta.craftorithm.arcenciel.ArcencielDispatcher;
import com.github.yufiriamazenta.craftorithm.item.ItemManager;
import com.github.yufiriamazenta.craftorithm.recipe.RecipeManager;
import com.github.yufiriamazenta.craftorithm.util.ItemUtil;
import com.github.yufiriamazenta.craftorithm.util.LangUtil;
import crypticlib.command.ISubCmdExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public final class ReloadCommand extends AbstractSubCommand {

    public static final ISubCmdExecutor INSTANCE = new ReloadCommand();

    private ReloadCommand() {
        super("reload", "craftorithm.command.reload");
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        try {
            reloadPlugin();
            LangUtil.sendLang(sender, "command.reload.success");
        } catch (Exception e) {
            e.printStackTrace();
            LangUtil.sendLang(sender, "command.reload.exception");
        }
        return true;
    }

    public static void reloadPlugin() {
        reloadConfigs();
        ItemManager.loadItems();
        reloadRecipes();
    }

    public static void reloadConfigs() {
        Craftorithm.getInstance().reloadConfig();
        LangUtil.reloadMsgConfig();
        RecipeManager.getRemovedRecipeConfig().reloadConfig();
        ItemManager.loadItemFiles();
        RecipeManager.reloadRecipeFiles();
        ArcencielDispatcher.INSTANCE.getFunctionFile().reloadConfig();
        ItemUtil.reloadCannotCraftLore();
    }

    public static void reloadRecipes() {
        RecipeManager.reloadRecipes();
    }

}
