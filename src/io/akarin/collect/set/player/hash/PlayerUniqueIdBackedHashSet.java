package io.akarin.collect.set.player.hash;

import io.akarin.collect.set.player.PlayerSet;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUniqueIdBackedHashSet implements PlayerSet, Serializable {
   static final long serialVersionUID = -8976470579519212722L;
   private final Set<UUID> set;

   public PlayerUniqueIdBackedHashSet() {
      this.set = new HashSet();
   }

   public PlayerUniqueIdBackedHashSet(Collection<? extends Player> c) {
      this.set = new HashSet(Math.max((int)((float)c.size() / 0.75F) + 1, 16));
      this.addAll(c);
   }

   public PlayerUniqueIdBackedHashSet(int initialCapacity, float loadFactor) {
      this.set = new HashSet(initialCapacity, loadFactor);
   }

   public PlayerUniqueIdBackedHashSet(int initialCapacity) {
      this.set = new HashSet(initialCapacity);
   }

   public int size() {
      return this.set.size();
   }

   public boolean isEmpty() {
      return this.set.isEmpty();
   }

   public boolean contains(Object o) {
      return this.set.contains(o instanceof Player ? ((Player)o).getUniqueId() : o);
   }

   public Iterator<Player> iterator() {
      return new PlayerUniqueIdBackedHashSet.PlayerUniqueIdIterator();
   }

   public Object[] toArray() {
      return this.set.toArray();
   }

   public <T> T[] toArray(T[] a) {
      return this.set.toArray(a);
   }

   public boolean add(Player e) {
      return this.set.add(e.getUniqueId());
   }

   public boolean remove(Object o) {
      return this.set.remove(o instanceof Player ? ((Player)o).getUniqueId() : o);
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
      boolean modified = false;
      Iterator var3 = c.iterator();

      while(var3.hasNext()) {
         Player e = (Player)var3.next();
         if (this.add(e)) {
            modified = true;
         }
      }

      return modified;
   }

   public boolean retainAll(Collection<?> c) {
      boolean modified = false;
      Iterator it = this.iterator();

      while(it.hasNext()) {
         if (!c.contains(it.next())) {
            it.remove();
            modified = true;
         }
      }

      return modified;
   }

   public boolean removeAll(Collection<?> c) {
      boolean modified = false;
      Iterator it = this.iterator();

      while(it.hasNext()) {
         if (c.contains(it.next())) {
            it.remove();
            modified = true;
         }
      }

      return modified;
   }

   public void clear() {
      this.set.clear();
   }

   @ConstructorProperties({"set"})
   public PlayerUniqueIdBackedHashSet(Set<UUID> set) {
      this.set = set;
   }

   private class PlayerUniqueIdIterator implements Iterator<Player> {
      final Iterator<UUID> it;

      public boolean hasNext() {
         return this.it.hasNext();
      }

      public Player next() {
         return Bukkit.getPlayer((UUID)this.it.next());
      }

      public void remove() {
         this.it.remove();
      }

      public PlayerUniqueIdIterator() {
         this.it = PlayerUniqueIdBackedHashSet.this.set.iterator();
      }
   }
}
