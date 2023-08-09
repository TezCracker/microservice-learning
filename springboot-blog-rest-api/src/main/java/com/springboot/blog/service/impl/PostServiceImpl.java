package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    // Constructor dependecy injection
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper mapper;
    // if a class is configured as a spring bean, if it has only 1 constructor, we can omit the @Autowired

/*     public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }*/

    @Override
    public PostDto createPost(PostDto postDto) {
        // covert Dto into entity
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post); // this the new entity
        // save method returns a newPost that is saved in the DB

        // now convert entity to dto
        PostDto postResponseDto = mapToDTO(newPost);
        return postResponseDto;
    }

    @Override
    public PostResponse getallPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        // for sorting dynamically
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort); // for descending static - Sort.by(sortBy).descending()
        Page<Post> posts = postRepository.findAll(pageable); // PAGE contains getcontent
        List<Post> listofPosts = posts.getContent();
        //List<Post> posts = postRepository.findAll(); // with no pageable
        List<PostDto> content = listofPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse(); // class created for pagination details
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    // Conert Entity to DTO
    private PostDto mapToDTO(Post post){
        PostDto postDto = mapper.map(post,PostDto.class);

        // these code is not required as we have used mappers
      /*  PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());*/
        return postDto;
    }

    // Convert DTO to entity
    private Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);

       /* Post post = new Post();
        post.setTitle(postDto.getTitle()); // get the data from the dto and set in the entity(DB operation column names) class
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/
        return post;
    }
}
