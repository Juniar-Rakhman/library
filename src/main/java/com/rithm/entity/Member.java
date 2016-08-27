package com.rithm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Entity
@Table(name="Member")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {
    @Id
    private String id;
    private String name;
    private String address;
    private Date registered;
    private Date expired;
}
