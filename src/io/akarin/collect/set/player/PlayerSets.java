package io.akarin.collect.set.player;

import io.akarin.collect.set.player.bit.PlayerInstanceHashcodeBackedBitSet;
import io.akarin.collect.set.player.bit.PlayerNameHashcodeBackedBitSet;
import io.akarin.collect.set.player.bit.PlayerNameInsensitiveHashcodeBackedBitSet;
import io.akarin.collect.set.player.bit.PlayerUniqueIdHashcodeBackedBitSet;
import io.akarin.collect.set.player.hash.PlayerNameBackedHashSet;
import io.akarin.collect.set.player.hash.PlayerNameInsensitiveBackedHashSet;
import io.akarin.collect.set.player.hash.PlayerUniqueIdBackedHashSet;

public abstract class PlayerSets {
   public static PlayerSet newUsernameHashSet() {
      return new PlayerNameBackedHashSet();
   }

   public static PlayerSet newUsernameInsensitiveHashSet() {
      return new PlayerNameInsensitiveBackedHashSet();
   }

   public static PlayerSet newUniqueIdHashSet() {
      return new PlayerUniqueIdBackedHashSet();
   }

   public static MarkablePlayerSet newUsernameBitSet() {
      return new PlayerNameHashcodeBackedBitSet();
   }

   public static MarkablePlayerSet newUsernameInsensitiveBitSet() {
      return new PlayerNameInsensitiveHashcodeBackedBitSet();
   }

   public static MarkablePlayerSet newUniqueIdBitSet() {
      return new PlayerUniqueIdHashcodeBackedBitSet();
   }

   public static MarkablePlayerSet newInstanceBitSet() {
      return new PlayerInstanceHashcodeBackedBitSet();
   }
}
