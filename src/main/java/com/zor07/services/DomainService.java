package com.zor07.services;

import java.util.List;

public interface DomainService<T> {
    List<T> list();

    T getById(Integer id);

    T save(T obj);

    void delete(Integer id);
}