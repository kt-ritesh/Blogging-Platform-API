package com.ritesh.Blogging.Platform.API.repository;

import com.ritesh.Blogging.Platform.API.model.Post;
import com.ritesh.Blogging.Platform.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepo extends JpaRepository<Post, Long> {
    List<Post> findByTitle(String title);

    User findPostOwnerByPostId(Long postId);
}
