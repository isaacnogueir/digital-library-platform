package com.project2025.digital_library_platform.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BookCreatedEvent extends ApplicationEvent {

    public final Long bookId;

    public BookCreatedEvent(Long bookId) {
        super(bookId);
        this.bookId = bookId;
    }
}
