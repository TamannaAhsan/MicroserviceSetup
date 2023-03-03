package com.tamanna.authserver.controller.auth;

import com.tamanna.authserver.entity.auth.OAuth2Client;
import com.tamanna.authserver.exception.ApiAuthorizationException;
import com.tamanna.authserver.exception.ApiSystemException;
import com.tamanna.authserver.feignInterface.auth.UserInterface;
import com.tamanna.authserver.response.token.TokenResponseDTO;
import com.tamanna.authserver.response.token.UserTokenValidationDto;
import com.tamanna.authserver.response.user.UserLoginDTO;
import com.tamanna.authserver.response.user.UserResponseDTO;
import com.tamanna.authserver.service.auth.AuthService;
import lombok.AllArgsConstructor;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.apache.catalina.connector.Request;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.MessageBytes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final   UserInterface userInterface;
    private final AuthService authService;

    @GetMapping
    public UserResponseDTO test() {
        return userInterface.fetchUserByUsername("1").get();
    }

    @PostMapping("/token")
    public TokenResponseDTO getToken(@RequestParam(value = "grant_type") String grantType,
                                     @RequestParam(value = "client_id", required = false) String clientId,
                                     @RequestParam(value = "client_secret", required = false) String clientSecret,
                                     @RequestParam(value = "scope", required = false) String scope,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "password", required = false) String password,
                                     HttpServletRequest request
    ) throws ApiAuthorizationException, ApiSystemException {


        if(grantType.equalsIgnoreCase("password")){
            UserLoginDTO userLoginDTO = new UserLoginDTO();
            userLoginDTO.setUserName(username);
            userLoginDTO.setPassword(password);

            return authService.createOAuthToken(userLoginDTO, request);
        }

        if(grantType.equalsIgnoreCase("client_credentials")){
            UserLoginDTO userLoginDTO = new UserLoginDTO();
            userLoginDTO.setClientId(clientId);
            userLoginDTO.setClientSecret(clientSecret);

            return authService.createOAuthTokenForClient(userLoginDTO);
        }
        return null;
    }

    @PostMapping("/validate")
    public ResponseEntity<UserTokenValidationDto> validateToken(@RequestParam("token") String token) throws ApiAuthorizationException {
        return ResponseEntity.ok(authService.validateToken(token));
    }

    @PostMapping("/")
    public ResponseEntity<OAuth2Client> createClient (@RequestBody OAuth2Client oAuth2Client) throws ApiSystemException {
        OAuth2Client savedClient = authService.createClient(oAuth2Client);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<OAuth2Client>> getAll (){
        List<OAuth2Client> clients = authService.getAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OAuth2Client> getById (@PathVariable Long id) throws ApiSystemException {
        OAuth2Client oAuth2Client = authService.getById(id);
        return new ResponseEntity<>(oAuth2Client, HttpStatus.OK);
    }

}
