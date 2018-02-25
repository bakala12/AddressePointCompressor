package compression.cache.store;

public interface ICacheStore<TKey, TValue> {
    boolean hasKey(TKey key);
    TValue get(TKey key);
    void add(TKey key, TValue value);
    void clear();
}
