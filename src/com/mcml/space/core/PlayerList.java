package com.mcml.space.core;

import com.google.common.collect.Lists;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class PlayerList implements Listener {
    private static final Set<Player> PLAYERS = new HashSet();
    private static final List<PlayerList.PlayerJoinReactor> JOIN_REACTORS = Lists.newArrayList();
    private static final List<PlayerList.PlayerQuitReactor> QUIT_REACTORS = Lists.newArrayList();

    public static void init(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new PlayerList(), plugin);
        Iterator var1 = Bukkit.getWorlds().iterator();

        while (var1.hasNext()) {
            World world = (World) var1.next();
            Iterator var3 = world.getPlayers().iterator();

            while (var3.hasNext()) {
                Player each = (Player) var3.next();
                PLAYERS.add(each);
            }
        }

        AzureAPI.log(Locale.isNative() ? "核心模块 - 玩家列表 已启用" : "Coremodule - PlayerList has been enabled");
    }

    public static void bind(PlayerList.PlayerJoinReactor reactor) {
        JOIN_REACTORS.add(reactor);
    }

    public static void bind(PlayerList.PlayerQuitReactor reactor) {
        QUIT_REACTORS.add(reactor);
    }

    public static void clearReactors() {
        JOIN_REACTORS.clear();
        QUIT_REACTORS.clear();
    }

    public static boolean contains(Player player) {
        return PLAYERS.contains(player);
    }

    public static boolean contains(String pn) {
        Iterator var1 = PLAYERS.iterator();

        Player player;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            player = (Player) var1.next();
        } while (!player.getName().equals(pn));

        return true;
    }

    public static boolean isEmpty() {
        return PLAYERS.isEmpty();
    }

    public static int size() {
        return PLAYERS.size();
    }

    public static void forEach(Consumer<Player> consumer) {
        Iterator var1 = Bukkit.getWorlds().iterator();

        while (var1.hasNext()) {
            World world = (World) var1.next();
            Iterator var3 = world.getPlayers().iterator();

            while (var3.hasNext()) {
                Player each = (Player) var3.next();
                consumer.accept(each);
            }
        }

    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onJoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer();
        PLAYERS.add(player);
        if (!JOIN_REACTORS.isEmpty()) {
            Iterator var3 = JOIN_REACTORS.iterator();

            while (var3.hasNext()) {
                PlayerList.PlayerJoinReactor re = (PlayerList.PlayerJoinReactor) var3.next();
                re.react(evt);
            }
        }

    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onQuit(PlayerQuitEvent evt) {
        Player player = evt.getPlayer();
        PLAYERS.remove(player);
        if (!QUIT_REACTORS.isEmpty()) {
            Iterator var3 = QUIT_REACTORS.iterator();

            while (var3.hasNext()) {
                PlayerList.PlayerQuitReactor re = (PlayerList.PlayerQuitReactor) var3.next();
                re.react(evt);
            }
        }

    }

    public interface PlayerQuitReactor {
        void react(PlayerQuitEvent var1);
    }

    public interface PlayerJoinReactor {
        void react(PlayerJoinEvent var1);
    }
}
