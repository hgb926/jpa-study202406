package com.spring.jpastudy.study.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name= "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "book_id")
    private Long bookId;

    @Setter
    private String title;

    private String author;

    private LocalDate publishedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author authorId;
}
