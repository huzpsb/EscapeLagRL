package io.akarin.collect.set.player.bit;

import io.akarin.collect.set.bit.BitValidate;
import io.akarin.collect.set.player.MarkablePlayerSet;
import io.akarin.collect.set.player.PlayerSet;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import org.bukkit.entity.Player;

public class PlayerInstanceHashcodeBackedBitSet implements PlayerSet, MarkablePlayerSet, Serializable {
   static final long serialVersionUID = -8976470579519212722L;
   private final BitSet set;

   public PlayerInstanceHashcodeBackedBitSet() {
      this.set = new BitSet();
   }

   public int size() {
      return this.set.cardinality();
   }

   public boolean isEmpty() {
      return this.set.isEmpty();
   }

   public boolean contains(Object o) {
      return this.set.get(BitValidate.hashcode((Player)o));
   }

   /** @deprecated */
   @Deprecated
   public Iterator<Player> iterator() {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public Object[] toArray() {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public <T> T[] toArray(T[] a) {
      throw new UnsupportedOperationException();
   }

   public boolean add(Player e) {
      this.set.set(BitValidate.hashcode(e.getName()));
      return true;
   }

   public boolean remove(Object o) {
      this.set.clear(BitValidate.hashcode((Player)o));
      return true;
   }

   public boolean containsAll(Collection<?> c) {
      Iterator var2 = c.iterator();

      Object e;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         e = var2.next();
      } while(this.contains(e));

      return false;
   }

   public boolean addAll(Collection<? extends Player> c) {
      Iterator var2 = c.iterator();

      while(var2.hasNext()) {
         Player e = (Player)var2.next();
         this.add(e);
      }

      return true;
   }

   public boolean retainAll(Collection<?> c) {
      this.set.clear();
      Iterator var2 = c.iterator();

      while(var2.hasNext()) {
         Object e = var2.next();
         this.set.set(BitValidate.hashcode((Player)e));
      }

      return true;
   }

   public boolean removeAll(Collection<?> c) {
      Iterator var2 = c.iterator();

      while(var2.hasNext()) {
         Object e = var2.next();
         this.remove(e);
      }

      return true;
   }

   public void clear() {
      this.set.clear();
   }

   @ConstructorProperties({"set"})
   public PlayerInstanceHashcodeBackedBitSet(BitSet set) {
      this.set = set;
   }
}
