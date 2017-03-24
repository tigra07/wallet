package com.zor07.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class Entry implements Comparable<Entry> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;

    private Date entryDate;

    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    @ManyToOne
    @JoinColumn
    private Category category;

    @ManyToOne
    @JoinColumn
    private Source source;

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

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", version=" + version +
                ", entryDate=" + entryDate +
                ", entryType=" + entryType +
                ", category=" + category +
                ", amount=" + amount +
                '}';
    }

    @Override
    public int compareTo(Entry o) {
        return id.compareTo(o.getId());
    }
}
