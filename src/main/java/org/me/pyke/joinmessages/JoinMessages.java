package org.me.pyke.joinmessages;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
public final class JoinMessages extends JavaPlugin {

    private String donatorjoinmessage;
    private String donatorleavemessage;
    private String donatorpermission;
    private String staffjoinmessage;
    private String staffleavemessage;
    private String staffpermission;
    private String defaultjoinmessage;
    private String defaultleavemessage;
    private String defaultpermission;
    private String reloadMessage;
    private boolean disableVanillamessages;

    @Override
    public void onEnable() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
            getLogger().info("LuckPerms found, nice!");
        }
        saveDefaultConfig();
        loadConfigValues();
        getLogger().warning("Plugin JoinMessages enabled.");
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getCommand("joinmessages").setExecutor(new ReloadCommandExecutor());
    }

    private void loadConfigValues() {
        FileConfiguration config = getConfig();
        donatorjoinmessage = config.getString("donator.join-message");
        donatorleavemessage = config.getString("donator.leave-message");
        donatorpermission = config.getString("donator.permission");
        //
        staffjoinmessage = config.getString("staff.join-message");
        staffleavemessage = config.getString("staff.leave-message");
        staffpermission = config.getString("staff.permission");
        //
        defaultjoinmessage = config.getString("default.join-message");
        defaultleavemessage = config.getString("default.leave-message");
        defaultpermission = config.getString("default.permission");
        //
        disableVanillamessages = config.getBoolean("options.disable-vanilla-message");
        reloadMessage = config.getString("messages.reloaded");
    }

    public class ReloadCommandExecutor implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (command.getName().equalsIgnoreCase("joinmessages")) {
                if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    loadConfigValues();
                    String message = ChatColor.translateAlternateColorCodes('&', reloadMessage);
                    sender.sendMessage(message);
                    return true;
                }
            }
            return false;
        }
    }


    public class PlayerQuitListener implements Listener {
        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event){
            if(disableVanillamessages) {
                event.setQuitMessage(null);
            }
            Player player = event.getPlayer();
            LuckPerms luckPerms = LuckPermsProvider.get();

            if (player.hasPermission(staffpermission)){
                String message = ChatColor.translateAlternateColorCodes('&', staffleavemessage.replace("%user%", player.getName()));
                Bukkit.broadcastMessage(message);
            }
            else if (player.hasPermission(donatorpermission)){
                String message = ChatColor.translateAlternateColorCodes('&', donatorleavemessage.replace("%user%", player.getName()));
                Bukkit.broadcastMessage(message);
            }
            else if (player.hasPermission(defaultpermission)) {
                String message = ChatColor.translateAlternateColorCodes('&', defaultleavemessage.replace("%user%", player.getName()));
                Bukkit.broadcastMessage(message);
            }
        }

    }

    public class PlayerJoinListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event){
            if(disableVanillamessages) {
                event.setJoinMessage(null);
            }
            Player player = event.getPlayer();
            LuckPerms luckPerms = LuckPermsProvider.get();
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            if (player.hasPermission(staffpermission)){
                String message = ChatColor.translateAlternateColorCodes('&', staffjoinmessage.replace("%user%", player.getName()));
                Bukkit.broadcastMessage(message);
            }
            else if (player.hasPermission(donatorpermission)){
                String message = ChatColor.translateAlternateColorCodes('&', donatorjoinmessage.replace("%user%", player.getName()));
                Bukkit.broadcastMessage(message);
            }
            else if (player.hasPermission(defaultpermission)){
                String message = ChatColor.translateAlternateColorCodes('&', defaultjoinmessage.replace("%user%", player.getName()));
                Bukkit.broadcastMessage(message);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
