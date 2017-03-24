package com.zor07.services;


import com.zor07.domain.*;
import com.zor07.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class EntryService implements DomainService<Entry>{
    
    private EntryRepository repository;
    private SourceService sourceService;

    @Autowired
    public void setSourceService(SourceService sourceService) {
        this.sourceService = sourceService;
    }

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

    public Set<Entry> findAllMatchingCriteria(SearchCriteria criteria){
        System.out.println("Finding");
        criteria.parse();
        Set<Entry> result = new TreeSet<>();

        if (criteria.filterIsEmpty()){
            System.out.println("Empty filter");
            result.addAll(list());
            return result;
        }

        for (Entry entry : list()){
            /* Filter by FromDate */
            if (criteria.getDateFromFilter() != null){
                Date fromDate = criteria.getDateFromFilter();
                if (entry.getEntryDate().compareTo(fromDate) >= 0){
                    result.add(entry);
                    continue;
                } else {
                    continue;
                }
            }

            /* Filter by ToDate */
            if (criteria.getDateToFilter() != null){
                Date toDate = criteria.getDateToFilter();
                if (entry.getEntryDate().compareTo(toDate) <= 0){
                    result.add(entry);
                    continue;
                } else {
                    continue;
                }
            }

            /* Filter by Source */
            if (criteria.getSourceFilter() != null){
                Integer sourceFilterId = criteria.getSourceFilter().getId();
                Integer entrySourceId = entry.getSource().getId();
                if (entrySourceId.equals(sourceFilterId)){
                    result.add(entry);
                    continue;
                } else {
                    continue;
                }
            }

            /* Filter by EntryType */
            if (criteria.getTypeFilter() != null){
                if (entry.getEntryType().equals(criteria.getTypeFilter())){
                    result.add(entry);
                    continue;
                } else {
                    continue;
                }
            }

            /* Filter by Category */
            if (criteria.getCategoryFilter() != null){
                Integer categoryFilterId = criteria.getCategoryFilter().getId();
                Integer entryCategoryId = entry.getCategory().getId();
                if (categoryFilterId.equals(entryCategoryId)) {
                    result.add(entry);
                }
            }
        }
        System.out.println("Result Length - " + result.size());
        return result;
    }
}
