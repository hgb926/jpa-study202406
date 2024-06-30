package com.spring.jpastudy.study.repository;

import com.spring.jpastudy.study.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
