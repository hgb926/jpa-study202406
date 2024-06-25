package com.spring.jpastudy.chap02.repository;

import com.spring.jpastudy.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {

    // 쿼리메서드: 메서드의 이름에 특별한 규칙을 적용하면
    // SQL이 규칙에 맞게 생성됨.

    //  findBy + 필드명, findBy는 규칙(고정)
    List<Student> findByName(String name);

    // 규칙에 맞게 WHERE 조건 추가한 메서드
    List<Student> findByCityAndMajor(String city, String Major);

    // Containing은 '포함'의 의미
    // WHERE major like '%major%'
    List<Student> findByMajorContaining(String Major);

    // WHERE major like 'major%'
    List<Student> findByMajorStartingWith(String Major);

    // WHERE major like '%major'
    List<Student> findByMajorEndingWith(String Major);

    // WHERE age <= ?
//    List<Student> findByAgeLessThanEqual(int age);

    // 순수한 SQL
    // native sql 사용하기 (메서드 명은 막 지어도 됨. 규칙에 해당되지 않음)
    //                              파라미터 작명 내맘대로. @Param과 통일만 시켜준다  nativeQuery값은 필수
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :snm OR city = :city", nativeQuery = true)
    List<Student> getStudentByNameOrCity(@Param("snm") String name, @Param("city") String city);


    // 다른 방법. 파라미터가 ?1 부터 자동으로 바인딩 된다.
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = ?1 OR city = ?2", nativeQuery = true)
    List<Student> getStudentByNameOrCity2(String name, String city);
}
