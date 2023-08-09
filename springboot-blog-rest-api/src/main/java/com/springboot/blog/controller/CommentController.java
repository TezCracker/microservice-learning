package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @Valid  @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentsId}")
    public ResponseEntity<CommentDto> getCommentsById(@PathVariable(value = "postId") Long postId,
                                                      @PathVariable(value = "commentsId") Long commentsId){
        CommentDto commentDto = commentService.getCommentsById(postId,commentsId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentsId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "commentsId") Long commentsId,
                                                    @Valid @RequestBody CommentDto commentDto){
        CommentDto updatedCommentDto = commentService.updateComment(postId,commentsId,commentDto);
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentsId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                      @PathVariable(value = "commentsId") Long commentsId){
         commentService.deleteComment(postId,commentsId);
        return new ResponseEntity<>("Comment deleted Successfully", HttpStatus.OK);
    }

}
