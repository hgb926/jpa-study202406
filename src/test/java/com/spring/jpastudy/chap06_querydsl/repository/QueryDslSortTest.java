package com.spring.jpastudy.chap06_querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.jpastudy.chap06_querydsl.entity.Group;
import com.spring.jpastudy.chap06_querydsl.entity.Idol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.spring.jpastudy.chap06_querydsl.entity.QIdol.idol;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false)
class QueryDslSortTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

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
        Idol idol5 = new Idol("장원영", 20, ive);


        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);
        idolRepository.save(idol5);

    }


    @Test
    @DisplayName("QueryDSL로 기본 정렬하기")
    void sortingTest() {
        //given
        List<Idol> sortedIdols = factory
                .selectFrom(idol)
                .orderBy(idol.age.desc())
                .fetch();
        //when

        //then
        assertFalse(sortedIdols.isEmpty());

        System.out.println("\n\n\n");
        sortedIdols.forEach(System.out::println);
        System.out.println("\n\n\n");

        // 추가 검증 예시: 첫 번째 아이돌이 나이가 가장 많고 이름이 올바르게 정렬되었는지 확인
        assertEquals("사쿠라", sortedIdols.get(0).getIdolName());
        assertEquals(26, sortedIdols.get(0).getAge());

    }


    @Test
    @DisplayName("페이징 처리 하기")
    void pagingTest() {
        //given
        int pageNo = 1;
        int amount = 2;

        //when
        List<Idol> pagedIdols = factory
                .selectFrom(idol)
                .orderBy(idol.age.desc())
                .offset((pageNo - 1) * amount) // limit 0,
                .limit(amount) //  2
                .fetch();

        // 총 데이터 수
        Long totalCount = factory
                .select(idol.count())
                .from(idol)
                .fetchOne();

        //then
        System.out.println("\n\n\n");
        pagedIdols.forEach(System.out::println);
        System.out.println("\n\n\n");
        assertTrue(totalCount == 5);

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