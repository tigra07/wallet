package com.zor07.bootstrap;

import com.zor07.domain.*;
import com.zor07.repositories.CategoryRepository;
import com.zor07.repositories.EntryRepository;
import com.zor07.repositories.AccountRepository;
import com.zor07.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.GregorianCalendar;

@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent>{
    private CategoryRepository categoryRepository;
    private AccountRepository accountRepository;
    private EntryRepository entryRepo;
    private UserRepository userRepo;
    private static int userCount;
    private static int sourceCount;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
        if (1==1) return;
        for (int j = 0; j < 3; j++) {
            User user = createUser();
            for (int i = 0; i < 2; i++) {
                Account account = createSource(user);
                createEntries(user, account);
            }
        }
        createUser();
    }


    private User createUser(){
        sourceCount = 0;
        User user = new User();

        user.setAuthorities(Arrays.asList(Role.values()));
        user.setUsername(String.format("User_%d", ++userCount));
        user.setPassword("pass" + userCount);
        user.setEmail(String.format("user_%d@email.com", userCount));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        userRepo.save(user);
        return user;
    }


    private Account createSource(User user){
        sourceCount ++;
        Account account = new Account();
        account.setName(user.getUsername() + " debit card " + sourceCount);
        account.setDescription("Дебетовая карта");
        account.setUser(user);
        accountRepository.save(account);
        return account;
    }

    private void createEntries(User user, Account account){
        for (int i = 1; i < 5; i++) {
            count ++ ;
            Category category = new Category();
            EntryType eType = i % 2 == 0 ? EntryType.INCOME : EntryType.OUTCOME;
            category.setType(eType);

            String eTypeName = eType == EntryType.INCOME ? "income_" : "outcome_";
            category.setName(eTypeName + count);
            category.setUser(user);
            category.setRating(i);
            categoryRepository.save(category);
            createEntries(category, account, user);
        }
    }

    private void createEntries(Category category, Account account, User user){
        for (int j = 0; j < 4; j++) {
            Entry e = new Entry();
            e.setUser(user);
            e.setEntryDate(getRandDate());
            e.setEntryType(category.getType());
            e.setCategory(category);
            e.setAccount(account);
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
