package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utlis.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
 
    @Autowired
    PostService postService;
    // we are injecting interface, so we make a loose coupling , not a tight coupling

    /*public PostController(PostService postService) { // @AutoWired is not required , as only 1 constructor is used
        this.postService = postService;
    }*/

    // create a blog post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all blog post
    @GetMapping
    private PostResponse getAllPosts(
            @RequestParam(value ="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getallPosts(pageNo, pageSize, sortBy, sortDir);
    }

    // get post by id
    @GetMapping("/{id}")
    private ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // update postAPI
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    private ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name ="id") long id){
        //PostDto postResponse = postService.updatePost(postDto,id);
        //return new ResponseEntity<>(postResponse, HttpStatus.OK);
        return new ResponseEntity<>(postService.updatePost(postDto,id), HttpStatus.OK);
    }

    // delete post
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    private ResponseEntity<String> deletePosts(@PathVariable(name = "id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully",HttpStatus.OK);
    }


}
