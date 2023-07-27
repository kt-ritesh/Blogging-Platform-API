package com.ritesh.Blogging.Platform.API.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true)
    private String userName;
    @NotBlank
    private String userProfileName;
    private String userBio;

    @Email
    @Column(unique = true)
    private String userEmail;
    @NotBlank
    private String userPassword;

}
