package com.project2025.auth_2.events;

import org.springframework.context.ApplicationEvent;

public class BookCreatedEvent extends ApplicationEvent {

    public final Long bookId;

    public BookCreatedEvent(Long bookId) {
        super(bookId);
        this.bookId = bookId;
    }
}
