package com.springboot.blog.payload;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {
    // Can also mention as  dto package
    private  long id;
    // Suppose for a requirement , the validation needed are
    // title should not be null or empty // title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

}
