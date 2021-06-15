package me.albert.amazingbot;

import com.xbaimiao.amazingbot.MiraiLoader;
import io.izzel.taboolib.loader.util.ILoader;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.inject.TInject;
import me.albert.amazingbot.sqlite.SQLer;
import io.izzel.taboolib.loader.Plugin;
import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.database.MySQL;
import me.albert.amazingbot.listeners.NewPlayer;
import me.albert.amazingbot.listeners.OnBind;
import me.albert.amazingbot.listeners.OnCommand;
import me.albert.amazingbot.utils.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class AmazingBot extends Plugin {

    @TInject(value = {"config.yml"})
    public static TConfig config;

    private static JavaPlugin instance;
    private static CustomConfig data;
    private static CustomConfig mysqlSettings;

    public static JavaPlugin getinstance() {
        return instance;
    }

    public static CustomConfig getData() {
        return data;
    }

    public static CustomConfig getMysqlSettings() {
        return mysqlSettings;
    }


    @Override
    public void onEnable() {
        MiraiLoader.start();
        instance = getPlugin();
        getPlugin().saveDefaultConfig();
        Bukkit.getScheduler().runTaskLater(getPlugin(), Bot::start,30L); // xbaimiao - delay the task to make sure the bot start up properly
        registerEvent(new OnCommand());
        registerEvent(new NewPlayer());
        registerEvent(new OnBind());
        data = new CustomConfig("data.yml", instance);
        mysqlSettings = new CustomConfig("mysql.yml", instance);
        if (mysqlSettings.getConfig().getBoolean("enable")) {
            MySQL.setUP();
        }
        if (SQLer.isEnable()){
            getPlugin().getLogger().info("已启用sqlite储存数据");
        }
    }

    private void registerEvent(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, instance);
    }

    @Override
    public void onDisable() {
        if (MySQL.ENABLED) {
            MySQL.close();
            return;
        }
        data.save();
    }

}
