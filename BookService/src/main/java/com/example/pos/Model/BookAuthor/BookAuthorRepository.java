package com.example.pos.Model.BookAuthor;

import com.example.pos.Model.Author.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    //boolean
    //public List<Author>

    //@Override
    List<BookAuthor> findByBook_isbn(String isbn);
}
