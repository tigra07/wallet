package com.zor07.bootstrap;

import com.zor07.domain.Category;
import com.zor07.domain.EntryType;
import com.zor07.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CategoryLoader implements ApplicationListener<ContextRefreshedEvent>{
    private CategoryRepository repository;

    @Autowired
    public void setRepository(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (int i = 1; i < 10; i++) {
            Category category = new Category();
            category.setType(EntryType.INCOME);
            category.setName("income_" + i);
            category.setRating(i);
            repository.save(category);
        }
        for (int i = 1; i < 10; i++) {
            Category category = new Category();
            category.setType(EntryType.OUTCOME);
            category.setName("outcome_" + i);
            category.setRating(i);
            repository.save(category);
        }
    }
}
