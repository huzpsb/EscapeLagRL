package com.mcml.space.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VersionLevel {
   private static final VersionLevel.Version level = checkServerAndApi();
   private static boolean modernApi;
   private static boolean spigot;
   private static boolean spigotInternalApi;
   private static boolean paper;
   private static boolean paperViewDistanceApi;
   private static boolean forge;
   private static String rawVersion;

   public static final VersionLevel.Version get() {
      return level;
   }

   public static final String rawVersion() {
      return rawVersion;
   }

   public static boolean modernApi() {
      return modernApi;
   }

   public static boolean isSpigot() {
      return spigot;
   }

   public static boolean canAccessSpigotInternalApi() {
      return spigotInternalApi;
   }

   public static boolean isPaper() {
      return paper;
   }

   public static boolean canAccessPaperViewDistanceApi() {
      return paperViewDistanceApi;
   }

   public static boolean isForge() {
      return forge;
   }

   private static VersionLevel.Version checkServerAndApi() {
      VersionLevel.Version level = checkServerVersion();
      checkServerType(rawVersion);
      checkApiType(level);
      return level;
   }

   private static void checkServerType(String rawVersion) {
      String bukkitVersion = rawVersion.toLowerCase();
      boolean thermos = bukkitVersion.contains("thermos") || bukkitVersion.contains("contigo");
      forge = thermos || bukkitVersion.contains("cauldron") || bukkitVersion.contains("mcpc") || bukkitVersion.contains("uranium") || bukkitVersion.contains("cat");
      paper = bukkitVersion.contains("paper") || bukkitVersion.contains("taco") || bukkitVersion.contains("torch") || bukkitVersion.contains("akarin");
      if (paper || thermos || bukkitVersion.contains("spigot")) {
         spigot = true;
      }

   }

   private static void checkApiType(VersionLevel.Version level) {
      modernApi = level.ordinal() <= VersionLevel.Version.MINECRAFT_1_13_R1.ordinal();

      try {
         Class.forName("org.spigotmc.RestartCommand");
         spigotInternalApi = true;
      } catch (ClassNotFoundException var3) {
         spigotInternalApi = false;
      }

      try {
         Player.class.getMethod("getViewDistance");
         paperViewDistanceApi = true;
      } catch (SecurityException | NoSuchMethodException var2) {
         paperViewDistanceApi = false;
      }

   }

   private static VersionLevel.Version checkServerVersion() {
      String version = rawVersion = Bukkit.getServer().getVersion();
      if (version.contains("1.13")) {
         return VersionLevel.Version.MINECRAFT_1_13_R1;
      } else if (version.contains("1.12")) {
         return VersionLevel.Version.MINECRAFT_1_12_R1;
      } else if (version.contains("1.11")) {
         return VersionLevel.Version.MINECRAFT_1_11_R1;
      } else if (version.contains("1.10")) {
         return VersionLevel.Version.MINECRAFT_1_10_R1;
      } else if (version.contains("1.9.4")) {
         return VersionLevel.Version.MINECRAFT_1_9_R2;
      } else if (version.contains("1.9")) {
         return VersionLevel.Version.MINECRAFT_1_9_R1;
      } else if (version.contains("1.8.8")) {
         return VersionLevel.Version.MINECRAFT_1_8_R3;
      } else if (version.contains("1.8.3")) {
         return VersionLevel.Version.MINECRAFT_1_8_R2;
      } else if (version.contains("1.8")) {
         return VersionLevel.Version.MINECRAFT_1_8_R1;
      } else if (version.contains("1.7.10")) {
         return VersionLevel.Version.MINECRAFT_1_7_R4;
      } else if (version.contains("1.7.9")) {
         return VersionLevel.Version.MINECRAFT_1_7_R3;
      } else if (version.contains("1.7.5")) {
         return VersionLevel.Version.MINECRAFT_1_7_R2;
      } else if (version.contains("1.7")) {
         return VersionLevel.Version.MINECRAFT_1_7_R1;
      } else if (version.contains("1.6.4")) {
         return VersionLevel.Version.MINECRAFT_1_6_R3;
      } else if (version.contains("1.6.2")) {
         return VersionLevel.Version.MINECRAFT_1_6_R2;
      } else if (version.contains("1.6.1")) {
         return VersionLevel.Version.MINECRAFT_1_6_R1;
      } else if (version.contains("1.5.2")) {
         return VersionLevel.Version.MINECRAFT_1_5_R3;
      } else if (version.contains("1.5.1")) {
         return VersionLevel.Version.MINECRAFT_1_5_R2;
      } else if (version.contains("1.5")) {
         return VersionLevel.Version.MINECRAFT_1_5_R1;
      } else if (version.contains("1.4.7")) {
         return VersionLevel.Version.MINECRAFT_1_4_R1;
      } else if (version.contains("1.4.6")) {
         return VersionLevel.Version.MINECRAFT_1_4_6;
      } else {
         AzureAPI.warn(Locale.isNative() ? "由于无法识别到服务器版本, 设定为未来版本." : "Cannot capture server version, set as a future version.");
         return VersionLevel.Version.MINECRAFT_FUTURE;
      }
   }

   public static boolean isLowerThan(VersionLevel.Version other) {
      return level.ordinal() > other.ordinal();
   }

   public static boolean isLowerEquals(VersionLevel.Version other) {
      return level.ordinal() >= other.ordinal();
   }

   public static boolean isHigherThan(VersionLevel.Version other) {
      return level.ordinal() < other.ordinal();
   }

   public static boolean isHigherEquals(VersionLevel.Version other) {
      return level.ordinal() <= other.ordinal();
   }

   public static boolean equals(VersionLevel.Version other) {
      return level == other;
   }

   public static enum Version {
      MINECRAFT_FUTURE,
      MINECRAFT_1_13_R1,
      MINECRAFT_1_12_R1,
      MINECRAFT_1_11_R1,
      MINECRAFT_1_10_R1,
      MINECRAFT_1_9_R2,
      MINECRAFT_1_9_R1,
      MINECRAFT_1_8_R3,
      MINECRAFT_1_8_R2,
      MINECRAFT_1_8_R1,
      MINECRAFT_1_7_R4,
      MINECRAFT_1_7_R3,
      MINECRAFT_1_7_R2,
      MINECRAFT_1_7_R1,
      MINECRAFT_1_6_R3,
      MINECRAFT_1_6_R2,
      MINECRAFT_1_6_R1,
      MINECRAFT_1_5_R3,
      MINECRAFT_1_5_R2,
      MINECRAFT_1_5_R1,
      MINECRAFT_1_4_R1,
      MINECRAFT_1_4_6;
   }

   @Documented
   @Retention(RetentionPolicy.SOURCE)
   public @interface CallerSensitive {
      String server() default "";

      String version() default "";
   }
}
