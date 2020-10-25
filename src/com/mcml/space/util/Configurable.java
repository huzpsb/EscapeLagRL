package com.mcml.space.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class Configurable {
   public static void restoreNodes(AzureAPI.Coord<File, FileConfiguration> fileCoord, Class<? extends Configurable> providerClass) throws IllegalArgumentException, IllegalAccessException, IOException {
      FileConfiguration config = (FileConfiguration)fileCoord.getValue();
      Field[] var3 = providerClass.getDeclaredFields();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field field = var3[var5];
         Configurable.Node node = (Configurable.Node)field.getAnnotation(Configurable.Node.class);
         if (node != null) {
            field.setAccessible(true);
            Object defaultValue = field.get((Object)null);
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) && !Modifier.isFinal(mod)) {
               String path = node.value();
               Object configuredValue = config.get(path);
               Configurable.View view = (Configurable.View)field.getAnnotation(Configurable.View.class);
               if (view == null && configuredValue != null) {
                  field.set((Object)null, deserializeCollection(configuredValue, defaultValue.getClass()).getValue());
               } else {
                  config.set(path, AzureAPI.colorzine(serializeCollection(defaultValue).getValue(), Object.class));
               }
            }
         }
      }

      config.save((File)fileCoord.getKey());
   }

   public static AzureAPI.Coord<Object, Object> serializeCollection(Object defaultValue) {
      Class<?> prevType = defaultValue.getClass();
      ArrayList combinedEntries;
      if (Set.class.isAssignableFrom(prevType)) {
         combinedEntries = Lists.newArrayList((Object[])AzureAPI.colorzine(((Set)defaultValue).toArray(new String[0]), String[].class));
         return AzureAPI.wrapCoord(defaultValue, combinedEntries);
      } else if (!Map.class.isAssignableFrom(prevType)) {
         return AzureAPI.wrapCoord(defaultValue, defaultValue);
      } else {
         combinedEntries = Lists.newArrayList();
         Iterator var3 = ((Map)defaultValue).entrySet().iterator();

         while(var3.hasNext()) {
            Entry<?, ?> entry = (Entry)var3.next();
            boolean canReserve = !isBoolean(entry.getKey()) && isBoolean(entry.getValue());
            boolean hideSeparator = canReserve && equalsTrue(entry.getValue());
            combinedEntries.add((canReserve ? (hideSeparator ? "" : entry.getValue()) : entry.getKey()) + (hideSeparator ? "" : " : ") + (canReserve ? entry.getKey() : entry.getValue()));
         }

         return AzureAPI.wrapCoord(defaultValue, combinedEntries);
      }
   }

   private static boolean isBoolean(Object value) {
      return value instanceof Boolean || Boolean.TYPE.isAssignableFrom(value.getClass());
   }

   private static boolean equalsTrue(Object value) {
      return isBoolean(value) && value.toString().equalsIgnoreCase("true");
   }

   public static AzureAPI.Coord<Object, Object> deserializeCollection(Object configuredValue, Class<?> targetType) {
      if (Set.class.isAssignableFrom(targetType)) {
         Set<String> fieldValue = Sets.newHashSet(((List)configuredValue).toArray(new String[0]));
         return AzureAPI.wrapCoord(configuredValue, fieldValue);
      } else if (!Map.class.isAssignableFrom(targetType)) {
         return AzureAPI.wrapCoord(configuredValue, configuredValue);
      } else {
         Map<Object, Object> deserializedMap = Maps.newHashMap();
         List<String> combinedEntries = Lists.newArrayList(((List)configuredValue).toArray(new String[0]));
         Iterator var4 = combinedEntries.iterator();

         while(var4.hasNext()) {
            String entry = (String)var4.next();
            deserializedMap.put(hasMapSeparator(entry) ? (hasBooleanKey(entry) && !hasBooleanValue(entry) ? adaptType(StringUtils.replace(StringUtils.substringAfter(entry, " : "), "/:/", ":")) : adaptType(StringUtils.replace(StringUtils.substringBefore(entry, " : "), "/:/", ":"))) : adaptType(StringUtils.replace(entry, "/:/", ":")), hasMapSeparator(entry) ? (hasBooleanKey(entry) && !hasBooleanValue(entry) ? adaptType(StringUtils.replace(StringUtils.substringBefore(entry, " : "), "/:/", ":")) : adaptType(StringUtils.substringAfter(entry, " : "))) : true);
         }

         return AzureAPI.wrapCoord(combinedEntries, targetType.cast(deserializedMap));
      }
   }

   private static boolean hasMapSeparator(String value) {
      return StringUtils.contains(value, " : ");
   }

   private static boolean hasBooleanKey(String entry) {
      return StringUtils.startsWith(entry, "true : ") || StringUtils.startsWith(entry, "false : ");
   }

   private static boolean hasBooleanValue(String entry) {
      return StringUtils.endsWith(entry, " : true") || StringUtils.endsWith(entry, " : false");
   }

   private static Object adaptType(String value) {
      return value.equalsIgnoreCase("true") ? true : (value.equalsIgnoreCase("false") ? false : value);
   }

   @Documented
   @Retention(RetentionPolicy.RUNTIME)
   protected @interface View {
   }

   @Documented
   @Retention(RetentionPolicy.RUNTIME)
   protected @interface Node {
      String value();
   }
}
