package top.oasismc.oasisrecipe.update;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import top.oasismc.oasisrecipe.OasisRecipe;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UpdateUtil {

    public static void checkUpdate(CommandSender sender) {
        Bukkit.getScheduler().runTaskAsynchronously(OasisRecipe.getInstance(), () -> {
            try {
                URL url = new URL("https://api.github.com/repos/ChiyodaXiaoYi/OasisRecipe/releases/latest");
                URLConnection conn = url.openConnection();
                String newVersion = "";
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(60000);
                InputStream is = conn.getInputStream();
                newVersion = new BufferedReader(new InputStreamReader(is)).readLine();
                is.close();

                int index = newVersion.indexOf("\"tag_name\"");
                int index2 = newVersion.indexOf("\"target_commitish\"");
                newVersion = newVersion.substring(index + 13, index2 - 2);
                System.out.println(newVersion);
                String ver = OasisRecipe.getInstance().getDescription().getVersion();
                ver = ver.substring(0, ver.indexOf("-"));
                if (!ver.equals(newVersion)) {
                    Bukkit.getScheduler().callSyncMethod(OasisRecipe.getInstance(), () -> {
                        OasisRecipe.getInstance().sendMsg(sender, "update.newVersion");
                        return null;
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
