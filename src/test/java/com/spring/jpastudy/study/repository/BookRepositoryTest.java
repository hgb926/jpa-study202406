package com.spring.jpastudy.study.repository;

import com.spring.jpastudy.study.entity.Author;
import com.spring.jpastudy.study.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

//    @BeforeEach
    void setUp() {
        Author author1 = Author.builder()
                .name("한기범")
                .build();
        Author author2 = Author.builder()
                .name("양지훈")
                .build();
        authorRepository.save(author1);
        authorRepository.save(author2);

        Book book1 = Book.builder()
                .name("책1")
                .author(author1)
                .build();
        Book book2 = Book.builder()
                .name("책2")
                .author(author1)
                .build();
        Book book3 = Book.builder()
                .name("책3")
                .author(author2)
                .build();
        Book book4 = Book.builder()
                .name("책4")
                .author(author2)
                .build();
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
    }
    
    
    @Test
    @DisplayName("dummy Test")
    void dummyTest() {
        //given

        //when
        
        //then
    }


    @Test
    @DisplayName("select")
    void selectTest() {
        //given
        List<Book> books = bookRepository.findAll();
        List<Author> authors = authorRepository.findAll();
        //when
        books.forEach(System.out::println);
        authors.forEach(System.out::println);
        //then
    }




}