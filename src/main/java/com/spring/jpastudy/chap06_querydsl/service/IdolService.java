package com.spring.jpastudy.chap06_querydsl.service;

import com.spring.jpastudy.chap06_querydsl.entity.Idol;
import com.spring.jpastudy.chap06_querydsl.repository.IdolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional // JPA, QueryDsl 쓸 때 잊지말 것!
public class IdolService {

    public final IdolRepository idolRepository;

    // 아이돌을 나이 순으로 내림차 정렬해서 조회
    public List<Idol> getIdols() {

       // 방법 1
//        List<Idol> idolList = idolRepository.findAll();
//        return idolList.stream()
//                .sorted(Comparator.comparing(Idol::getAge).reversed())
//                .collect(Collectors.toList());

        // 방법 2
//        List<Idol> idolList = idolRepository.findAllBySorted();

        List<Idol> idolList = idolRepository.foundByGroupName();

        return idolList;
    }
}
