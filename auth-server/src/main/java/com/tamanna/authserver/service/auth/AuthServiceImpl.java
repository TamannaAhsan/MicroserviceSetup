package com.tamanna.authserver.service.auth;

import com.tamanna.authserver.entity.auth.OAuth2Client;
import com.tamanna.authserver.entity.auth.OAuthToken;
import com.tamanna.authserver.exception.ApiAuthorizationException;
import com.tamanna.authserver.exception.ApiNotAcceptableException;
import com.tamanna.authserver.exception.ApiSystemException;
import com.tamanna.authserver.feignInterface.auth.UserInterface;
import com.tamanna.authserver.repository.auth.OAuth2ClientRepository;
import com.tamanna.authserver.repository.auth.OAuthTokenRepository;
import com.tamanna.authserver.response.token.TokenResponseDTO;
import com.tamanna.authserver.response.token.UserTokenValidationDto;
import com.tamanna.authserver.response.user.UserLoginDTO;
import com.tamanna.authserver.response.user.UserResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.tamanna.authserver.utils.Utility.isNotNull;

@AllArgsConstructor
@Component
@Slf4j
public class AuthServiceImpl implements AuthService{

    private static final Logger logger = LoggerFactory.getLogger(OAuth2Client.class);
    public static final String CONSUMER_OPS = "consumer_ops";
    public static final String CLIENT_OPS = "client_ops";
    private UserInterface userInterface;
    private SecretKey secretKey;
    
    private final Long tokenExpirationAfterDays = 3L;
    private final Long refreshTokenExpirationAfterDays = 30L;
    private OAuthTokenRepository oAuthTokenRepository;
    private OAuth2ClientRepository oAuth2ClientRepository;
    @Override
    public TokenResponseDTO createOAuthToken(UserLoginDTO userLoginDTO, HttpServletRequest request) throws ApiAuthorizationException, ApiSystemException {
        try{
            // authorize token request
            OAuth2Client client = authenticateTokenRequest(request);

            if(client == null)
                throw new ApiAuthorizationException("Client credentials not valid");

            // verify username password from customer service
            UserLoginDTO response = userInterface.isUserNamePasswordValid(userLoginDTO);

            if(response == null)
                throw new ApiAuthorizationException("Not Authorized");

            // create and store  the token in the db
            OAuthToken oAuthToken = new OAuthToken();

            oAuthToken.setAccessTokenValue(createAccessToken(response));
            oAuthToken.setTokenIssuedAt(new Date());
            oAuthToken.setTokenExpiredAt(java.sql.Date.valueOf(
                    LocalDate.now().plusDays(tokenExpirationAfterDays)));
            oAuthToken.setRefreshTokenValue(createRefreshToken(userLoginDTO));
            oAuthToken.setRefreshTokenIssuedAt(new Date());
            oAuthToken.setRefreshTokenExpiredAt(java.sql.Date.valueOf(
                    LocalDate.now().plusDays(refreshTokenExpirationAfterDays)));
            oAuthToken.setTokenType("bearer");

            oAuthTokenRepository.save(oAuthToken);

            // return the response
            return TokenResponseDTO.builder()
                    .accessToken(oAuthToken.getAccessTokenValue())
                    .refreshToken(oAuthToken.getRefreshTokenValue())
                    .expiresIn(tokenExpirationAfterDays.intValue())
                    .tokenType("bearer")
                    .jti("dummy_data_jti")
                    .scope(client.getScopes())
                    .build();
        }catch (Exception e) {
            throw new ApiAuthorizationException("user.name.not.found");
        }
    }

    private OAuth2Client authenticateTokenRequest(HttpServletRequest request) {
        try{

            String encodedAuthRequest = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
            String decodedAuthRequest = new String(Base64.getDecoder().decode(encodedAuthRequest.getBytes()));
            String[] authRequestArray = decodedAuthRequest.split(":");
            String clientId = authRequestArray[0].trim();
            String clientSecret = authRequestArray[1].trim();

            OAuth2Client client = oAuth2ClientRepository.findByClientId(clientId)
                    .orElseThrow(ApiAuthorizationException::new);

            if(clientSecret.equals(client.getClientSecret()))
                return  client;

            return null;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public TokenResponseDTO createOAuthTokenForClient(UserLoginDTO userLoginDTO, HttpServletRequest request) throws ApiAuthorizationException {
        // authorize token request
        OAuth2Client client = authenticateTokenRequest(request);

        if(client == null)
            throw new ApiAuthorizationException("Client credentials not valid");

        // get the oauth2 client
        OAuth2Client oAuth2Client = oAuth2ClientRepository.findByClientId(userLoginDTO.getClientId()).
                orElseThrow(()->new ApiAuthorizationException());

        // return the response
        return TokenResponseDTO.builder().
                accessToken(createAccessTokenForClient(userLoginDTO)).
                scope(client.getScopes()).
                tokenType("bearer").
                expiresIn(oAuth2Client.getAccessTokenValidity()).
                build();
    }

    @Override
    public OAuth2Client createClient(OAuth2Client oAuth2Client) throws ApiSystemException {
        try {
            logger.info("Save User Information");
            if (isNotNull(oAuth2Client.getId())) {
                throw new ApiNotAcceptableException("new entity cant have id");
            }
            oAuth2Client.setClientIdIssuedAt(LocalDateTime.now());
            OAuth2Client savedClient = oAuth2ClientRepository.save(oAuth2Client);
            return savedClient;
        }catch (Exception e) {
            throw new ApiSystemException("user.not.created");
        }
    }

    @Override
    public List<OAuth2Client> getAll() {
        List<OAuth2Client> oAuth2Clients = oAuth2ClientRepository.findAll();
        return oAuth2Clients;
    }

    @Override
    public OAuth2Client getById(Long id) throws ApiSystemException {
        try{
            OAuth2Client oAuth2Client = oAuth2ClientRepository.findById(id)
                    .orElseThrow(()->new ApiSystemException("User id not found"));
            return oAuth2Client;

        }catch (Exception e) {
            throw new ApiSystemException("user.id.not.found");
        }
    }


    private String createAccessToken(UserLoginDTO userLoginDTO){
        String token = Jwts.builder()
                .setSubject(userLoginDTO.getUserName())
                .claim("type", CONSUMER_OPS)
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(
                        LocalDate.now().plusDays(tokenExpirationAfterDays)
                ))
                .signWith(secretKey)
                .compact();
        return token;
    }

    private String createRefreshToken(UserLoginDTO userLoginDTO){
        String token = Jwts.builder()
                .setSubject(userLoginDTO.getUserName())
                .claim("type", CONSUMER_OPS)
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(
                        LocalDate.now().plusDays(refreshTokenExpirationAfterDays)
                ))
                .signWith(secretKey)
                .compact();
        return token;
    }

    private String createAccessTokenForClient(UserLoginDTO userLoginDTO){
        String token = Jwts.builder()
                .setSubject(userLoginDTO.getClientId())
                .claim("scope", userLoginDTO.getScope())
                .claim("type", CLIENT_OPS)
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(
                        LocalDate.now().plusDays(tokenExpirationAfterDays)
                ))
                .signWith(secretKey)
                .compact();
        return token;
    }

    public UserTokenValidationDto validateToken(String token) throws ApiAuthorizationException {
        try{
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build();

            Jwt<Header, Claims> jwt = jwtParser.parse(token);
            Claims claims = jwt.getBody();

            String type = (String) claims.get("type");

            String subject = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            Long id = null;

            if(type.equalsIgnoreCase(CONSUMER_OPS)){
                Optional<UserResponseDTO> userResponseDTO = userInterface.fetchUserByUsername(subject);

                if(userResponseDTO.isEmpty())
                    throw new ApiAuthorizationException("Not Authorized");

                UserResponseDTO userResponse = userResponseDTO.get();

                id = userResponse.getId();
            } else if (type.equalsIgnoreCase(CLIENT_OPS)) {
                OAuth2Client client =  oAuth2ClientRepository.findByClientId(subject)
                        .orElseThrow(()-> new ApiAuthorizationException("Not authorized"));
                id = client.getId();
            }

            return UserTokenValidationDto.builder()
                    .username(subject)
                    .token(token)
                    .id(id)
                    .build();

        }catch (Exception e) {
            throw new ApiAuthorizationException("credentials.not.valid");
        }
    }
}
