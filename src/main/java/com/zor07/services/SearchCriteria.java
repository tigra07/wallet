package com.zor07.services;

import com.zor07.bootstrap.ApplicationContextHolder;
import com.zor07.domain.Account;
import com.zor07.domain.Category;
import com.zor07.domain.EntryType;
import com.zor07.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by anzor on 22.03.17.
 */
public class SearchCriteria {

    private User userFilter;
    private Date dateFromFilter;
    private Date dateToFilter;
    private Account accountFilter;
    private EntryType typeFilter;
    private Category categoryFilter;

    private String dateFromFilterStr;
    private String dateToFilterStr;
    private String accountFilterStr;
    private String typeFilterStr;
    private String categoryFilterStr;

    private AccountService accountService;
    private CategoryService categoryService;
    private UserService userService;

    public SearchCriteria() {
    }

    void parse(){
        if (accountService == null){
            accountService = ApplicationContextHolder.getContext().getBean(AccountService.class);
        }
        if (categoryService == null){
            categoryService = ApplicationContextHolder.getContext().getBean(CategoryService.class);
        }
        if (userService == null){
            userService = ApplicationContextHolder.getContext().getBean(UserService.class);
        }
        dateFromFilter = "".equals(dateFromFilterStr)
                ? null
                : parseDate(dateFromFilterStr);

        dateToFilter = "".equals(dateToFilterStr)
                ? null
                : parseDate(dateToFilterStr);


        accountFilter = "".equals(accountFilterStr)
                ? null
                : accountService.getById(Integer.parseInt(accountFilterStr));

        typeFilter = "".equals(typeFilterStr)
                ? null
                : EntryType.valueOf(typeFilterStr);

        categoryFilter = "".equals(categoryFilterStr)
                ? null
                : categoryService.getById(Integer.parseInt(categoryFilterStr));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        userFilter = (User) userService.loadUserByUsername(name);
    }

    boolean filterIsEmpty(){
        return dateFromFilter == null
            && dateToFilter   == null
            && accountFilter == null
            && typeFilter     == null
            && categoryFilter == null;
    }


    private Date parseDate(String strDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = null;
        try {
            parsed = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(parsed.getTime());
    }

    public Date getDateFromFilter() {
        return dateFromFilter;
    }

    public Date getDateToFilter() {
        return dateToFilter;
    }

    public Account getAccountFilter() {
        return accountFilter;
    }

    public EntryType getTypeFilter() {
        return typeFilter;
    }

    public Category getCategoryFilter() {
        return categoryFilter;
    }

    public User getUserFilter() {
        return userFilter;
    }

    public void setUserFilter(User userFilter) {
        this.userFilter = userFilter;
    }

    public void setDateFromFilterStr(String dateFromFilterStr) {
        this.dateFromFilterStr = dateFromFilterStr;
    }

    public void setDateToFilterStr(String dateToFilterStr) {
        this.dateToFilterStr = dateToFilterStr;
    }

    public void setAccountFilterStr(String accountFilterStr) {
        this.accountFilterStr = accountFilterStr;
    }

    public void setTypeFilterStr(String typeFilterStr) {
        this.typeFilterStr = typeFilterStr;
    }

    public void setCategoryFilterStr(String categoryFilterStr) {
        this.categoryFilterStr = categoryFilterStr;
    }

}
