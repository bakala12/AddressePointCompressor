package compression.cache.store;

import java.util.HashMap;
import java.util.Map;

public class SimpleMemoryStore<TKey, TValue> implements ICacheStore<TKey, TValue> {

    private final Map<TKey, TValue> internalStore;

    public SimpleMemoryStore(){
        internalStore = new HashMap<>();
    }

    @Override
    public boolean hasKey(TKey key) {
        return internalStore.containsKey(key);
    }

    @Override
    public TValue get(TKey key) {
        return internalStore.get(key);
    }

    @Override
    public void add(TKey key, TValue value){
        internalStore.put(key, value);
    }

    @Override
    public void clear(){
        internalStore.clear();
    }
}
