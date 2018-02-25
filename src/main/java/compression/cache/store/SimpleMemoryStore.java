package compression.cache.store;

import java.util.HashMap;
import java.util.Map;

public class SimpleMemoryStore<TKey, TValue> implements ICacheStore<TKey, TValue> {

    private Map<TKey, TValue> internalStore;

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
}
