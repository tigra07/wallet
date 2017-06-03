package com.zor07.bootstrap;

import com.zor07.domain.*;
import com.zor07.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent>{
    private CategoryRepository categoryRepository;
    private SourceRepository  sourceRepository;
    private EntryRepository entryRepo;
    private UserRepository userRepo;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Autowired
    public void setSourceRepository(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }
    @Autowired
    public void setEntryRepo(EntryRepository entryRepo) {
        this.entryRepo = entryRepo;
    }
    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    int count = 0;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createUsers();
        createSources();
    }

    private void createSources() {
        Source source = new Source();
        source.setName("Сбербанк");
        source.setDescription("Дебетовая карта");
        sourceRepository.save(source);
        createEntries(source);


        Source source1 = new Source();
        source1.setName("ВТБ24");
        source1.setDescription("Дебетовая карта");
        sourceRepository.save(source1);
        createEntries(source1);


        Source source2 = new Source();
        source2.setName("Тинькофф");
        source2.setDescription("Дебетовая карта");
        sourceRepository.save(source2);
        createEntries(source2);
    }

    private void createUsers() {
        User user = new User();

        user.setAuthorities(Arrays.asList(Role.values()));
        user.setUsername("zor");
        user.setPassword(new BCryptPasswordEncoder().encode("pass11"));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        userRepo.save(user);

        User user2 = new User();
        user2.setAuthorities(Arrays.asList(Role.values()));
        user2.setUsername("user");
        user2.setPassword("pass22");
        user2.setAccountNonExpired(true);
        user2.setAccountNonLocked(true);
        user2.setCredentialsNonExpired(true);
        user2.setEnabled(true);
        userRepo.save(user2);
    }

    private void createEntries(Source source){
        for (int i = 1; i < 10; i++) {
            count ++ ;
            Category category = new Category();
            EntryType eType = i % 2 == 0 ? EntryType.INCOME : EntryType.OUTCOME;
            category.setType(eType);

            String eTypeName = eType == EntryType.INCOME ? "income_" : "outcome_";
            category.setName(eTypeName + count);
            category.setRating(i);
            categoryRepository.save(category);
            createEntries(i, category, source);
        }
    }

    private void createEntries(int i, Category category, Source source){
        for (int j = 0; j < 4; j++) {
            Entry e = new Entry();
            e.setEntryDate(getRandDate());
            e.setEntryType(category.getType());
            e.setCategory(category);
            e.setSource(source);
            e.setAmount(new BigDecimal(randBetween(12, 100)));
            entryRepo.save(e);
        }
    }

    private static Date getRandDate(){
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(2010, 2017);
        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return new Date(gc.getTimeInMillis());

    }


    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

}
