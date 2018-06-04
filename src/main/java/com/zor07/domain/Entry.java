package com.zor07.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class Entry implements Comparable<Entry> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    private Integer version;

    @JoinColumn
    @ManyToOne
    private User user;

    private Date entryDate;

    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    @ManyToOne
    @JoinColumn
    private Category category;

    @ManyToOne
    @JoinColumn
    private Account account;

    private BigDecimal amount;

    public Entry() {
    }

    public Entry(EntryType entryType) {
        this.entryType = entryType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", version=" + version +
                ", user=" + user +
                ", entryDate=" + entryDate +
                ", entryType=" + entryType +
                ", category=" + category +
                ", account=" + account +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        boolean res = entry.entryDate.equals(entryDate);
        res &= entry.id.equals(id);
        return res;

    }

    @Override
    public int hashCode() {
        return id == null || entryDate == null
            ? super.hashCode()
            : id.hashCode() + entryDate.hashCode();
    }



    @Override
    public int compareTo(Entry o) {
        int res = o.getEntryDate().compareTo(entryDate);
        if (res == 0){
            res = o.id.compareTo(id);
        }
        return res;
    }
}
