package com.spring.jpastudy.chap02.repository;

import com.spring.jpastudy.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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


    /*
        - JPQL (테이블 접근x 자바 클래스에 접근)

        SELECT 엔터티별칭
        FROM 엔터티클래스명 AS 엔터티별칭 (as 생략 가능)
        WHERE 별칭.필드명

        ex) native - SELECT * FROM tbl_student WHERE stu_name = ?
            JPQL   - SELECT st FROM Student AS st WHERE st.name = ?

     */

    // 도시명으로 학생 1명을 단일 조회
    // Optional은 null 방지 용도
    @Query(value = "SELECT st FROM Student st WHERE st.city = ?1")
    Optional<Student> getByCityWithJPQL(String city);

    // 특정 이름이 포함된 학생 리스트 조회하기
    @Query("SELECT stu FROM Student stu WHERE stu.name LIKE %?1%")
    List<Student> searchByNameWithJPQL(String name);


    // JPQL로 갱신 처리하기

    @Modifying
    // SELECT 외 나머지 쿼리는 무조건 @Modifying을 붙혀야 한다.
    @Query("DELETE FROM Student s WHERE s.name = ?1 AND s.city = ?2")
    void deleteByNameAndCity(String name, String city);

}
