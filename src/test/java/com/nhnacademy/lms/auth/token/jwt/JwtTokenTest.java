package com.nhnacademy.lms.auth.token.jwt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.nhnacademy.lms.auth.token.domain.JwtToken;
import com.nhnacademy.lms.auth.token.exception.UnverifiedTokenException;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaimsBuilder;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author : 이성준
 * @since : 1.0
 */


@Slf4j
class JwtTokenTest {

    JwtBuilder jwtBuilder;
    JwtParser jwtParser;
    SecretKey secretKey;
    ClaimsBuilder claimsBuilder;

    @BeforeEach
    void setUp() {

        secretKey = Keys.hmacShaKeyFor("1234123412341234123412341234123412341234".getBytes());
        jwtBuilder = Jwts.builder().signWith(secretKey);
        jwtParser = Jwts.parser().verifyWith(secretKey).build();
        claimsBuilder = new DefaultClaimsBuilder();
    }

    @Test
    void constructor() {
        JwtToken token = JwtToken.builder()
                .jwtBuilder(jwtBuilder)
                .build();

        log.info("{}", token);
        assertThat(token, notNullValue());
    }

    @Test
    void getPayload_beforeVerified() {
        JwtToken token = JwtToken.builder()
                .jwtBuilder(jwtBuilder)
                .build();

        assertThat(token, notNullValue());
        assertThrows(UnverifiedTokenException.class, token::getClaimKeys);
    }

    @Test
    void getPayload_afterVerified() {
        JwtToken token = JwtToken.builder()
                .jwtBuilder(jwtBuilder)
                .add("payload", "payload")
                .build();

        token.verify(jwtParser);
        assertDoesNotThrow(() -> assertThat(token.getClaimKeys().size(), greaterThan(0)));
    }
    @Test
    void getSpecificClaim_afterVerified() {
        String claimKey = "payload";
        String claimObject = "payload";

        JwtToken token = JwtToken.builder()
                .jwtBuilder(jwtBuilder)
                .add(claimKey, claimObject)
                .build();

        token.verify(jwtParser);
        assertThat(token.getClaim(claimKey), equalTo(claimObject));
    }

}
