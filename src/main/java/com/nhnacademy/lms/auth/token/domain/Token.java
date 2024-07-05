package com.nhnacademy.lms.auth.token.domain;

import lombok.Getter;

/**
 * The type Token.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
public abstract class Token {

    private boolean isVerified;
    protected final String tokenValue;


    /**
     * Instantiates a new Token.
     *
     * @param tokenValue the token value
     */
    protected Token(String tokenValue) {
        this.tokenValue = tokenValue;
        this.isVerified = false;
    }


    /**
     * Verify.
     * 토큰 객체의 상태를 검증되었음 으로 변경합니다.
     */
    protected void verify(){
        this.isVerified = true;
    }
}
