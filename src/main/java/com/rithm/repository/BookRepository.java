package com.rithm.repository;

import com.rithm.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String>, QueryDslPredicateExecutor {
    List<Book> findByTitle(@Param("word") String word);
}
