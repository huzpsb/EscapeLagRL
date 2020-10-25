package com.mcml.space.util;

import com.mcml.space.config.Core;
import org.apache.commons.lang.StringUtils;

public class Locale {
   private static volatile Locale.Lang language;

   public static Locale.Lang checkLang(String i18n) {
      if (!i18n.equalsIgnoreCase("zh_cn") && !i18n.equalsIgnoreCase("zh_sg")) {
         if (StringUtils.startsWithIgnoreCase(i18n, "zh_")) {
            return Locale.Lang.CHINESE_TRADITIONAL;
         } else if (StringUtils.startsWithIgnoreCase(i18n, "en_")) {
            return Locale.Lang.ENGLISH;
         } else {
            AzureAPI.warn("Cannot capture language, set as global.");
            return Locale.Lang.GLOBAL;
         }
      } else {
         return Locale.Lang.CHINESE_SIMPLIFIED;
      }
   }

   public static boolean isNative() {
      return language == Locale.Lang.CHINESE_SIMPLIFIED || language == Locale.Lang.CHINESE_TRADITIONAL;
   }

   public static boolean equals(Locale.Lang other) {
      return language == other;
   }

   static {
      language = checkLang(Core.lang);
   }

   public static enum Lang {
      GLOBAL,
      ENGLISH,
      CHINESE_SIMPLIFIED,
      CHINESE_TRADITIONAL;
   }
}
