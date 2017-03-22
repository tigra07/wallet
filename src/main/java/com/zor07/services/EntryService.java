package com.zor07.services;


import com.zor07.domain.Entry;
import com.zor07.domain.EntryType;
import com.zor07.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EntryService implements DomainService<Entry>{
    
    private EntryRepository repository;

    @Autowired
    public void setRepository(EntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Entry> list() {
        return repository.findAll();
    }

    @Override
    public Entry getById(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public Entry save(Entry obj) {

        return repository.save(obj);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    public List<EntryType> getEntryTypes(){
        return Arrays.asList(EntryType.values());
    }



}
