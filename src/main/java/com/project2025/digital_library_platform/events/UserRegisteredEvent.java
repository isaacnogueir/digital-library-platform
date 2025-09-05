package com.project2025.digital_library_platform.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegisteredEvent extends ApplicationEvent {

    private final Long userId;

    public UserRegisteredEvent(Long userId) {
        super(userId);
        this.userId = userId;
    }

}
