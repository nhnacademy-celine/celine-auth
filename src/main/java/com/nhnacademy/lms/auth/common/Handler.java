package com.nhnacademy.lms.auth.common;

/**
 *
 * @param <T> The type to Handle
 * @author : 이성준
 * @since : 1.0
 */
public interface Handler<T> {

    boolean supports(Class<T> targetClass);
}
