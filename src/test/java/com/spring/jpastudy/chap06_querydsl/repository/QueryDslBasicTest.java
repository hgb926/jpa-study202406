package com.spring.jpastudy.chap06_querydsl.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.jpastudy.chap06_querydsl.entity.Group;
import com.spring.jpastudy.chap06_querydsl.entity.Idol;
import com.spring.jpastudy.chap06_querydsl.entity.QIdol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.spring.jpastudy.chap06_querydsl.entity.QIdol.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false)
class QueryDslBasicTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    // JPA의 CRUD를 제어하는 객체
    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory factory;


    @BeforeEach
    void setUp() {
        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);

        Idol idol1 = new Idol("김채원", 24, leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, leSserafim);
        Idol idol3 = new Idol("가을", 22, ive);
        Idol idol4 = new Idol("리즈", 20, ive);

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);

    }


    @Test
    @DisplayName("JPQL로 특정 아이돌 조회하기")
    void jpqlTest() {
        //given
        String jpqlQuery = "SELECT i FROM Idol i WHERE i.idolName = ?1";
        //when
        Idol foundIdol = em.createQuery(jpqlQuery, Idol.class)
                .setParameter(1, "가을") // 파라미터
                .getSingleResult();// 1건 가져오라
        // then
        assertEquals("아이브", foundIdol.getGroup().getGroupName());

        System.out.println("\n\n\n\n");
        System.out.println("foundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
        System.out.println("\n\n\n\n");
    }


    @Test
    @DisplayName("QueryDsl로 특정 이름의 아이돌 조회하기")
    void queryDslTest() {
        //given
        Idol foundIdol = factory
                //      *
                .select(idol)
                // Q타입.엔터티
                .from(idol) // 반드시 Q타입으로 적어야 함.
                .where(idol.idolName.eq("사쿠라"))
                .fetchOne();

        //then

        System.out.println("foundIdol = " + foundIdol);
        assertEquals("르세라핌", foundIdol.getGroup().getGroupName());
    }


    @Test
    @DisplayName("이름과 나이로 아이돌 조회하기")
    void searchTest() {
        //given
        String name = "리즈";
        int age = 20;
        //when
        Idol foundIdol = factory
                .select(idol)
                .from(idol)
                .where(
                        idol.idolName.eq(name)
                                .and(idol.age.eq(age))
                )
                .fetchOne();

        //then
        System.out.println("foundIdol = " + foundIdol);
        assertNotNull(foundIdol);
        assertEquals("아이브", foundIdol.getGroup().getGroupName());
    }



    @Test
    @DisplayName("조회 결과 반환하기")
    void fetchTest() {

        // 리스트 조회 (fetch)
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                .fetch();

        // 단일행 조회 (fetchOne)
        Idol foundIdol = factory
                .select(idol)
                .from(idol)
                .where(idol.age.lt(21))
                .fetchOne();


        // 단일행 조회시 null safety를 위한 Optional로 받고 싶을 때
        Optional<Idol> foundIdolOptional = Optional.ofNullable(factory
                .select(idol)
                .from(idol)
                .where(idol.age.lt(21))
                .fetchOne());

        Idol foundIdol2 = foundIdolOptional.orElseThrow();
        System.out.println("\n\n============fetch one============\n\n");
        System.out.println("foundIdol2 = " + foundIdol2);

    }


    @Test
    @DisplayName("나이가 24세 이상인 아이돌을 조회.")
    void ageOver24() {
        //given
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                .where(idol.age.goe(24))
                .fetch();
        //when
        System.out.println("idolList = " + idolList);

        //then
    }


    @Test
    @DisplayName("이름에 김 들어간 아이돌 조회")
    void findWhoseNameIncludeKim() {
        //given
        List<Idol> kimList = factory
                .select(idol)
                .from(idol)
                .where(idol.idolName.contains("김"))
                .fetch();
        //when
        System.out.println("kimList = " + kimList);
        assertNotNull(kimList);
        //then
    }
    
    @Test
    @DisplayName("나이가 20세 ~ 25세 사이인 아이돌 조회")
    void findYoungIdols() {
        //given
        List<Idol> girlList = factory
                .select(idol)
                .from(idol)
                .where(idol.age.between(20, 25))
                .fetch();
        //when
        System.out.println("girlList = " + girlList);
        assertEquals(3, girlList.size());
        //then
    }
    
    
    @Test
    @DisplayName("그룹이름이 문자열 르세라핌인 아이돌 조회")
    void findleSserafim() {
        //given
        List<Idol> leSserafimList = factory
                .select(idol)
                .from(idol)
                .where(idol.group.groupName.eq("르세라핌"))
                .fetch();
        //when
        System.out.println("leSserafimList = " + leSserafimList);
        assertEquals(2, leSserafimList.size());
        
        //then
    }


//            idol.idolName.eq("리즈") // idolName = '리즈'
//            idol.idolName.ne("리즈") // username != '리즈'
//            idol.idolName.eq("리즈").not() // username != '리즈'
//            idol.idolName.isNotNull() //이름이 is not null
//            idol.age.in(10, 20) // age in (10,20)
//            idol.age.notIn(10, 20) // age not in (10, 20)
//            idol.age.between(10,30) //between 10, 30
//            idol.age.goe(30) // age >= 30
//            idol.age.gt(30) // age > 30
//            idol.age.loe(30) // age <= 30
//            idol.age.lt(30) // age < 30
//            idol.idolName.like("_김%")  // like _김%
//            idol.idolName.contains("김") // like %김%
//            idol.idolName.startsWith("김") // like 김%
//            idol.idolName.endsWith("김") // like %김




}