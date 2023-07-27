package com.ritesh.Blogging.Platform.API.repository;


import com.ritesh.Blogging.Platform.API.model.AuthenticationToken;
import com.ritesh.Blogging.Platform.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findFirstByTokenValue(String token);

    AuthenticationToken findFirstByUser(User user);
}
