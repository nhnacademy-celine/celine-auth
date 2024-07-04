package com.nhnacademy.lms.auth.common;

/**
 *
 * @param <T> The type to Send
 * @author : 이성준
 * @since : 1.0
 */
public interface Sender<T> {

    /**
     *
     * @param payload payload object
     * @author : 이성준
     * @since : 1.0
     */
    void send(T payload);
}
