package com.example.pos.Model.Book;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    boolean existsByTitle(String title);
    List<Book> findByGenre(String genre);

    @Query("SELECT book FROM Book book WHERE book.release_year = :year")
    List<Book> findByRelease_year(@RequestParam("year") Integer year);

    @Query("SELECT book FROM Book book WHERE book.release_year = :year AND book.genre = :genre")
    List<Book>findByGenreAndYear(@RequestParam("genre")String genre, @RequestParam("year")Integer year);

}
