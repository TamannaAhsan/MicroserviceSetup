package com.tamanna.authserver.repository.auth;

import com.tamanna.authserver.entity.auth.OAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthTokenRepository extends JpaRepository<OAuthToken, Long> {
}
