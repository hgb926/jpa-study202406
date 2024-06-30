package com.spring.jpastudy.study.repository;

import com.spring.jpastudy.study.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        Book book1 = Book.builder()
                .author("한기범")
                .title("테스트책1")
                .publishedDate(LocalDate.now())
                .build();
        Book book2 = Book.builder()
                .author("양지훈")
                .title("테스트책2")
                .publishedDate(LocalDate.now())
                .build();
        Book book3 = Book.builder()
                .author("백성현")
                .title("테스트책3")
                .publishedDate(LocalDate.now())
                .build();
        Book book4 = Book.builder()
                .author("박세환")
                .title("테스트책4")
                .publishedDate(LocalDate.now())
                .build();
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
    }


    @Test
    @DisplayName("새로운 책 저장")
    void saveTest() {
        //given
        Book book = Book.builder()
                .author("한기범")
                .title("세상 사는 법")
                .publishedDate(LocalDate.now())
                .build();
        //when
        bookRepository.save(book);

        //then
        System.out.println("book = " + book);
        assertTrue(1 == bookRepository.count());
    }


    @Test
    @DisplayName("모든책 조회 테스트")
    void findAllTest() {
        //given
        List<Book> all = bookRepository.findAll();
        //when
        all.forEach(System.out::println);

        //then
    }
    
    
    @Test
    @DisplayName("상세조회")
    void findOneTest() {
        //given
        Long no = 2L;
        //when
        Book foundBook = bookRepository.findById(no).orElseThrow();
        //then
    }


    @Test
    @DisplayName("책 한권의 정보를 업데이트")
    void modifyTest() {
        //given
        Long id = 2L;
        String text = "양지훈의 세상사는 법";
        //when
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(text);
        //then
        bookRepository.save(book);
        assertTrue(book.getTitle().equals(text));
        System.out.println("book = " + book);
    }


    @Test
    @DisplayName("삭제테스트")
    void deleteTest() {
        //given
        Long id = 3L;
        //when
        bookRepository.deleteById(id);

        //then
        assertTrue(3 == bookRepository.count());
    }


    @Test
    @DisplayName("새로운 작가와 책을 저장.")
    void saveEachTest() {
        //given
        Book book5 = Book.builder()
                .author("박세환")
                .title("테스트책5")
                .publishedDate(LocalDate.now())
                .build();
        //when
        bookRepository.save(book5);


        //then
        assertTrue(5 == bookRepository.count());
        assertTrue(1 == authorRepository.count());
    }




}