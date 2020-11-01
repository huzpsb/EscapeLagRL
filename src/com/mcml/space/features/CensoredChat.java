package com.mcml.space.features;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mcml.space.config.Features;
import com.mcml.space.core.EscapeLag;
import com.mcml.space.core.PlayerList;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.AzureAPI.Coord;
import com.mcml.space.util.Locale;
import com.mcml.space.util.Perms;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CensoredChat {
    public static void init(Plugin plugin) {
        if (Features.AntiSpamenable) {
            Bukkit.getPluginManager().registerEvents(new CensoredChat.SpamDetector(), plugin);
        }

        if (Features.enableAntiDirty) {
            Bukkit.getPluginManager().registerEvents(new CensoredChat.DirtyChatDetector(), plugin);
        }

        AzureAPI.log(Locale.isNative() ? "子模块 - 聊天审查 已启动" : "Submodule - CensoredChat has been enabled");
    }

    private static class DirtyChatDetector implements Listener {
        private DirtyChatDetector() {
        }

        // $FF: synthetic method
        @SuppressWarnings("unused")
        DirtyChatDetector(Object x0) {
            this();
        }

        private static boolean handle(String message, String contain, boolean ignoreCase, Cancellable evt, Player player) {
            if (!isAllowed(message, contain, ignoreCase)) {
                evt.setCancelled(true);
                AzureAPI.log(player, Features.AntiSpamDirtyWarnMessage);
                return true;
            } else {
                return false;
            }
        }

        private static boolean isAllowed(String message, String contain, boolean ignoreCase) {
            message = ignoreCase ? message.toLowerCase() : message;
            boolean singleAllowed = StringUtils.endsWith(contain, "$");
            boolean literally = StringUtils.endsWith(contain, "*");
            if (singleAllowed) {
                label82:
                {
                    if (ignoreCase) {
                        if (!message.equals(contain = removeSignals(contain.toLowerCase()))) {
                            break label82;
                        }
                    } else if (!message.equals(contain = removeSignals(contain))) {
                        break label82;
                    }

                    return true;
                }
            }

            if (literally) {
                char[] var9 = (!singleAllowed && ignoreCase ? removeSignals(contain.toLowerCase()) : contain).toCharArray();
                int var10 = var9.length;

                for (int var11 = 0; var11 < var10; ++var11) {
                    char c = var9[var11];
                    if (!StringUtils.contains(message, c)) {
                        return true;
                    }
                }

                return false;
            } else {
                int count = StringUtils.countMatches(message, !singleAllowed && ignoreCase ? removeSignals(contain.toLowerCase()) : contain);
                if (count == 0) {
                    return true;
                } else {
                    Iterator<String> var6 = Features.AntiSpamDirtyWhitelist.iterator();

                    String each;
                    do {
                        if (!var6.hasNext()) {
                            return false;
                        }

                        each = var6.next();
                    } while (StringUtils.countMatches(message, ignoreCase ? each.toLowerCase() : each) < count);

                    return true;
                }
            }
        }

        private static String removeSignals(String contain) {
            return StringUtils.removeEnd(StringUtils.removeEnd(contain, "$"), "*");
        }

        @EventHandler(
                priority = EventPriority.HIGH,
                ignoreCancelled = true
        )
        public void checkChatDirty(AsyncPlayerChatEvent evt) {
            Player player = evt.getPlayer();
            if (!Perms.has(player) && !AzureAPI.hasPerm(player, "escapelag.bypass.dirty")) {
                String message = evt.getMessage();
                Iterator<Entry<String, Boolean>> var4 = Features.AntiSpamDirtyList.entrySet().iterator();

                Entry<String, Boolean> entry;
                do {
                    if (!var4.hasNext()) {
                        return;
                    }

                    entry = var4.next();
                } while (!handle(message, entry.getKey(), entry.getValue(), evt, player));

            }
        }
    }

    private static class SpamDetector implements Listener, PlayerList.PlayerQuitReactor {
        private final Map<String, Long> playersChat;
        private final Map<String, Set<AzureAPI.Coord<String, Long>>> playersCommand;

        private SpamDetector() {
            this.playersChat = Maps.newHashMap();
            this.playersCommand = Maps.newHashMap();
            BukkitScheduler var10000 = Bukkit.getScheduler();
            Map<String, Long> var10002 = this.playersChat;
            var10000.runTaskTimer(EscapeLag.plugin, var10002::clear, 0L, AzureAPI.toTicks(TimeUnit.SECONDS, (int) Math.ceil(Features.AntiSpamPeriodPeriod) > 30 ? (long) ((int) Math.ceil(Features.AntiSpamPeriodPeriod)) : 30L));
        }

        // $FF: synthetic method
        @SuppressWarnings("unused")
        SpamDetector(Object x0) {
            this();
        }

        @EventHandler(
                priority = EventPriority.LOWEST,
                ignoreCancelled = true
        )
        public void checkChatSpam(AsyncPlayerChatEvent evt) {
            Player player = evt.getPlayer();
            if (!Perms.has(player) && !AzureAPI.hasPerm(player, "escapelag.bypass.spam")) {
                long now = System.currentTimeMillis();
                if (this.isSpammingChat(player, now)) {
                    evt.setCancelled(true);
                    AzureAPI.log(player, Features.AntiSpamPeriodWarnMessage);
                }

            }
        }

        @EventHandler(
                priority = EventPriority.LOWEST,
                ignoreCancelled = true
        )
        public void checkCommandSpam(PlayerCommandPreprocessEvent evt) {
            Player player = evt.getPlayer();
            if (!Perms.has(player) && !AzureAPI.hasPerm(player, "escapelag.bypass.spam")) {
                long now = System.currentTimeMillis();
                if (this.isSpammingCommand(player, StringUtils.substringBefore(evt.getMessage(), " "), now)) {
                    evt.setCancelled(true);
                    AzureAPI.log(player, Features.AntiSpamPeriodWarnMessage);
                }

            }
        }

        public void react(PlayerQuitEvent evt) {
            this.playersChat.remove(evt.getPlayer().getName());
        }

        private boolean isSpammingChat(Player player, long now) {
            Long last = this.playersChat.get(player.getName());
            this.playersChat.put(player.getName(), now);
            return last != null && (double) (now - last) <= Features.AntiSpamPeriodPeriod * 1000.0D;
        }

        private boolean isSpammingCommand(Player player, String commandLabel, long now) {
            Set<AzureAPI.Coord<String, Long>> recorded = this.playersCommand.get(player.getName());
            if (recorded == null) {
                recorded = Sets.newHashSet();
                recorded.add(AzureAPI.wrapCoord(commandLabel, now));
                this.playersCommand.put(player.getName(), recorded);
                return false;
            } else {
                Iterator<Coord<String, Long>> it = recorded.iterator();

                AzureAPI.Coord<String, Long> coord;
                do {
                    if (!it.hasNext()) {
                        recorded.add(AzureAPI.wrapCoord(commandLabel, now));
                        return false;
                    }

                    coord = it.next();
                } while (!coord.getKey().equalsIgnoreCase(commandLabel));

                it.remove();
                recorded.add(AzureAPI.wrapCoord(commandLabel, now));
                return (double) (now - coord.getValue()) <= Features.AntiCommandSpamPeriodPeriod * 1000.0D;
            }
        }
    }
}
