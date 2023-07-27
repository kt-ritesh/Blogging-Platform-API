# Blogging-Platform-API
Welcome to the Blogging Platform! This is a feature-rich web application that allows users to create, read, update, and delete posts and comments. Additionally, users can search for posts and follow other users to stay updated with their latest content. This platform is built with a focus on stability, security, and usability.

## Features
1. Static MySQL Database and IP Address:

    * The Blogging Platform uses a static MySQL database to ensure data integrity and consistency.
    * The deployment link's IP address is also static, providing a stable experience for users.
2. Post Update and Commenting Permissions:

    * Only the owner of a post can update the post's content.
    * Other users can only comment on the post, promoting engagement and interaction.
3. Pagination for Comments (Optional):

    *  The platform offers an optional pagination feature for comments on user posts.
    * Users can view a maximum of 10 comments at a time, with the ability to navigate through multiple pages.
4. Annotation-Based Validation:

    * All user inputs are thoroughly validated using annotation-based validation techniques.
    * This ensures that the platform remains secure and prevents malicious data from being processed.
5. Controller, Service, and Repository Pattern:

    * The Blogging Platform adheres to the Controller, Service, and Repository architectural pattern.
    * Each layer has its own distinct responsibilities to maintain a clear separation of concerns.
    * The Controller layer handles incoming requests and dispatches them to the appropriate Service methods.
    * The Service layer contains the business logic and communicates with the Repository layer.
    * The Repository layer is responsible for interacting with the MySQL database and managing data operations.

    ## Technologies Used
    * Java: The application is developed using the Java programming language.
    * Spring Boot Framework: Spring Boot is utilized to implement the Controller, Service, and Repository pattern and handle dependency injection.
    * MySQL: The database management system is MySQL, which securely stores posts, comments, and user data.
    * Hibernate: Hibernate is employed as the Object-Relational Mapping (ORM) tool to facilitate seamless data access.
    * Maven: Maven is used for project management, build, and dependency management.

    ## API Endpoints
    1. @PostMapping("user/signup") :- Sign up new User.

    2. @PostMapping("user/signin") :- Login existing user help userName and password.
    3. @DeleteMapping("user/sign-out") :- Sign out login user.
    4. @PostMapping("post") :- Create a new post.
    5. @DeleteMapping("post/delete") :- Delete a post, \
    Note:- Only post owner can delete post.
    6. @PutMapping("post/update") :- Update post contains,\
    Note:- Only post owner can update post.
    7. @GetMapping("post/{title}") :- Get specific post by title.
    8. @PostMapping("comment") :- Comment on after login.
    9. @DeleteMapping("comment/remove") :- Remove comment by comment,\
    Note:- Only post owner and commenter can delete comment.
    10. @GetMapping("comment/count/{postId}") :- Get number of comment.
    11. @GetMapping("comments/{postId}") :- Get all comments.
    12. @PostMapping("follow") :- Follow another user.
    13. @DeleteMapping("unfollow") :- Unfollow user if you following already.
    14. @GetMapping("follower/count/{userId}") :- Get all follower count.

    ## Setup and Installation
    * Visit this link for use this Blogging Platform👇
    

    ## Conclusion
    Congratulations! You have successfully set up the Blogging Platform, which includes a static MySQL database, annotation-based validation, and various features for managing posts, comments, and user interactions. If you have any questions or face any issues, please feel free to contact the development team.

    Happy blogging! 😊