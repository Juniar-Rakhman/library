package com.rithm.controller;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.rithm.entity.Book;
import com.rithm.entity.QBook;
import com.rithm.repository.BookRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;

/**
 * Created by a9jr5626 on 8/27/16.
 */
@RestController
public class BookController {

    @Autowired
    BookRepository bookRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = "/api/books", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> bookAdd (@RequestBody Book book){
        JSONObject entity = new JSONObject();
        try {
            book.setRegistered(new Date(System.currentTimeMillis()));
            Integer total = bookRepo.findByTitle(book.getTitle()).size() + 1;
            book.setAvailable(1);
            book.setTotal(total);
            bookRepo.save(book);
            entity.put("payload", book);
            entity.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            entity.put("message", e.getMessage());
            entity.put("success", false);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<Object> bookEdit (@RequestBody Book book){
        JSONObject entity = new JSONObject();
        try {
            if (!bookRepo.exists(book.getId())) {
                throw new Exception("Book id is not available : " + book.getId());
            }
            bookRepo.save(book);
            entity.put("payload", book);
            entity.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            entity.put("message", e.getMessage());
            entity.put("success", false);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Object> bookFind (@RequestBody Book filter){
        JSONObject entity = new JSONObject();
        JPQLQuery query = new JPAQuery(entityManager);
        try {
            Iterable<Book> bookList;
            if (filter == null) {
                bookList = bookRepo.findAll();
            } else {
                QBook book = QBook.book;
                bookList =  query.from(book)
                        .where(book.title.eq(filter.getTitle())
                                .and(book.category.eq(filter.getCategory())))
                        .list(book);
            }
            entity.put("payload", bookList);
            entity.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            entity.put("message", e.getMessage());
            entity.put("success", false);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
