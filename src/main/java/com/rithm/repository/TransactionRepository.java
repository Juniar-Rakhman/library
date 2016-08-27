package com.rithm.repository;


import com.rithm.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

/**
 * Created by a9jr5626 on 8/27/16.
 */
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query("SELECT t FROM Transaction t WHERE t.startDt=:start")
    List<Transaction> findByStartDt(@Param("start") Date start);
}
