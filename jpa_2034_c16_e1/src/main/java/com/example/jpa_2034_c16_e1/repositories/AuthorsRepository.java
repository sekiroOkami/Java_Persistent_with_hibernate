package com.example.jpa_2034_c16_e1.repositories;

import com.example.jpa_2034_c16_e1.entities.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface AuthorsRepository extends CrudRepository<Author, Integer> {
//    @Query("""
//            SELECT a FROM Author a WHERE a.id= :id
//            """)
//    Optional<Author> findAuthorById(Integer id);
//
//    @Query("""
//            SELECT a FROM Author a
//            """)
//    List<Author> findAllAuthor(Integer id);
}
