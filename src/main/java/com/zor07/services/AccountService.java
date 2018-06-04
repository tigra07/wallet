package com.zor07.services;

import com.zor07.domain.Account;
import com.zor07.domain.User;
import com.zor07.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements DomainService<Account>{
    private AccountRepository repository;

    @Autowired
    public void setRepository(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Account> list(User user) {
        return repository.findAll().stream()
                .filter(category -> user.equals(category.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    public Account getById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public Account save(Account obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

}
