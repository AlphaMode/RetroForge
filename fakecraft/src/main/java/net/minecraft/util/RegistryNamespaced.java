package net.minecraft.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RegistryNamespaced<T> {
    protected ObjectIntIdentityMap underlyingIntegerMap;
    protected List<T> registryObjects;
    protected Map<T, T> field_148758_b;

    public String getNameForObject(Object obj) {
        return null;
    }

    public void addObject(int id, String name, Object thing) {}

    public void putObject(Object objectName, Object obj) {}

    public T getObject(String name) {
        return null;
    }

    public T getObjectById(int id) {
        return null;
    }

    public int getIDForObject(T obj) {
        return -1;
    }

    public boolean containsKey(String key) {
        return false;
    }

    public Iterator<T> iterator() {
        return null;
    }
}
