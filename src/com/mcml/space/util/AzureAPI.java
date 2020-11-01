package com.mcml.space.util;

import com.google.common.collect.Lists;
import com.mcml.space.core.EscapeLag;
import com.mcml.space.core.PlayerList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.WatchdogThread;

import javax.annotation.Nonnull;
import java.beans.ConstructorProperties;
import java.io.File;
import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public abstract class AzureAPI {
    private static final int squaredViewDistanceChunk = Bukkit.getViewDistance() * 2 ^ 3;
    private static final int viewDistanceBlock = Bukkit.getViewDistance() * 16;
    private static final Pattern SPACE = Pattern.compile(" ");
    private static final Pattern NOT_NUMERIC = Pattern.compile("[^-\\d.]");
    private static String loggerPrefix;
    private static Thread serverThread;

    @Nonnull
    public static Thread serverThread() {
        if (serverThread != null) {
            return serverThread;
        } else {
            Iterator<Thread> var0 = Thread.getAllStackTraces().keySet().iterator();

            Thread t;
            do {
                if (!var0.hasNext()) {
                    throw new AssertionError(Locale.isNative() ? "找不到服务器主线程!" : "NO SERVER THREAD!");
                }

                t = var0.next();
            } while (!t.getName().equals("Server thread"));

            serverThread = t;
            return t;
        }
    }

    public static AzureAPI.ChunkCoord wrapCoord(int chunkX, int chunkZ) {
        return new AzureAPI.ChunkCoord(chunkX, chunkZ);
    }

    public static int viewDistance(Player player) {
        return Bukkit.getViewDistance();
    }

    public static int viewDistanceBlock(Player player) {
        return viewDistanceBlock;
    }

    public static int viewDistanceChunk(Player player) {
        return squaredViewDistanceChunk;
    }

    public static boolean customViewDistance(Player player) {
        if (player != null && VersionLevel.canAccessPaperViewDistanceApi()) {
            return player.getFallDistance() != Bukkit.getViewDistance();
        } else {
            return false;
        }
    }

    public static String setPrefix(String prefix) {
        loggerPrefix = prefix = StringUtils.replaceChars(prefix, '&', '§');
        return prefix;
    }

    public static void resetPrefix() {
        loggerPrefix = null;
    }

    public static String prefix(String prefix, String context) {
        return prefix == null ? context : prefix.concat(context);
    }

    public static void fatal(String context, JavaPlugin plugin) {
        fatal(loggerPrefix, context, plugin);
    }

    public static void fatal(String prefix, String context, JavaPlugin plugin) {
        Bukkit.getLogger().severe(prefix(prefix, context));
        Bukkit.getPluginManager().disablePlugin(plugin);
    }

    public static void warn(String context) {
        warn(loggerPrefix, context);
    }

    public static void warn(String prefix, String context) {
        Bukkit.getLogger().log(Level.WARNING, prefix(prefix, context));
    }

    public static void log(String context) {
        log(loggerPrefix, context);
    }

    public static void log(String prefix, String context) {
        Bukkit.getConsoleSender().sendMessage(prefix(prefix, context));
    }

    public static void log(CommandSender sender, String context) {
        log(sender, loggerPrefix, context);
    }

    public static void log(CommandSender sender, String prefix, String context) {
        sender.sendMessage(prefix(prefix, context));
    }

    public static void bc(String context) {
        bc(loggerPrefix, context);
    }

    public static void bc(String context, String placeholder, String value) {
        bc(loggerPrefix, context, placeholder, value);
    }

    public static void bc(String prefix, String context, String placeholder, String value) {
        bc(prefix, StringUtils.replace(context, placeholder, value));
    }

    public static void bc(String prefix, String context) {
        Bukkit.broadcastMessage(prefix(prefix, context));
    }

    public static long toTicks(TimeUnit unit, long duration) {
        return unit.toSeconds(duration) * 20L;
    }

    public static <K, V> AzureAPI.Coord<K, V> wrapCoord(K key, V value) {
        return new AzureAPI.Coord(key, value);
    }

    public static <K, V, E> AzureAPI.Coord3<K, V, E> wrapCoord(K key, V value, E extra) {
        return new AzureAPI.Coord3(key, value, extra);
    }

    public static <E> List<E> matchElements(List<E> list, int start) {
        return matchElements(list, start, list.size() - 1);
    }

    public static <E> List<E> matchElements(List<E> list, int start, int end) {
        ArrayList<E> t;
        for (t = Lists.newArrayListWithCapacity(end - start + 1); start <= end; ++start) {
            t.add(list.get(start));
        }

        return t;
    }

    public static String concatsBetween(List<String> list, int start, char spilt) {
        return concatsBetween(list, start, spilt);
    }

    public static String concatsBetween(List<String> list, int start, String spilt) {
        return concatsBetween(list, start, list.size() - 1, spilt);
    }

    public static String concatsBetween(List<String> list, int start, int end, char spilt) {
        return concatsBetween(list, start, end, spilt);
    }

    public static String concatsBetween(List<String> list, int start, int end, String spilt) {
        String concated;
        for (concated = ""; start <= end; ++start) {
            concated = concated.concat(list.get(start).concat(start == end ? "" : spilt));
        }

        return concated;
    }

    public static boolean hasPerm(CommandSender sender, String perm) {
        return sender.hasPermission(perm);
    }

    public static boolean hasPerm(String username, String perm) {
        Player player = Bukkit.getPlayer(username);
        return player != null && player.hasPermission(perm);
    }

    public static boolean hasPerm(CommandSender sender, Permission perm) {
        return sender.hasPermission(perm);
    }

    public static FileConfiguration loadOrCreateConfiguration(File file) {
        try {
            file = createDirectories(file);
            file.createNewFile();
        } catch (IOException var2) {
            fatal(Locale.isNative() ? "无法创建文件 '" + file.getPath() + "', 已锁定?" : "Cannot create file '" + file.getPath() + "', blocked?", EscapeLag.plugin);
            var2.printStackTrace();
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    public static File fixesFilePath(File file) {
        return !StringUtils.contains(file.getName(), "/") && !StringUtils.contains(file.getName(), "\\") && !StringUtils.contains(file.getName(), File.separator) ? file : new File(StringUtils.substringBeforeLast(fixesPathSeparator(file.getPath()), File.separator) + File.separator + StringUtils.substringBeforeLast(fixesPathSeparator(file.getName()), File.separator), StringUtils.substringAfterLast(fixesPathSeparator(file.getName()), File.separator));
    }

    public static String fixesPathSeparator(String path) {
        return StringUtils.replace(path, "/", File.separator);
    }

    public static File createDirectories(File file) {
        file = fixesFilePath(file);
        (new File(StringUtils.substringBeforeLast(fixesPathSeparator(file.getPath()), File.separator))).mkdirs();
        return file;
    }

    public static void restartServer(String message) {
        PlayerList.forEach((player) -> {
            player.kickPlayer(prefix(loggerPrefix, message));
        });
        Bukkit.shutdown();
    }

    public static void playSound(Player player, Sound sound) {
        playSound(player, sound, false);
    }

    public static void playSound(Player player, Sound sound, boolean broadcast) {
        if (broadcast) {
            player.getWorld().playSound(player.getLocation(), sound, 10.0F, 1.0F);
        } else {
            player.playSound(player.getLocation(), sound, 10.0F, 1.0F);
        }

    }

    @SuppressWarnings("unchecked")
    public static <E> E colorzine(E element, Class<E> type) {
        if (element instanceof String) {
            return (E) StringUtils.replaceChars((String) element, '&', '§');
        } else if (element instanceof String[]) {
            String[] array = (String[])((String[])element);

            for (int i = 0; i < array.length; ++i) {
                array[i] = StringUtils.replaceChars(array[i], '&', '§');
            }

            return (E) array;
        } else {
            Iterator<String> var3;
            String each;
            if (element instanceof List) {
                List<String> c = (List<String>) element;
                var3 = c.iterator();

                while (var3.hasNext()) {
                    each = var3.next();
                    c.set(c.indexOf(each), StringUtils.replaceChars(each, '&', '§'));
                }

                return (E) c;
            } else if (!(element instanceof Set)) {
                return element;
            } else {
                Set<String> c = (Set<String>) element;
                var3 = c.iterator();

                while (var3.hasNext()) {
                    each = var3.next();
                    c.add(StringUtils.replaceChars(each, '&', '§'));
                }

                return (E) c;
            }
        }
    }

    public static int getSeconds(String str) {
        str = SPACE.matcher(str).replaceAll("");
        char unit = str.charAt(str.length() - 1);
        str = NOT_NUMERIC.matcher(str).replaceAll("");

        double num;
        try {
            num = Double.parseDouble(str);
        } catch (Exception var5) {
            num = 0.0D;
        }

        switch (unit) {
            case 'd':
                num *= 86400.0D;
                break;
            case 'h':
                num *= 3600.0D;
                break;
            case 'm':
                num *= 60.0D;
            case 's':
        }

        return (int) num;
    }

    public static String timeSummary(int seconds) {
        String time = "";
        if (seconds > 86400) {
            time = time + TimeUnit.SECONDS.toDays((long) seconds) + "d";
            seconds %= 86400;
        }

        if (seconds > 3600) {
            time = time + TimeUnit.SECONDS.toHours((long) seconds) + "h";
            seconds %= 3600;
        }

        if (seconds > 0) {
            time = time + TimeUnit.SECONDS.toMinutes((long) seconds) + "m";
        }

        return time;
    }

    public static void dumpThread(ThreadInfo thread, Logger logger) {
        if (VersionLevel.isSpigot()) {
            try {
                Class<WatchdogThread> wdt = WatchdogThread.class;
                Method dumpthreadmd = wdt.getDeclaredMethod("dumpThread", ThreadInfo.class, Logger.class);
                dumpthreadmd.setAccessible(true);
                dumpthreadmd.invoke(null, thread, logger);
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var4) {
                Logger.getLogger(AzureAPI.class.getName()).log(Level.SEVERE, null, var4);
            }
        }

    }

    public static class Coord3<K, V, E> {
        final K key;
        final V value;
        final E extra;

        @ConstructorProperties({"key", "value", "extra"})
        public Coord3(K key, V value, E extra) {
            this.key = key;
            this.value = value;
            this.extra = extra;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public E getExtra() {
            return this.extra;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof AzureAPI.Coord3)) {
                return false;
            } else {
                AzureAPI.Coord3<?, ?, ?> other = (AzureAPI.Coord3) o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    label47:
                    {
                        Object this$key = this.getKey();
                        Object other$key = other.getKey();
                        if (this$key == null) {
                            if (other$key == null) {
                                break label47;
                            }
                        } else if (this$key.equals(other$key)) {
                            break label47;
                        }

                        return false;
                    }

                    Object this$value = this.getValue();
                    Object other$value = other.getValue();
                    if (this$value == null) {
                        if (other$value != null) {
                            return false;
                        }
                    } else if (!this$value.equals(other$value)) {
                        return false;
                    }

                    Object this$extra = this.getExtra();
                    Object other$extra = other.getExtra();
                    if (this$extra == null) {
                        return other$extra == null;
                    } else return this$extra.equals(other$extra);

                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof AzureAPI.Coord3;
        }

        public int hashCode() {
            int PRIME = 1;
            int result = 1;
            Object $key = this.getKey();
            result = result * 59 + ($key == null ? 43 : $key.hashCode());
            Object $value = this.getValue();
            result = result * 59 + ($value == null ? 43 : $value.hashCode());
            Object $extra = this.getExtra();
            result = result * 59 + ($extra == null ? 43 : $extra.hashCode());
            return result;
        }
    }

    public static class Coord<K, V> {
        final K key;
        final V value;

        @ConstructorProperties({"key", "value"})
        public Coord(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof AzureAPI.Coord)) {
                return false;
            } else {
                AzureAPI.Coord<?, ?> other = (Coord<?, ?>) o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$key = this.getKey();
                    Object other$key = other.getKey();
                    if (this$key == null) {
                        if (other$key != null) {
                            return false;
                        }
                    } else if (!this$key.equals(other$key)) {
                        return false;
                    }

                    Object this$value = this.getValue();
                    Object other$value = other.getValue();
                    if (this$value == null) {
                        return other$value == null;
                    } else return this$value.equals(other$value);

                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof AzureAPI.Coord;
        }

        public int hashCode() {
            int PRIME = 1;
            int result = 1;
            Object $key = this.getKey();
            result = result * 59 + ($key == null ? 43 : $key.hashCode());
            Object $value = this.getValue();
            result = result * 59 + ($value == null ? 43 : $value.hashCode());
            return result;
        }
    }

    public static class ChunkCoord {
        final int chunkX;
        final int chunkZ;

        @ConstructorProperties({"chunkX", "chunkZ"})
        public ChunkCoord(int chunkX, int chunkZ) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
        }

        public int getChunkX() {
            return this.chunkX;
        }

        public int getChunkZ() {
            return this.chunkZ;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof AzureAPI.ChunkCoord)) {
                return false;
            } else {
                AzureAPI.ChunkCoord other = (AzureAPI.ChunkCoord) o;
                if (!other.canEqual(this)) {
                    return false;
                } else if (this.getChunkX() != other.getChunkX()) {
                    return false;
                } else {
                    return this.getChunkZ() == other.getChunkZ();
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof AzureAPI.ChunkCoord;
        }

        public int hashCode() {
            int PRIME = 1;
            int result = 1;
            result = result * 59 + this.getChunkX();
            result = result * 59 + this.getChunkZ();
            return result;
        }
    }
}
