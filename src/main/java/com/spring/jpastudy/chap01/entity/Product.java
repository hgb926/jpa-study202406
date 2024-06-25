package com.spring.jpastudy.chap01.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name= "tbl_product") // 테이블 명 지정 가능
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    // Oracle = SEQUENCE.       Mariadb, MySql = IDENTITY
    @Column(name= "prod_id")
    private Long id; // PK

    @Column(name= "prod_nm", length = 30, nullable = false) // VARCHAR(30) NOT NULL
    private String name; // 상품명

    @Column(name= "price")
    private int price; // 상품 가격

    @CreationTimestamp // INSERT시에 자동으로 서버시간 저장
    @Column(updatable = false) // 수정 불가
    private LocalDateTime createdAt; // 상품 등록시간

    @UpdateTimestamp // UPDATE문 실행시 자동으로 시간이 저장
    private LocalDateTime updatedAt; // 상품 수정시간

    // 데이터베이스에는 저장 안하고 클래스 내부에서만 사용할 필드
    @Transient
    private String nickName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // EnumType.ORDINAL로 하면 int 타입으로 됨
    private Category category; // 상품 카테고리

    public enum Category {
        FOOD, FASHION, ELECTRONIC
    }

}
