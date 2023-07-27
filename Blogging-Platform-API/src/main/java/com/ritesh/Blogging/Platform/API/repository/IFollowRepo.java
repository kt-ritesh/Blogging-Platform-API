package com.ritesh.Blogging.Platform.API.repository;

import com.ritesh.Blogging.Platform.API.model.Follow;
import com.ritesh.Blogging.Platform.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFollowRepo extends JpaRepository<Follow, Long> {
    List<Follow> findByCurrentUserAndCurrentUserFollower(User targetUser, User followerUser);

    List<Follow> findByCurrentUser(User user);
}
