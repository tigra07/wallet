package com.zor07.bootstrap;

import com.zor07.domain.Category;
import com.zor07.domain.EntryType;
import com.zor07.domain.Source;
import com.zor07.repositories.CategoryRepository;
import com.zor07.repositories.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent>{
    private CategoryRepository categoryRepository;
    private SourceRepository  sourceRepository;

    @Autowired
    public void setSourceRepository(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (int i = 1; i < 10; i++) {
            Category category = new Category();
            category.setType(EntryType.INCOME);
            category.setName("income_" + i);
            category.setRating(i);
            categoryRepository.save(category);
        }
        for (int i = 1; i < 10; i++) {
            Category category = new Category();
            category.setType(EntryType.OUTCOME);
            category.setName("outcome_" + i);
            category.setRating(i);
            categoryRepository.save(category);
        }

        Source source = new Source();
        source.setName("Сбербанк");
        source.setDescription("Дебетовая карта");
        sourceRepository.save(source);

        Source source1 = new Source();
        source1.setName("ВТБ24");
        source1.setDescription("Дебетовая карта");
        sourceRepository.save(source1);

        Source source2 = new Source();
        source2.setName("Тинькофф");
        source2.setDescription("Дебетовая карта");
        sourceRepository.save(source2);
    }
}
