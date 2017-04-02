package com.zor07.services;


import com.zor07.domain.Category;
import com.zor07.domain.EntryType;
import com.zor07.repositories.CategoryRepository;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
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

    public Category getByTypeAndName(EntryType type, String name){
        for (Category category : list()){
            if (category.getType().equals(type) && category.getName().equals(name))
                return category;
        }
        return null;
    }

    public Category getByName(String name){
        for (Category category : list()){
            if (category.getName().equals(name))
                return category;
        }
        return null;
    }


}
