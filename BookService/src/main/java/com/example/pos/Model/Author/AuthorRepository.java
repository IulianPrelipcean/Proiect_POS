package com.example.pos.Model.Author;

import com.example.pos.Model.Book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT author FROM Author author WHERE author.first_name LIKE %:first_name% ")
    List<Author> findByNamePartialMatch(@RequestParam("first_name") String first_name);

    @Query("SELECT author FROM Author author WHERE author.first_name = :first_name ")
    List<Author> findByNameExactMatch(@RequestParam("first_name") String first_name);

}
