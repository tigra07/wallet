package com.zor07.services;


import com.zor07.domain.Category;
import com.zor07.domain.EntryType;
import com.zor07.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements DomainService<Category>{

    private CategoryRepository repository;

    @Autowired
    public void setRepository(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> list() {
        return repository.findAll();
    }

    @Override
    public Category getById(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public Category save(Category obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

}
