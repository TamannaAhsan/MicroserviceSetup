package com.tamanna.authserver.response.token;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequestDTO {
    private String grantType;
    private String clientId;
    private String clientSecret;
    private String scope;
    private String username;
    private String password;
}
