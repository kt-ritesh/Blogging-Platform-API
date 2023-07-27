package com.ritesh.Blogging.Platform.API.controller;

import com.ritesh.Blogging.Platform.API.dto.SignInInput;
import com.ritesh.Blogging.Platform.API.dto.SignUpOutput;
import com.ritesh.Blogging.Platform.API.model.Comment;
import com.ritesh.Blogging.Platform.API.model.Follow;
import com.ritesh.Blogging.Platform.API.model.Post;
import com.ritesh.Blogging.Platform.API.model.User;
import com.ritesh.Blogging.Platform.API.service.AuthenticationService;
import com.ritesh.Blogging.Platform.API.service.UserService;
import jakarta.validation.Valid;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("user/signup")
    public SignUpOutput SignUpUser(@RequestBody User user){
        return userService.signUpUser(user);
    }

    @PostMapping("user/signin")
    public String signInUser(@RequestBody @Valid SignInInput signInInput){
        return userService.signInUser(signInInput);
    }

    @DeleteMapping("user/sign-out")
    public String signOutUser(@RequestParam String userName, @RequestParam String Token){
        if (authenticationService.authenticate(userName, Token)) {
            return userService.signOutUser(userName);
        }else{
            return "Invalid username and token........";
        }
    }

    @PostMapping("post")
    public String addAPost(@RequestBody Post post, @RequestParam String userName, @RequestParam String Token){
        if (authenticationService.authenticate(userName, Token)) {
            return userService.addAPost(post, userName);
        }else{
            return "Invalid username and token........";
        }
    }

    @DeleteMapping("post/delete")
    public String deleteAPost(@RequestParam Long postId, @RequestParam String userName, @RequestParam String Token){
        if (authenticationService.authenticate(userName, Token)) {
            return userService.deleteAPost(postId, userName);
        }else{
            return "Invalid username and token........";
        }
    }
    @PutMapping("post/update")
    public String updateAPost(@RequestParam Long postId, @RequestParam String userName, @RequestParam String Token, @RequestBody Post post){
        if (authenticationService.authenticate(userName, Token)) {
            return userService.updateAPost(postId, userName, post);
        }else{
            return "Invalid username and token........";
        }
    }

    @GetMapping("post/{title}")
    public List<Post> findPostByTitle(@PathVariable String title, @RequestParam String userName, @RequestParam String Token){
        if (authenticationService.authenticate(userName, Token)) {
            return userService.findPostByTitle(title);
        }else{
            List<Post > val = new ArrayList();
            System.out.println("Invalid username and token........");
            return val;
        }
    }

    @PostMapping("comment")
    public String addCommentOnPost(@RequestBody Comment comment, @RequestParam String commenterUserName, @RequestParam String commenterToken){
        if (authenticationService.authenticate(commenterUserName, commenterToken)) {
            return userService.addCommentOnPost(comment, commenterUserName);
        }else{
            return "Invalid username and token........";
        }
    }

    @DeleteMapping("comment/remove")
    public String removeComment(@RequestParam Long commentId, @RequestParam String userName, @RequestParam String Token){
        if (authenticationService.authenticate(userName, Token)) {
            return userService.removeComment(commentId, userName);
        }else{
            return "Invalid username and token........";
        }
    }

    @GetMapping("comment/count/{postId}")
    public String getPostCommentCount(@PathVariable Long postId, @RequestParam String userName, @RequestParam String Token){
        if (authenticationService.authenticate(userName, Token)) {
            return userService.getPostCommentCount(postId, userName);
        }else{
            return "Invalid username and token........";
        }
    }

    @GetMapping("comments/{postId}")
    public List<String> getAllComments(@PathVariable Long postId, @RequestParam String userName, @RequestParam String Token){
        if (authenticationService.authenticate(userName, Token)) {
            return userService.getAllComments(postId, userName);
        }else{
            List<String> val = new ArrayList<>();
            val.add("Invalid username and token........");
            return val;
        }
    }

    @PostMapping("follow")
    public String followUser(@RequestBody Follow follow, @RequestParam String followerUserName, @RequestParam String followerToken){
        if (authenticationService.authenticate(followerUserName, followerToken)) {
            return userService.followUser(follow, followerUserName);
        }else{
            return "Invalid username and token........";
        }
    }

    @DeleteMapping("unfollow")
    public String unfollowUser(Long followId, @RequestParam String followerUserName, @RequestParam String followerToken){
        if (authenticationService.authenticate(followerUserName, followerToken)) {
            return userService.unfollowUser(followId, followerUserName);
        }else{
            return "Invalid username and token........";
        }
    }

    @GetMapping("follower/count/{userId}")
    public String getAllFollowerCount(@PathVariable Long userId, @RequestParam String UserName, @RequestParam String Token){
        if (authenticationService.authenticate(UserName, Token)) {
            return userService.getAllFollowerCount(userId);
        }else{
            return "Invalid username and token........";
        }
    }

}
