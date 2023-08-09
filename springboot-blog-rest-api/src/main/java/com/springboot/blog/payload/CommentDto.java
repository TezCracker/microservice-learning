package com.springboot.blog.payload;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
public class CommentDto {
    private long id;
    @NotEmpty(message = "name can't be null or empty")
    private String name;
    @NotEmpty(message = "email can't be null or empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 10 , message = "Comment body must have a at least 10 characters")
    private String body;
}
