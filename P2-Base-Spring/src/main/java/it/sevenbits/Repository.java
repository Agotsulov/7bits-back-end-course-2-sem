package it.sevenbits;

import java.util.List;

public interface Repository<K, V> {

    V get(K k);

    V put(K k, V v);

    List<V> getAll();

}
