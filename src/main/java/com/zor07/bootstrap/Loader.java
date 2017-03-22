package com.zor07.bootstrap;

import com.zor07.domain.Category;
import com.zor07.domain.Entry;
import com.zor07.domain.EntryType;
import com.zor07.domain.Source;
import com.zor07.repositories.CategoryRepository;
import com.zor07.repositories.EntryRepository;
import com.zor07.repositories.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent>{
    private CategoryRepository categoryRepository;
    private SourceRepository  sourceRepository;
    private EntryRepository entryRepo;
    private Source source;
    private Source source1;
    private Source source2;

    @Autowired
    public void setEntryRepo(EntryRepository entryRepo) {
        this.entryRepo = entryRepo;
    }

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
        source = new Source();
        source.setName("Сбербанк");
        source.setDescription("Дебетовая карта");
        sourceRepository.save(source);

        source1 = new Source();
        source1.setName("ВТБ24");
        source1.setDescription("Дебетовая карта");
        sourceRepository.save(source1);

        source2 = new Source();
        source2.setName("Тинькофф");
        source2.setDescription("Дебетовая карта");
        sourceRepository.save(source2);

        for (int i = 1; i < 10; i++) {
            Category category = new Category();
            category.setType(EntryType.INCOME);
            category.setName("income_" + i);
            category.setRating(i);
            categoryRepository.save(category);
            createEntries(i, category);
        }

        for (int i = 1; i < 10; i++) {
            Category category = new Category();
            category.setType(EntryType.OUTCOME);
            category.setName("outcome_" + i);
            category.setRating(i);
            categoryRepository.save(category);
            createEntries(i, category);
        }
    }

    private void createEntries(int i, Category category){
        for (int j = 0; j < 4; j++) {
            Entry e = new Entry();
            e.setEntryDate(new java.sql.Date(new Date().getTime()));
            e.setEntryType(category.getType());
            e.setCategory(category);
            switch (i % 3){
                case 0:
                    e.setSource(source2);
                    break;
                case 1:
                    e.setSource(source);
                    break;
                case 2:
                    e.setSource(source1);
                    break;
            }
            e.setAmount(new BigDecimal((i + j) * 3 + 13));
            entryRepo.save(e);
        }
    }

}
