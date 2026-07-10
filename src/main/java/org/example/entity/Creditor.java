package org.example.entity;

import org.example.entity.common.Column;
import org.example.entity.common.IEntity;
import org.example.entity.common.NonEditColumn;
import org.example.entity.common.SequenceColumn;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

//Пример, если в БД есть таблица с тремя колонками
public class Creditor implements IEntity {
    @Column(title = "ID")
    @SequenceColumn
    @NonEditColumn
    private Integer id;

    @Column(title = "Name")
    private String name;
    @Column(title = "Age")
    private Integer age;
    @Column(title = "Start Date")
    private LocalDate startDate;

    public Creditor(Integer id, String name, Integer age, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.startDate = startDate;
    }

    public Creditor(Map<String, String> columns) {
        this(
                columns.get("id") != null ? Integer.valueOf(columns.get("id")) : null,
                columns.get("name"),
                Integer.valueOf(columns.get("age")),
                columns.get("startDate") != null ? LocalDate.parse(columns.get("startDate")) : null
        );
    }

    @Override
    public Integer getPk() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDate getStartLocaleDate() {
        return startDate;
    }

    public Date getStartDate() {
        return startDate != null ? Date.valueOf(startDate) : null;
    }
}