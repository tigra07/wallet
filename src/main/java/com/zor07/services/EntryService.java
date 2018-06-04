package com.zor07.services;


import com.zor07.domain.*;
import com.zor07.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EntryService implements DomainService<Entry>{
    
    private EntryRepository repository;

    @Autowired
    public void setRepository(EntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Entry> list(User user) {
        return repository.findAll().stream()
                .filter(category -> user.equals(category.getUser()))
                .collect(Collectors.toList());
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

    public List<Entry> findAllMatchingCriteria(SearchCriteria criteria){
        criteria.parse();
        List<Entry> result = new ArrayList<>();
        result.addAll(list(criteria.getUserFilter()));
        if (criteria.filterIsEmpty())
            return result;

        Date dateFrom = criteria.getDateFromFilter();
        Date dateTo = criteria.getDateToFilter();
        EntryType typeFilter = criteria.getTypeFilter();

        Integer categoryFilterId = criteria.getCategoryFilter() == null
                ? null
                : criteria.getCategoryFilter().getId();

        Integer accountFilterId = criteria.getAccountFilter() == null
                ? null
                : criteria.getAccountFilter().getId();

        /* Filter by FromDate */
        if (dateFrom != null){
            result = result.stream()
                    .filter(e -> e.getEntryDate().compareTo(dateFrom) >= 0)
                    .collect(Collectors.toList());
        }

        /* Filter by ToDate */
        if (dateTo != null){
            result = result.stream()
                    .filter(e -> e.getEntryDate().compareTo(dateTo) <= 0)
                    .collect(Collectors.toList());
        }

        /* Filter by Account */
        if (accountFilterId != null){
            result = result.stream()
                    .filter(e -> e.getAccount().getId().equals(accountFilterId))
                    .collect(Collectors.toList());
        }

        /* Filter by EntryType */
        if (typeFilter != null){
            result = result.stream()
                    .filter(e -> e.getEntryType().equals(typeFilter))
                    .collect(Collectors.toList());
        }

        /* Filter by Category */
        if (categoryFilterId != null){
            result =  result.stream()
                    .filter(e -> e.getCategory().getId().equals(categoryFilterId))
                    .collect(Collectors.toList());
        }

        return result;
    }
}
