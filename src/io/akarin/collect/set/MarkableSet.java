package io.akarin.collect.set;

import java.util.Collection;
import java.util.Set;

public interface MarkableSet<E> extends Set<E> {
   int size();

   boolean isEmpty();

   boolean contains(Object var1);

   boolean add(E var1);

   boolean remove(Object var1);

   boolean containsAll(Collection<?> var1);

   boolean addAll(Collection<? extends E> var1);

   boolean retainAll(Collection<?> var1);

   boolean removeAll(Collection<?> var1);

   void clear();
}
