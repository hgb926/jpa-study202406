package com.spring.jpastudy.study.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="author_id")
    private Long id;

    @Column(name="author_name", nullable = false)
    private String name;

//    @OneToMany
//    private List<Book> bookList; // 한 저자가 쓴 책 목록
}
