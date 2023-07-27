package com.ritesh.Blogging.Platform.API.repository;

import com.ritesh.Blogging.Platform.API.model.Comment;
import com.ritesh.Blogging.Platform.API.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogPost(Post validPost);
}
