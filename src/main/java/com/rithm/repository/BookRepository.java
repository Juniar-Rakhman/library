package com.rithm.repository;

import com.rithm.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByTitle(@Param("word") String word);

    @Query("SELECT b FROM Book b WHERE b.title like %:title% or b.category like %:category%")
    List<Book> findByFilter(@Param("title") String title, @Param("category") String category);
}
