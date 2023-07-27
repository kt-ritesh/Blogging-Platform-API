package com.ritesh.Blogging.Platform.API.service;

import com.ritesh.Blogging.Platform.API.model.AuthenticationToken;
import com.ritesh.Blogging.Platform.API.model.User;
import com.ritesh.Blogging.Platform.API.repository.IAuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    IAuthenticationRepo authenticationRepo;


    public boolean authenticate(String userName, String token) {

        AuthenticationToken authToken = authenticationRepo.findFirstByTokenValue(token);
        if(authToken == null){
            return false;
        }
        String tokenUserName = authToken.getUser().getUserName();

        return tokenUserName.equals(userName);
    }


    public void saveAuthToken(AuthenticationToken authToken) {

        authenticationRepo.save(authToken);
    }


    public void removeToken(AuthenticationToken authToken) {
        authenticationRepo.delete(authToken);
    }


    public AuthenticationToken findFirstByUser(User user) {

        return authenticationRepo.findFirstByUser(user);
    }
}

