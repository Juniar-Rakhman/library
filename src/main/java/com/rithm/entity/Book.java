package com.rithm.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Entity
@Table(name = "Book")
public class Book {
    @Id
    public String id;
    public String title;
    public String category;
    public Date registered;
    public Integer total;
    public Integer available;
}
