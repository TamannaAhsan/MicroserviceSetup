package com.tamanna.authserver.entity.auth;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "oauth_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthToken implements Serializable  {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registeredClientId;
    private String accessTokenValue;
    private Date tokenIssuedAt;
    private java.sql.Date tokenExpiredAt;
    private String refreshTokenValue;
    private Date refreshTokenIssuedAt;
    private java.sql.Date refreshTokenExpiredAt;
    private String tokenType;

}
