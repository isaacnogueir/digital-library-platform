package com.project2025.auth_2.events;

import org.springframework.context.ApplicationEvent;


public class UserRegisteredEvent extends ApplicationEvent {

    private final Long userId;

    public UserRegisteredEvent(Long userId) {
        super(userId);
        this.userId = userId;
    }

}
