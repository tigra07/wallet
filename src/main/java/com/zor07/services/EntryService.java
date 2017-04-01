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

    public List<Entry> findAllMatchingCriteria(SearchCriteria criteria){
        criteria.parse();
        List<Entry> result = new ArrayList<>();
        result.addAll(list());
        if (criteria.filterIsEmpty())
            return result;

        Date dateFrom = criteria.getDateFromFilter();
        Date dateTo = criteria.getDateToFilter();
        EntryType typeFilter = criteria.getTypeFilter();

        Integer categoryFilterId = criteria.getCategoryFilter() == null
                ? null
                : criteria.getCategoryFilter().getId();

        Integer sourceFilterId = criteria.getSourceFilter() == null
                ? null
                : criteria.getSourceFilter().getId();

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

        /* Filter by Source */
        if (sourceFilterId != null){
            result = result.stream()
                    .filter(e -> e.getSource().getId().equals(sourceFilterId))
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
