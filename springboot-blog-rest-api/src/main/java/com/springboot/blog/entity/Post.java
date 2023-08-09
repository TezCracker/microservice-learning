package com.springboot.blog.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id  // primary key
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    ) // Auto increment
    private long id;
    @Column(name = "title", nullable = false, updatable = false) // even is @Column is not used JPA will add column name
    private String title;
    @Column(name ="description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;

    // Set doesn't allow duplicates
    // bidirectional mapping
    // CascadeType.ALL , whenever we do some change to parents , it's applicable to child as well
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();


}
