package com.spring.jpastudy.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;

@Setter @Getter
@ToString(exclude = "department")
// toString에서 필요없는 컬럼은 뺄 수 있다.
// 연관관계 필드 제외
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
    // EAGER Loading: 연관된 데이터를 항상 JOIN을 통해 같이 가져옴 (성능 이슈 초래)
    // LAZY Loading: 해당 엔터티 데이터만 가져오고 필요한 경우 연관엔터티를 가져옴 (성능 굿)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id") // FK 컬럼명
    private Department department; // 1 (one)

}
