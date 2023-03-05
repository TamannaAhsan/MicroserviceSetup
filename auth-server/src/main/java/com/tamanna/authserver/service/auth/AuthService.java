package com.tamanna.authserver.service.auth;

import com.tamanna.authserver.entity.auth.OAuth2Client;
import com.tamanna.authserver.exception.ApiAuthorizationException;
import com.tamanna.authserver.exception.ApiSystemException;
import com.tamanna.authserver.response.token.TokenResponseDTO;
import com.tamanna.authserver.response.token.UserTokenValidationDto;
import com.tamanna.authserver.response.user.UserLoginDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface AuthService {

    TokenResponseDTO createOAuthToken(UserLoginDTO userLoginDTO, HttpServletRequest request) throws ApiAuthorizationException, ApiSystemException;

    TokenResponseDTO createOAuthTokenForClient(UserLoginDTO userLoginDTO, HttpServletRequest request) throws ApiAuthorizationException;

    OAuth2Client createClient (OAuth2Client oAuth2Client) throws ApiSystemException;

    List<OAuth2Client> getAll ();

    OAuth2Client getById (Long id) throws ApiSystemException;

    UserTokenValidationDto validateToken(String token) throws ApiAuthorizationException;
}
