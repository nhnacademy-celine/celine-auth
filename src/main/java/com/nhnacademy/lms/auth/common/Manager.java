package com.nhnacademy.lms.auth.common;

/**
 *
 * @param <T> The type to Manage
 * @author : 이성준
 * @since : 1.0
 */
public interface Manager<T> {

    boolean manage(T target);
    boolean release(T target);


}
