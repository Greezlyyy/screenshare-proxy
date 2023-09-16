package me.wiceh;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnssCommand extends Command {

    public UnssCommand() {
        super("unss", "ss.use", "uncontrollo");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(player.hasPermission("ss.use")) {
                if(args.length == 0) {
                    player.sendMessage("§cUtilizzo: /unss <player>");
                }else if(args.length == 1) {
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                    if(target != null) {
                        if(!target.getName().equalsIgnoreCase(player.getName())) {
                            if(target.getServer().getInfo().getName().equalsIgnoreCase("ScreenShare")) {
                                ServerInfo lobbyServer = ProxyServer.getInstance().getServerInfo("lobby");
                                player.connect(lobbyServer);
                                target.connect(lobbyServer);
                                Main.jedis.set("Staffer", "");
                                Main.jedis.set("Sospettato", "");
                                player.sendMessage("§8[§cSS§8] §7Hai smesso di controllare §c" + target.getName());
                                for (ProxiedPlayer onlineStaff : ProxyServer.getInstance().getPlayers()) {
                                    if(onlineStaff.hasPermission("ss.use")) {
                                        onlineStaff.sendMessage("§8[§cSS§8] §b" + player.getName() + " §7ha smesso di controllare §c" + target.getName());
                                    }
                                }
                            }else {
                                player.sendMessage("§8[§cSS§8] §cQuesto giocatore non è nel server degli ScreenShare!");
                            }
                        }else {
                            player.sendMessage("§8[§cSS§8] §cNon puoi fare ciò a te stesso!");
                        }
                    }else {
                        player.sendMessage("§8[§cSS§8] §cGiocatore non trovato");
                    }
                }
            }else {
                player.sendMessage("§cNon hai il permesso!");
            }
        }
    }
}
