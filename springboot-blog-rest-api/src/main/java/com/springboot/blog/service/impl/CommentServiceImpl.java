package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        //set post to comment entity
        comment.setPost(post);
        // comment entity to DB
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        // retrieve comments by postID
        List<Comment> comments = commentRepository.findByPostId(postId);
        // convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));

        // retrieve comment by ID
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
            // equals is not working
       /* if (comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST ,"Comment doesn't belongs to post");
        }*/

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        // retrieve comment by ID
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        /* if (comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST ,"Comment doesn't belongs to post");
        }*/
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));

        // retrieve comment by ID
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        // equals is not working
       /* if (comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST ,"Comment doesn't belongs to post");
        }*/
        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);

        /*CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());*/
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);

        /*Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());*/
        return comment;
    }
}
