package com.nhnacademy.lms.auth.token.domain;

import com.nhnacademy.lms.auth.token.exception.UnverifiedTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * The type Jwt token.
 *
 * @author : 이성준
 * @since : 1.0
 */
public class JwtToken extends Token {

    private JwsHeader header;
    private Map<String, Object> claims;
    private byte[] signature;

    /**
     * Instantiates a new Jwt token.
     *
     * @param tokenValue . (period) 로 구분되는 header, payload, signature 로 이루어진 jwt 문자열입니다.
     */
    public JwtToken(String tokenValue) {
        super(tokenValue);
    }

    private Map<String, Object> getClaims() {
        if (isVerified()) {
            return claims;
        }
        throw new UnverifiedTokenException("검증되지 않은 토큰의 값은 사용할 수 없습니다.");
    }

    /**
     * Gets claim keys.
     *
     * @return the claim keys
     */
    public Set<String> getClaimKeys() {
        return getClaims().keySet();
    }

    /**
     * Gets claim.
     *
     * @param key the key
     * @return the claim
     */
    public Object getClaim(String key) {
        return getClaims().get(key);
    }

    /**
     * Verify Jwt with JwtParser.
     *
     * @param parser the parser to parse jwt
     */
    public void verify(JwtParser parser) throws JwtException, IllegalArgumentException {
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

        /**
         * Instantiates a new Jwt token builder.
         */
        public JwtTokenBuilder() {
            this.jwtBuilder = Jwts.builder();
            this.claimsBuilder = Jwts.claims();
        }

        /**
         * Replace the JwtBuilder for the jwt build.
         *
         * @param jwtBuilder the jwt builder
         * @return the jwt token builder
         */
        public JwtTokenBuilder jwtBuilder(JwtBuilder jwtBuilder){
            this.jwtBuilder = jwtBuilder;
            return this;
        }

        /**
         * add Issuer jwt token.
         *
         * @param iss the iss
         * @return the jwt token builder
         */
        public JwtTokenBuilder issuer(String iss) {
            claimsBuilder.issuer(iss);
            return this;
        }

        /**
         * add Expiration jwt token.
         *
         * @param exp the exp
         * @return the jwt token builder
         */
        public JwtTokenBuilder expiration(Date exp) {
            claimsBuilder.expiration(exp);
            return this;
        }

        /**
         * add IssuedAt jwt token.
         *
         * @param iat the iat
         * @return the jwt token builder
         */
        public JwtTokenBuilder issuedAt(Date iat) {
            claimsBuilder.issuedAt(iat);
            return this;
        }

        /**
         * Add Id for jwt token.
         *
         * @param jti the jti
         * @return the jwt token builder
         */
        public JwtTokenBuilder id(String jti) {
            claimsBuilder.id(jti);
            return this;
        }

        /**
         * Add jwt token claim.
         *
         * @param key   the key
         * @param value the value
         * @return the jwt token builder
         */
        public JwtTokenBuilder add(String key, Object value) {
            claimsBuilder.add(key, value);
            return this;
        }

        /**
         * Build jwt token.
         *
         * @return the jwt token
         */
        public JwtToken build() {
            return new JwtToken(jwtBuilder.claims(claimsBuilder.build()).compact());
        }
    }


}
