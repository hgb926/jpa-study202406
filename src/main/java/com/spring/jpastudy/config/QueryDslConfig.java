package com.spring.jpastudy.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// QueryDsl 세팅
@Configuration
public class QueryDslConfig {

    @PersistenceContext // 영속성 주입
    private EntityManager em;

    @Bean // 외부라이브러리를 스프링 컨테이너에 관리시키는 설정
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
