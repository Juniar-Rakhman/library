package com.rithm.controller;

import com.rithm.entity.Book;
import com.rithm.entity.Transaction;
import com.rithm.repository.BookRepository;
import com.rithm.repository.TransactionRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by a9jr5626 on 8/27/16.
 */
@RestController
public class TransactionController {


    @Autowired
    BookRepository bookRepo;

    @Autowired
    TransactionRepository transRepo;

    @RequestMapping(value = "/api/transactions", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Object> transactionAdd (@RequestBody Transaction transaction){
        JSONObject entity = new JSONObject();
        try {
            Book book = bookRepo.findOne(transaction.getBookId());
            if (book.getAvailable() == 0) {
                throw new Exception("available count is 0");
            }
            transaction.setStartDt(new Date(System.currentTimeMillis()));

            Calendar c = Calendar.getInstance();
            c.setTime(transaction.getStartDt());
            c.add(Calendar.DATE, 10);
            transaction.setReturnDt(new Date(c.getTimeInMillis()));
            transRepo.save(transaction);
            entity.put("payload", transaction);
            entity.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            entity.put("message", e.getMessage());
            entity.put("success", false);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/transactions", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object> transactionHistory (@RequestBody Transaction filter){
        JSONObject entity = new JSONObject();
        try {
            Iterable<Transaction> transList = null;
            if (filter == null) {
                transList = transRepo.findAll();
            } else {
                //TODO: This sucks. Learn to use querydsl
                transList = transRepo.findByStartDt(filter.getStartDt());
            }
            entity.put("payload", transList);
            entity.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            entity.put("message", e.getMessage());
            entity.put("success", false);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
