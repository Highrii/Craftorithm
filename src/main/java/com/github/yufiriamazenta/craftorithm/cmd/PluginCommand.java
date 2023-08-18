package com.github.yufiriamazenta.craftorithm.cmd;

import com.github.yufiriamazenta.craftorithm.Craftorithm;
import com.github.yufiriamazenta.craftorithm.cmd.subcmd.*;
import com.github.yufiriamazenta.craftorithm.util.ContainerUtil;
import com.github.yufiriamazenta.craftorithm.util.LangUtil;
import crypticlib.annotations.BukkitCommand;
import crypticlib.command.IPluginCommand;
import crypticlib.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@BukkitCommand(command = "craftorithm")
public class PluginCommand implements IPluginCommand {

    private final Map<String, ISubCommand> subCommandMap;

    public PluginCommand() {
        subCommandMap = new ConcurrentHashMap<>();
        regDefaultSubCommands();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> argList = Arrays.asList(args);
        if (argList.size() < 1) {
            LangUtil.sendMsg(sender, "command.not_enough_param", ContainerUtil.newHashMap("<number>", String.valueOf(1)));
            return true;
        }
        ISubCommand subCommand = subCommandMap.get(argList.get(0));
        if (subCommand != null) {
            String perm = subCommand.getPerm();
            if (perm != null) {
                if (!sender.hasPermission(perm)) {
                    LangUtil.sendMsg(sender, "command.no_perm");
                    return true;
                }
            }
            return subCommand.onCommand(sender, argList.subList(1, argList.size()));
        }
        else {
            LangUtil.sendMsg(sender, "command.undefined_subcmd");
            return true;
        }
    }

    private void regDefaultSubCommands() {
        regSubCommand(ReloadCommand.INSTANCE);
        regSubCommand(VersionCommand.INSTANCE);
        regSubCommand(RemoveRecipeCommand.INSTANCE);
        regSubCommand(ItemCommand.INSTANCE);
        regSubCommand(RunArcencielCmd.INSTANCE);
        regSubCommand(LookRecipeCommand.INSTANCE);
        regSubCommand(CreateRecipeCommand.INSTANCE);
    }

    @Override
    public @NotNull Map<String, ISubCommand> getSubCommands() {
        return subCommandMap;
    }

    public void regSubCommand(ISubCommand executor) {
        subCommandMap.put(executor.getSubCommandName(), executor);
    }

    @Override
    public Plugin getPlugin() {
        return Craftorithm.getInstance();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> argList = Arrays.asList(args);
        if (argList.size() <= 1) {
            List<String> tabList = new ArrayList<>();
            for (String subCmd : subCommandMap.keySet()) {
                ISubCommand subCommand = subCommandMap.get(subCmd);
                if (sender.hasPermission(subCommand.getPerm()))
                    tabList.add(subCmd);
            }
            tabList.removeIf(str -> !str.startsWith(args[0]));
            return tabList;
        }
        ISubCommand subCommand = subCommandMap.get(argList.get(0));
        if (subCommand != null) {
            if (!sender.hasPermission(subCommand.getPerm()))
                return Collections.singletonList("");
            return subCommand.onTabComplete(sender, argList.subList(1, argList.size()));
        }
        else
            return Collections.singletonList("");
    }

    public Map<String, ISubCommand> getSubCommandMap() {
        return subCommandMap;
    }


}
