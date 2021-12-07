package com.example.pos.Model.Entities.BookAuthor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {

    List<BookAuthor> findByBook_isbn(String isbn);
    //List<BookAuthor> findByIndex_author(String index_author);

}
