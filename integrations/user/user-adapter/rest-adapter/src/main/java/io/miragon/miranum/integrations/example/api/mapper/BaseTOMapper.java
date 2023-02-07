package io.miragon.miranum.integrations.example.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Map between transport and domain object.
 *
 * @param <T> Transport object
 * @param <M> Domain object
 */
public interface BaseTOMapper<T, M> {

    M map(T to);

    default List<M> map(final List<T> list) {
        return list.stream().map(this::map).collect(Collectors.toList());
    }

    T map2TO(M model);

    default List<T> map2TO(final List<M> list) {
        return list.stream().map(this::map2TO).collect(Collectors.toList());
    }
}
