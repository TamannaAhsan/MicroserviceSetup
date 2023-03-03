package com.tamanna.authserver.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Tamanna
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO implements Serializable{

    private Long id;

    private String username;

    private String email;

    private String password;

    private Set<String> roles = new HashSet<>();
}
