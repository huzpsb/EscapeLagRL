package io.akarin.collect.set.bit;

public abstract class BitValidate {
   public static int hashcode(Object o) {
      return abs(o.hashCode());
   }

   public static int abs(int hashcode) {
      return hashcode < 0 ? unsigned(hashcode) : hashcode;
   }

   public static int unsigned(int hashcode) {
      return hashcode & Integer.MAX_VALUE;
   }
}
