package com.spring.jpastudy.study.entity;

import lombok.*;

import javax.persistence.*;

@Setter @Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="book") // many
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private Long id;

    @Column(name="book_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id") // 상대방의 PK
    private Author author;
}
