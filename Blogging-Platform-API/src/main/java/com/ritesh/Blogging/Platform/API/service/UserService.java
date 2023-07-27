package com.ritesh.Blogging.Platform.API.service;

import com.ritesh.Blogging.Platform.API.dto.SignInInput;
import com.ritesh.Blogging.Platform.API.dto.SignUpOutput;
import com.ritesh.Blogging.Platform.API.model.*;
import com.ritesh.Blogging.Platform.API.repository.IUserRepo;
import com.ritesh.Blogging.Platform.API.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    FollowService followService;

    public SignUpOutput signUpUser(User user){

        Boolean signUpStatus = true;
        String signUpMessage = null;

        // checking valid ser name
        String newUserName = user.getUserName();

        if(newUserName == null){
            signUpStatus = false;
            signUpMessage = "Please enter a valid user name!";

            return new SignUpOutput(signUpStatus, signUpMessage);
        }

        // checking user already registered or not
        User existingUser = userRepo.findFirstByUserName(newUserName);

        if(existingUser != null){
            signUpStatus = false;
            signUpMessage = "User already registered!!!!!!";

            return new SignUpOutput(signUpStatus, signUpMessage);
        }


        // encrypting password and saving in user database
        try {
            String encryptPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            user.setUserPassword(encryptPassword);

            userRepo.save(user);

            signUpStatus = true;
            signUpMessage = "User sign Up successfully!!";

            return new SignUpOutput(signUpStatus, signUpMessage);

        } catch (Exception e) {
            signUpStatus = false;
            signUpMessage = "Internal error during sign Up!!";

            return new SignUpOutput(signUpStatus, signUpMessage);
        }
    }

    // login for register user
    public String signInUser(SignInInput signInInput) {
        // getting username from input info
        String loginUserName = signInInput.getUserName();

        // checking username valid or not
        if(loginUserName == null ){
            return "Invalid user name \n please enter valid user name.....";
        }

        // checking username register or not
        User existingUser = userRepo.findFirstByUserName(loginUserName);

        if(existingUser == null){
            return "User not register please register first......";
        }

        // matching the username and password and generating token value
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());

            if(existingUser.getUserPassword().equals(encryptedPassword)){

                //session should be created since password matched and user id is valid
                AuthenticationToken authToken = new AuthenticationToken(existingUser);

                authenticationService.saveAuthToken(authToken);


                return authToken.getTokenValue();
            }else {
                return  "Invalid credentials....\n please check your user name and password....";
            }
        } catch (Exception e) {
            return "Internal error during login please try latter.......";
        }
    }

    // sign out the user
    public String signOutUser(String userName) {

        User user = userRepo.findFirstByUserName(userName);

        AuthenticationToken authToken = authenticationService.findFirstByUser(user);

        authenticationService.removeToken(authToken);

        return "sign out successfully......";
    }


    public String addAPost(Post post, String userName) {

        User postOwner = userRepo.findFirstByUserName(userName);
        post.setPostOwner(postOwner);
        return postService.addAPost(post);
    }

    public String deleteAPost(Long postId, String userName) {
        User user = userRepo.findFirstByUserName(userName);

        return postService.deleteAPost(postId, user);
    }

    public String updateAPost(Long postId, String userName, Post post) {
        User givenUser = userRepo.findFirstByUserName(userName);
        User postOwner = postService.findPostOwnerByPostId(postId);

        if(postOwner != null){
            if(givenUser.equals(postOwner)){
                return postService.updateAPost(post, postId);
            }
            else {
                return "Un - authorized update request!!!";
            }
        }else {
            return "Invalid user!!";
        }


    }

    public List<Post> findPostByTitle(String title) {
        return postService.findPostByTitle(title);
    }

    public String addCommentOnPost(Comment comment, String commenterUserName) {
        boolean validPost = postService.validatePost(comment.getBlogPost());
        if(validPost){
            User commenter = userRepo.findFirstByUserName(commenterUserName);
            comment.setCommenter(commenter);
            return commentService.addComment(comment);
        }
        else {
            return "Cannot comment on Invalid Post!!";
        }
    }

    private boolean AuthorizedCommentRemover(Comment comment, String userName) {
        String commenterUserName = comment.getCommenter().getUserName();
        String postOwnerName = comment.getBlogPost().getPostOwner().getUserName();

        return commenterUserName.equals(userName) || postOwnerName.equals(userName);
    }
    public String removeComment(Long commentId, String userName) {

        Comment comment = commentService.findComment(commentId);

        if(comment!=null){
            if(AuthorizedCommentRemover(comment, userName)){
                commentService.removeComment(comment);
                return "Comment deleted!!!";
            }else {
                return "Un - authorized comment deletion request!!";
            }
        }else {
            return "Invalid comment !!!!!!";
        }
    }

    public String getPostCommentCount(Long postId, String userName) {
        Post validPost = postService.getPostById(postId);

        if(validPost!=null){
            Integer commentCount = commentService.findAllCommentsCount(validPost);
            return String.valueOf(commentCount);
        }else {
            return "Invalid post....";
        }
    }


    public List<String> getAllComments(Long postId, String userName) {
        Post validPost = postService.getPostById(postId);

        if(validPost!=null){
            List<Comment> comments= commentService.getAllComments(validPost);
            List<String> comment = new ArrayList<>();

            int i=0;
            while (i<comments.size()){
                comment.add(comments.get(0).getContent());
                i++;
            }

            return comment;
        }
        else {
            List<String> val = new ArrayList<>();
            val.add("Invalid post....");
            return val;
        }
    }

    public String followUser(Follow follow, String followerUserName) {

        User targetUser = userRepo.findById(follow.getCurrentUser().getUserId()).orElse(null);
        User followerUser = userRepo.findFirstByUserName(followerUserName);

        if(targetUser!=null){
            if(followService.followAllowed(targetUser, followerUser)){
                followService.startFollowing(follow, followerUser);

                return followerUser.getUserProfileName()+ " is following "+ targetUser.getUserProfileName();
            }else {
                return "You already follows this user.....!";
            }
        }else {
            return "Invalid target user............";
        }
    }

    private boolean authorizedUnfollow(Follow follow, String followerUserName) {
        String targetUser = follow.getCurrentUser().getUserName();
        String followerUser = follow.getCurrentUserFollower().getUserName();

        return targetUser.equals(followerUser) || followerUser.equals(followerUserName);
    }

    public String unfollowUser(Long followId, String followerUserName) {

        Follow follow = followService.findFollow(followId);
        if(follow != null){
            if(authorizedUnfollow(follow, followerUserName)){
                followService.unfollow(follow);
                return follow.getCurrentUser().getUserProfileName() + " is unfollowed by "+ followerUserName;
            }else {
                return "Un authorized unfollow request.......";
            }
        }else {
            return "Invalid follow mapping !!!";
        }
    }


    public String getAllFollowerCount(Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        return followService.getAllFollowerCount(user);
    }


}
