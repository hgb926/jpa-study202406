package com.spring.jpastudy.chap03_page;

import com.spring.jpastudy.chap02.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentPageRepository extends JpaRepository<Student, String> {

    // 전체조회 상황에서 페이징 처리하기

    // Import해온 Page 클래스에는 필요한 것들이 이미 다 구현되어 있다.
    Page<Student> findAll(Pageable pageable);

    // 검색 + 페이징
    Page<Student> findByNameContaining(String name, Pageable pageable);


}
