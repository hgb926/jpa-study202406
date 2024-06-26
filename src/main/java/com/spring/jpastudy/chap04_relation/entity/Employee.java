package com.spring.jpastudy.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;

@Setter @Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_emp")
public class Employee { // 다 (many)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private long id; // 사원 번호

    @Column(name = "emp_name", nullable = false)
    private String name;

    // 단방향 매핑 - 데이터베이스처럼 한 쪽에 상대방의 PK를 FK로 갖는 형태다.
    @ManyToOne
    @JoinColumn(name = "dept_id") // FK 컬럼명
    private Department department; // 1 (one)

}
