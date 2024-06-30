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
@Table(name= "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "author_id")
    private Long authorId;

    private String name;

    private LocalDate birthdate;

    @OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Book> bookList = new ArrayList<>();

}
