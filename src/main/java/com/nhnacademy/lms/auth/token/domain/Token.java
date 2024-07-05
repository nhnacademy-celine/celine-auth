package com.nhnacademy.lms.auth.token.domain;

import lombok.Getter;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Getter
public abstract class Token {

    private boolean isVerified;
    protected final String tokenValue;

    protected Token(String tokenValue) {
        this.tokenValue = tokenValue;
        this.isVerified = false;
    }

    protected void verify(){
        this.isVerified = true;
    }
}
