package me.wiceh;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SSCommand extends Command {

    public SSCommand() {
        super("ss", "ss.use", "controllo");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(player.hasPermission("ss.use")) {
                if(args.length == 0) {
                    ServerInfo ssServer = ProxyServer.getInstance().getServerInfo("ScreenShare");
                    player.connect(ssServer);
                }else if(args.length == 1) {
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                    if(target != null) {
                        if(!target.getName().equalsIgnoreCase(player.getName())) {
                            if(!target.getServer().getInfo().getName().equalsIgnoreCase("ScreenShare")) {
                                ServerInfo ssServer = ProxyServer.getInstance().getServerInfo("ScreenShare");
                                player.connect(ssServer);
                                target.connect(ssServer);
                                Main.jedis.set("Staffer", player.getName());
                                Main.jedis.set("Sospettato", target.getName());
                                player.sendMessage("§8[§cSS§8] §7Stai ora eseguendo un controllo hack a: §c" + target.getName());
                                for (ProxiedPlayer onlineStaff : ProxyServer.getInstance().getPlayers()) {
                                    if(onlineStaff.hasPermission("ss.use")) {
                                        onlineStaff.sendMessage("§8[§cSS§8] §b" + player.getName() + " §7sta ora eseguendo un controllo hack a §c" + target.getName());
                                    }
                                }

                                TextComponent seleziona = new TextComponent("§bSeleziona > ");
                                TextComponent cheating = new TextComponent("§8[§cCheating§8]");
                                TextComponent rifiuto = new TextComponent("§8[§6Rifiuto§8]");
                                TextComponent ammissione = new TextComponent("§8[§eAmmissione§8]");
                                TextComponent pulito = new TextComponent("§8[§aPulito§8]");
                                TextComponent spazio = new TextComponent(" ");
                                cheating.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tempban " + target.getName() + " 30d Cheating -s"));
                                cheating.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cCheating").create()));
                                rifiuto.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tempban " + target.getName() + " 30d Rifiuto controllo hack -s"));
                                rifiuto.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Rifiuto").create()));
                                ammissione.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tempban " + target.getName() + " 14d Ammissione utilizzo cheats -s"));
                                ammissione.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eAmmissione").create()));
                                pulito.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/unss " + target.getName()));
                                pulito.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aPulito").create()));

                                player.sendMessage(seleziona, cheating, spazio, rifiuto, spazio, ammissione, spazio, pulito);
                            }else {
                                player.sendMessage("§8[§cSS§8] §cQuesto giocatore è già nel server degli ScreenShare!");
                            }
                        }else {
                            player.sendMessage("§8[§cSS§8] §cNon puoi eseguire un controllo hack a te stesso!");
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
