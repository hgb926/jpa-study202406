package com.spring.jpastudy.chap06_querydsl.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.jpastudy.chap06_querydsl.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//import static com.spring.jpastudy.chap06_querydsl.entity.QIdol.idol;
import static com.spring.jpastudy.chap06_querydsl.entity.QAlbum.album;
import static com.spring.jpastudy.chap06_querydsl.entity.QGroup.group;
import static com.spring.jpastudy.chap06_querydsl.entity.QIdol.idol;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
//@Rollback(false)
class QueryDslJoinTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    JPAQueryFactory factory;


    @BeforeEach
    void setUp() {
        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");
        Group bts = new Group("방탄소년단");
        Group newjeans = new Group("뉴진스");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);
        groupRepository.save(bts);
        groupRepository.save(newjeans);

        Idol idol1 = new Idol("김채원", 24, "여", leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, "여", leSserafim);
        Idol idol3 = new Idol("가을", 22, "여", ive);
        Idol idol4 = new Idol("리즈", 20, "여", ive);
        Idol idol5 = new Idol("장원영", 20, "여", ive);
        Idol idol6 = new Idol("안유진", 21, "여", ive);
        Idol idol7 = new Idol("카즈하", 21, "여", leSserafim);
        Idol idol8 = new Idol("RM", 29, "남", bts);
        Idol idol9 = new Idol("정국", 26, "남", bts);
        Idol idol10 = new Idol("해린", 18, "여", newjeans);
        Idol idol11 = new Idol("혜인", 16, "여", newjeans);
        Idol idol12 = new Idol("김종국", 48, "남", null);
        Idol idol13 = new Idol("아이유", 31, "여", null);


        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);
        idolRepository.save(idol5);
        idolRepository.save(idol6);
        idolRepository.save(idol7);
        idolRepository.save(idol8);
        idolRepository.save(idol9);
        idolRepository.save(idol10);
        idolRepository.save(idol11);
        idolRepository.save(idol12);
        idolRepository.save(idol13);

        Album album1 = new Album("MAP OF THE SOUL 7", 2020, bts);
        Album album2 = new Album("FEARLESS", 2022, leSserafim);
        Album album3 = new Album("UNFORGIVEN", 2023, bts);
        Album album4 = new Album("ELEVEN", 2021, ive);
        Album album5 = new Album("LOVE DIVE", 2022, ive);
        Album album6 = new Album("OMG", 2023, newjeans);

        albumRepository.save(album1);
        albumRepository.save(album2);
        albumRepository.save(album3);
        albumRepository.save(album4);
        albumRepository.save(album5);
        albumRepository.save(album6);
    }


    @Test
    @DisplayName("내부 조인 예제")
    void innerJoinTest() {
        //given
        List<Tuple> idolList = factory
                .select(idol, group)
                .from(idol)
                // QueryDsl은 join할때 on절을 쓰지않는다
                // innerJoin에서 파라미터를 2개넣어 한번에 해결
                // 첫번째 파라미터는 from절에 있는 엔터티의 연관 객체
                // 두번째 파라미터는 실제로 조인할 엔터티클래스
                .innerJoin(idol.group, group)
                .fetch();
        //when
        System.out.println("\n\n\n");
        for (Tuple tuple : idolList) {
            Idol idol = tuple.get(QIdol.idol);
            Group group = tuple.get(QGroup.group);
            System.out.println("idol = " + idol);
            System.out.println("group = " + group);
        }
        System.out.println("\n\n\n");
        //then
    }


    @Test
    @DisplayName("Left Outer Join")
    void outerJoinTest() {
        //given

        //when
        List<Tuple> result = factory
                .select(idol, group)
                .from(idol)
                .leftJoin(idol.group, group)
                .fetch();
        //then
        assertFalse(result.isEmpty());
        for (Tuple tuple : result) {
            Idol i = tuple.get(idol);
            Group g = tuple.get(group);

            System.out.println("\nIdol: " + i.getIdolName()
                    + ", Group: "
                    + (g != null ? g.getGroupName() : "솔로가수"));
        }
    }

    
    @Test
    @DisplayName("아이브 그룹에 속한 아이돌의 이름과 그룹명 조회 ")
    void selectIve() {
        //given
        List<Tuple> list = factory
                .select(idol.idolName, group.groupName)
                .from(idol)
                .innerJoin(idol.group, group)
                .where(idol.group.groupName.eq("아이브"))
                .fetch();
        //when
        for (Tuple tuple : list) {
            System.out.println("tuple = " + tuple);
        }

        
        //then
    }
    
    
    @Test
    @DisplayName("그룹별 평균 나이 계산하여 평균 나이가 22세 이상인 그룹의 그룹명과 평균나이 조회")
    void ageSelectTest() {
        //given
        int age = 22;
        //when
        List<Tuple> list = factory
                .select(idol.group.groupName, idol.age.avg())
                .from(idol)
                .innerJoin(idol.group, group)
                .groupBy(idol.group)
                .having(idol.age.avg().goe(age))
                .fetch();
        //then
        for (Tuple tuple : list) {
            System.out.println("tuple = " + tuple);
        }
    }
    
    
    @Test
    @DisplayName("2022년에 발매된 앨범이 있는 아이돌의 이름과 그룹명과 앨범명과 발매년도 조회")
    void selectAlbumReleasedYear() {
        //given
        int year = 2022;
        //when
        List<Tuple> list = factory
                .select(idol.idolName, group.groupName, album.albumName, album.releaseYear)
                .from(idol)
                .innerJoin(idol.group, group)
                .innerJoin(group.albums, album)
                .where(album.releaseYear.in(year))
                .fetch();
        //then
        for (Tuple tuple : list) {
            String name = tuple.get(idol.idolName);
            String gName = tuple.get(group.groupName);
            String aName = tuple.get(album.albumName);
            int releaseYear = tuple.get(album.releaseYear);
            System.out.println("Idol: " + name + ", Group: " + gName + ", Album: " + aName + ", ReleaseYear: " + releaseYear);
        }
    }
    
    



}