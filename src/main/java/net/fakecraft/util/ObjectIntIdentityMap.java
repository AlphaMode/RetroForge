package net.fakecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import net.fakecraft.core.IdMap;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

public class ObjectIntIdentityMap<T> implements IdMap<T> {
    private IdentityHashMap<T, Integer> byKey = new IdentityHashMap<>(512);
    private List<T> byId = Lists.newArrayList();

    public void func_148746_a(T thing, int id) {
        this.byKey.put(thing, id);

        while (this.byId.size() <= id) {
            this.byId.add(null);
        }

        this.byId.set(id, thing);
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.filter(this.byId.iterator(), Predicates.notNull());
    }
}
