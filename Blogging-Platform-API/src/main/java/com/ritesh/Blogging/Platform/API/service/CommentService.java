package com.ritesh.Blogging.Platform.API.service;

import com.ritesh.Blogging.Platform.API.model.Comment;
import com.ritesh.Blogging.Platform.API.model.Post;
import com.ritesh.Blogging.Platform.API.repository.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    ICommentRepo commentRepo;
    public String addComment(Comment comment) {
        comment.setCommentCreationTimeStamp(LocalDateTime.now());
        commentRepo.save(comment);
        return "Comment has been added!!!";
    }

    public Comment findComment(Long commentId) {
        return commentRepo.findById(commentId).orElse(null);
    }

    public void removeComment(Comment comment) {
        commentRepo.delete(comment);
    }

    public Integer findAllCommentsCount(Post validPost) {
        List<Comment> posts =  commentRepo.findByBlogPost(validPost);

        return posts.size();
    }

    public List<Comment> getAllComments(Post validPost) {
        return commentRepo.findByBlogPost(validPost);
    }
}
