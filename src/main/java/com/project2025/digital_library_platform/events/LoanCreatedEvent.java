package com.project2025.digital_library_platform.events;

import org.springframework.context.ApplicationEvent;

public class LoanCreatedEvent extends ApplicationEvent {

    public final Long loanId;

    public LoanCreatedEvent(Long loanId) {
        super(loanId);
        this.loanId = loanId;
    }
}