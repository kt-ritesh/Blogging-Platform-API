package com.ritesh.Blogging.Platform.API.service;

import com.ritesh.Blogging.Platform.API.model.Post;
import com.ritesh.Blogging.Platform.API.model.User;
import com.ritesh.Blogging.Platform.API.repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    IPostRepo postRepo;
    public String addAPost(Post post) {
        post.setPostCreatedTimeStamp(LocalDateTime.now());
        postRepo.save(post);
        return "New post added..............";
    }

    public String deleteAPost(Long postId, User user) {
        Post post = postRepo.findById(postId).orElse(null);
        if(post!=null && post.getPostOwner().equals(user)){
            postRepo.deleteById(postId);

            return "A post deleted..........";
        } else if (post == null) {
            return "Post does not exists.......... ";
        }
        else {
            return "un-authorized not allowed....";
        }

    }

    public Boolean validatePost(Post blogPost) {
        return blogPost != null && postRepo.existsById(blogPost.getPostId());
    }

    public Post getPostById(Long postId) {
        return postRepo.findById(postId).orElse(null);
    }

    public List<Post> findPostByTitle(String title) {
        return postRepo.findByTitle(title);
    }

    public User findPostOwnerByPostId(Long postId) {
        return postRepo.findPostOwnerByPostId(postId);
    }

    public String updateAPost(Post post, Long postId) {
        Post existingPost = postRepo.findById(postId).orElse(null);

        if(post.getTitle()!=null){
            existingPost.setTitle(post.getTitle());
        }

        if(post.getContent()!=null){
            existingPost.setContent(post.getContent());
        }

        return "Post updated!!";
    }
}
