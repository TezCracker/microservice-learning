package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // each Repository need to extend JpaRepository<Model class, PrimaryKey data type>
    // it has all the save, delete, find , pagination and sorting
    // Don't need to add  the @Repository, @Transactional(readOnly = true). becz annotation is already added in the SimpleRespository
}
