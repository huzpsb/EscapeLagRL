package com.mcml.space.config;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mcml.space.util.Locale;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.entity.EntityType;

public abstract class DefaultOptions {
   public static Set<String> unloadClearEntityTypes() {
      DefaultOptions.TypedSet types = DefaultOptions.TypedSet.create();
      types.from(EntityType.ZOMBIE).from(EntityType.SKELETON).from(EntityType.SPIDER).from(EntityType.CREEPER);
      return Sets.newHashSet(types);
   }

   public static Set<String> slackEntityTypes() {
      DefaultOptions.TypedSet types = DefaultOptions.TypedSet.create();
      types.from(EntityType.ZOMBIE).from(EntityType.SKELETON).from(EntityType.SPIDER).from(EntityType.CREEPER).from(EntityType.SHEEP).from(EntityType.PIG).from(EntityType.CHICKEN);
      return Sets.newHashSet(types);
   }

   public static Set<String> blockedErrorMessages() {
      DefaultOptions.TypedSet messages = DefaultOptions.TypedSet.create();
      messages.from("ErrorPluginName").from("ErrorPluginMessage");
      return Sets.newHashSet(messages);
   }

   public static Set<String> EntityClearEntityTypes() {
      DefaultOptions.TypedSet types = DefaultOptions.TypedSet.create();
      types.from(EntityType.ZOMBIE).from(EntityType.SKELETON).from(EntityType.SPIDER).from(EntityType.CREEPER).from(EntityType.SHEEP).from(EntityType.PIG).from(EntityType.CHICKEN);
      return Sets.newHashSet(types);
   }

   public static Set<String> redstoneRemovalMaterialTypes() {
      Set<String> defaultset = new HashSet();
      defaultset.add("REDSTONE_WIRE");
      defaultset.add("DIODE_BLOCK_ON");
      defaultset.add("DIODE_BLOCK_OFF");
      defaultset.add("REDSTONE_TORCH_ON");
      defaultset.add("REDSTONE_TORCH_OFF");
      defaultset.add("REDSTONE_BLOCK");
      return defaultset;
   }

   public static Map<String, String> droppedItemClearsRadius() {
      DefaultOptions.TypedMap<String> radius = DefaultOptions.TypedMap.create("2");
      radius.from("x").from("y").from("z");
      return Maps.newHashMap(radius);
   }

   public static Map<String, Boolean> spamMessages() {
      DefaultOptions.TypedMap<Boolean> messages = DefaultOptions.TypedMap.create(Boolean.TRUE);
      if (Locale.isNative()) {
         messages.from("垃圾服*").from("破服*").from("狗服*").from("炸服*").from("laji服").from("垃圾管理*").from("死管理*").from("狗管理*").from("垃圾op*").from("狗op*").from("死op*").from("操你").from("日你").from("干你$").from("草你").from("吊你").from("丢你老").from("丢你楼").from("刁你").from("叼你").from("屌你").from("你妈*").from("亲妈$").from("他妈*").from("亲娘").from("你母亲*").from("您母亲*").from("你娘").from("死妈*").from("你吗死*").from("你吗炸*").from("你ma").from("全家$").from("升天$").from("飞天$").from("的逼*").from("个逼*").from("贱").from("屎").from("屁").from("艸").from("屄").from("肏").from("婊").from("弱智*").from("傻逼*").from("智障*").from("脑残*").from("二叉").from("傻蛋").from("傻狗").from("杂犬").from("司马*").from("艹").from("傻b*").from("臭b*").from("烂b*").from("mlgb").from("sb").from("s.b").from("s,b").from("s b").from("狗日").from("疯狗").from("草泥马*").from("拟吗").from("妮玛").from("哈麻批*").from("蛤蟆皮*").from("你麻痹*").from("马币").from("马勒戈壁*").from("买了个表").from("草泥馬").from("親媽").from("他媽").from("你媽").from("腦殘").from("賤");
      } else {
         messages.from("fuck*").from("shit$").from("bitch");
      }

      return Maps.newHashMap(messages);
   }

   public static Set<String> spamWhitelist() {
      DefaultOptions.TypedSet messages = DefaultOptions.TypedSet.create();
      if (Locale.isNative()) {
         messages.from("全家福").from("全家桶");
      } else {
         messages.from("holy shit");
      }

      return Sets.newHashSet(messages);
   }

   public static class TypedMap<V> extends HashMap<String, V> implements Map<String, V> {
      private static final long serialVersionUID = 1L;
      private final V defaultValue;

      public DefaultOptions.TypedMap<V> from(Object k) {
         assert k instanceof Enum || k instanceof String : "Unsafe cast";

         super.put(k.toString(), this.defaultValue);
         return this;
      }

      public DefaultOptions.TypedMap<V> from(Object k, V v) {
         assert k instanceof Enum || k instanceof String : "Unsafe cast";

         super.put(k.toString(), v);
         return this;
      }

      static <V> DefaultOptions.TypedMap<V> create(V defaultValue) {
         return new DefaultOptions.TypedMap(defaultValue);
      }

      @ConstructorProperties({"defaultValue"})
      public TypedMap(V defaultValue) {
         this.defaultValue = defaultValue;
      }
   }

   public static class TypedSet extends HashSet<String> implements Set<String> {
      private static final long serialVersionUID = 1L;

      public DefaultOptions.TypedSet from(Object e) {
         assert e instanceof Enum || e instanceof String : "Unsafe cast";

         super.add(e.toString());
         return this;
      }

      static DefaultOptions.TypedSet create() {
         return new DefaultOptions.TypedSet();
      }
   }

   public static class TypedList extends ArrayList<String> implements List<String> {
      private static final long serialVersionUID = 1L;

      public DefaultOptions.TypedList from(Object e) {
         assert e instanceof Enum || e instanceof String : "Unsafe cast";

         super.add(e.toString());
         return this;
      }

      static DefaultOptions.TypedList create() {
         return new DefaultOptions.TypedList();
      }
   }
}
