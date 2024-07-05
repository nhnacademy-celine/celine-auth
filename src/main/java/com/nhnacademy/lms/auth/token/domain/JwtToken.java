package com.nhnacademy.lms.auth.token.domain;

import com.nhnacademy.lms.auth.token.exception.UnverifiedTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author : 이성준
 * @since : 1.0
 */

public class JwtToken extends Token {

    private JwsHeader header;
    private Map<String, Object> claims;
    private byte[] signature;

    public JwtToken(String tokenValue) {
        super(tokenValue);
    }

    private Map<String, Object> getClaims() {
        if (isVerified()) {
            return claims;
        }
        throw new UnverifiedTokenException("검증되지 않은 토큰의 값은 사용할 수 없습니다.");
    }

    public Set<String> getClaimKeys() {
        return getClaims().keySet();
    }

    public Object getClaim(String key) {
        return getClaims().get(key);
    }

    public void verify(JwtParser parser) {
        if (parser.isSigned(tokenValue)) {
            Jws<Claims> jws = parser.parseSignedClaims(tokenValue);

            header = jws.getHeader();
            claims = jws.getPayload();
            signature = jws.getDigest();

            verify();
        }
    }

    @Override
    public String toString() {
        return "JwtToken{" +
                "header=" + header +
                ", claims=" + claims +
                ", signature=" + Arrays.toString(signature) +
                ", tokenValue='" + tokenValue + '\'' +
                '}';
    }

    public static JwtTokenBuilder builder() {
        return new JwtTokenBuilder();
    }

    public static class JwtTokenBuilder {

        private JwtBuilder jwtBuilder;
        private final ClaimsBuilder claimsBuilder;

        public JwtTokenBuilder() {
            this.jwtBuilder = Jwts.builder();
            this.claimsBuilder = Jwts.claims();
        }

        public JwtTokenBuilder jwtBuilder(JwtBuilder jwtBuilder){
            this.jwtBuilder = jwtBuilder;
            return this;
        }

        public JwtTokenBuilder issuer(String iss) {
            claimsBuilder.issuer(iss);
            return this;
        }

        public JwtTokenBuilder expiration(Date exp) {
            claimsBuilder.expiration(exp);
            return this;
        }

        public JwtTokenBuilder issuedAt(Date iat) {
            claimsBuilder.issuedAt(iat);
            return this;
        }

        public JwtTokenBuilder id(String jti) {
            claimsBuilder.id(jti);
            return this;
        }

        public JwtTokenBuilder add(String key, Object value) {
            claimsBuilder.add(key, value);
            return this;
        }

        public JwtToken build() {
            return new JwtToken(jwtBuilder.claims(claimsBuilder.build()).compact());
        }
    }


}
