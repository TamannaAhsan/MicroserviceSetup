package com.tamanna.authserver.response.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    private String userName;
    private String password;
    private String role;
    private String clientId;
    private String clientSecret;
    private String scope;

}
