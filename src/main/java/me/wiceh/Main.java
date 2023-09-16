package me.wiceh;

import net.md_5.bungee.api.plugin.Plugin;
import redis.clients.jedis.Jedis;

public class Main extends Plugin {

    public static Jedis jedis;
    private static final String CONNECTION_STRING = "redis://default:cVLItIxsoXNeUvDyJh6UGism15aLV1cI@redis-12700.c276.us-east-1-2.ec2.cloud.redislabs.com:12700";

    @Override
    public void onEnable() {
        getLogger().info("Il plugin è stato abilitato");
        jedis = new Jedis(CONNECTION_STRING);
        getProxy().getPluginManager().registerCommand(this, new SSCommand());
        getProxy().getPluginManager().registerCommand(this, new UnssCommand());
    }
}