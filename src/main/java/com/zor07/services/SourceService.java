package com.zor07.services;

import com.zor07.domain.Source;
import com.zor07.repositories.SourceRepository;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceService implements DomainService<Source>{
    private SourceRepository repository;

    @Autowired
    public void setRepository(SourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Source> list() {
        return repository.findAll();
    }

    @Override
    public Source getById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public Source save(Source obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    public Source findByName(String name){
        for (Source source : list()){
            if (source.getName().equals(name)) return source;
        }
        return null;
    }
}
