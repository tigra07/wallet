package com.zor07.services;

import com.zor07.domain.User;

import java.util.List;

public interface DomainService<T> {
    List<T> list(User user);

    T getById(Integer id);

    T save(T obj);

    void delete(Integer id);
}