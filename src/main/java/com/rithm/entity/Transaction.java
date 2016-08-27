package com.rithm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Entity
@Table(name="Transaction")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    @Id
    private String id;
    private Date startDt;
    private Date returnDt;
    private String memberId;
    private String bookId;
}
