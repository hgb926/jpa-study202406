package com.spring.jpastudy.study.repository;

import com.spring.jpastudy.study.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
